import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input, OnDestroy,
  OnInit,
  Output, QueryList,
  ViewChild,
  ViewChildren
} from '@angular/core';
import {CdkDragDrop, moveItemInArray,  CdkDrag, CdkDragMove, transferArrayItem} from '@angular/cdk/drag-drop';
import {startWith , map , switchMap , tap} from 'rxjs/operators';
import {merge , Subscription} from 'rxjs';
import * as _ from 'lodash';

const speed = 10;

@Component({
  selector: 'app-manage-columns',
  templateUrl: './manage-columns.component.html',
  styleUrls: ['./manage-columns.component.scss']
})
export class ManageColumnsComponent implements OnInit, AfterViewInit, OnDestroy {

  @Input('columns') columns: any[];
  @Input('visible') visible: boolean;
  @Input('fromDash') fromDash: boolean = false;

  @Output('onSubmit') onSubmit: EventEmitter<any> = new EventEmitter<any>();
  @Output('onCancel') onCancel: EventEmitter<any> = new EventEmitter<any>();

  listOfAvailableColumnsCache: any[];
  listOfAvailableColumns: any[];
  listOfUsedColumns: any[];

  @ViewChild('scrollEl')
  scrollEl:ElementRef<HTMLElement>;

  @ViewChildren(CdkDrag)
  dragEls:QueryList<CdkDrag>;

  subs = new Subscription();

  private animationFrame: number | undefined;

  constructor() {
    this.listOfAvailableColumnsCache = [];
  }

  ngOnInit() {
    this.listOfAvailableColumns = [];
  }

  ngAfterViewInit(){
    const onMove$ = this.dragEls.changes.pipe(
        startWith(this.dragEls)
        , map((d: QueryList<CdkDrag>) => d.toArray())
        , map(dragels => dragels.map(drag => drag.moved))
        , switchMap(obs => merge(...obs))
        , tap(this.triggerScroll)
    );

    this.subs.add(onMove$.subscribe());

    const onDown$ = this.dragEls.changes.pipe(
        startWith(this.dragEls)
        , map((d: QueryList<CdkDrag>) => d.toArray())
        , map(dragels => dragels.map(drag => drag.ended))
        , switchMap(obs => merge(...obs))
        , tap(this.cancelScroll)
    );

    this.subs.add(onDown$.subscribe());
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }

  @bound
  public triggerScroll($event: CdkDragMove) {
    if (this.animationFrame) {
      cancelAnimationFrame(this.animationFrame);
      this.animationFrame = undefined;
    }
    this.animationFrame = requestAnimationFrame(() => this.scroll($event));
  }

  @bound
  private cancelScroll() {
    if (this.animationFrame) {
      cancelAnimationFrame(this.animationFrame);
      this.animationFrame = undefined;
    }
  }

  private scroll($event: CdkDragMove) {
    const { y } = $event.pointerPosition;
    const baseEl = this.scrollEl.nativeElement;
    const box = baseEl.getBoundingClientRect();
    const scrollTop = baseEl.scrollTop;
    const top = box.top + - y ;
    if (top > 0 && scrollTop !== 0) {
      const newScroll = scrollTop - speed * Math.exp(top / 50);
      baseEl.scrollTop = newScroll;
      this.animationFrame = requestAnimationFrame(() => this.scroll($event));
      return;
    }

    const bottom = y - box.bottom ;
    if (bottom > 0 && scrollTop < box.bottom) {
      const newScroll = scrollTop + speed * Math.exp(bottom / 50);
      baseEl.scrollTop = newScroll;
      this.animationFrame = requestAnimationFrame(() => this.scroll($event));
    }
  }

  resetColumns() {
    if (!this.visible) this.listOfUsedColumns = [...this.columns];
    this.onCancel.emit();
  }

  saveColumns() {
    this.onSubmit.emit(this.listOfUsedColumns);
  }

  onShow() {
    if (this.fromDash) {
      this.listOfAvailableColumns = _.filter(this.columns, item => !item.visible);
      this.listOfAvailableColumnsCache = [...this.listOfAvailableColumns];
      this.listOfUsedColumns = _.filter(this.columns, item => item.visible);
    } else {
      this.listOfAvailableColumnsCache = [...this.listOfAvailableColumns];
      this.listOfUsedColumns = this.columns;
    }
  }

  drop(event: CdkDragDrop<any>) {
    const {
      previousContainer,
      container
    } = event;

    if (previousContainer === container) {
      if (container.id === 'usedListOfColumns') {
        moveItemInArray(
          this.listOfUsedColumns,
          event.previousIndex,
          event.currentIndex
        );
      }
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    }
  }

  onHide() {
    console.log(this.listOfAvailableColumns, this.listOfUsedColumns, this.listOfAvailableColumnsCache);
  }

  dropAll(dir: string) {
    if (dir === 'right') {
      const t = this.listOfUsedColumns;
      this.listOfUsedColumns = this.listOfAvailableColumns;
      this.listOfAvailableColumns = t;
    }
    if (dir === 'left') {
      const t = this.listOfAvailableColumns;
      this.listOfAvailableColumns = this.listOfUsedColumns;
      this.listOfUsedColumns = t;
    }
  }
}

export function bound(target: Object, propKey: string | symbol) {
  var originalMethod = (target as any)[propKey] as Function;

  // Ensure the above type-assertion is valid at runtime.
  if (typeof originalMethod !== "function") throw new TypeError("@bound can only be used on methods.");

  if (typeof target === "function") {
    // Static method, bind to class (if target is of type "function", the method decorator was used on a static method).
    return {
      value: function () {
        return originalMethod.apply(target, arguments);
      }
    };
  } else if (typeof target === "object") {
    // Instance method, bind to instance on first invocation (as that is the only way to access an instance from a decorator).
    return {
      get: function () {
        // Create bound override on object instance. This will hide the original method on the prototype, and instead yield a bound version from the
        // instance itself. The original method will no longer be accessible. Inside a getter, 'this' will refer to the instance.
        var instance = this;

        Object.defineProperty(instance, propKey.toString(), {
          value: function () {
            // This is effectively a lightweight bind() that skips many (here unnecessary) checks found in native implementations.
            return originalMethod.apply(instance, arguments);
          }
        });

        // The first invocation (per instance) will return the bound method from here. Subsequent calls will never reach this point, due to the way
        // JavaScript runtimes look up properties on objects; the bound method, defined on the instance, will effectively hide it.
        return instance[propKey];
      }
    } as PropertyDescriptor;
  }
}
