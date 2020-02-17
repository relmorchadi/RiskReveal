import {AfterViewInit, ChangeDetectorRef, Component, Injector, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {BaseTable} from "../../base/base-table.component";

@Component({
  selector: 'd-table',
  templateUrl: './global-table.component.html',
  styleUrls: ['./global-table.component.scss']
})
export class GlobalTableComponent extends BaseTable implements OnInit, AfterViewInit, OnChanges {

  totalColumnWidth: number;
  data: any[];
  columns: any[];

  isModalVisible: boolean;

  visibleList = [];

  availableList = [];

  constructor(_injector: Injector, cdRef: ChangeDetectorRef) {
    super(_injector, cdRef);
    this.injectDependencies('plt-manager');
    this.data = [];
    this.columns= [];
    this.visibleList= [];
    this.availableList= [];
    this.totalColumnWidth = 0;
  }

  ngOnInit() {
    super.ngOnInit();


    this._handler.data$.subscribe(d => {
      this.data= d;
      this.detectChanges();
    });

    this._handler.visibleColumns$.subscribe(c => {
      this.columns= c;
      this.visibleList= c;
      this.detectChanges();
    });

    this._handler.availableColumns$.subscribe(c => {
      this.availableList= c;
      this.detectChanges();
    });

    this._handler.totalColumnWidth$.subscribe(totalColumnWidth => {
      this.totalColumnWidth = totalColumnWidth;
      this.detectChanges();
    })
  }

  ngAfterViewInit(): void {
    super.ngAfterViewInit();
  }

  detectChanges() {
    super.detectChanges();
  }

  ngOnChanges(changes: SimpleChanges): void {
    super.ngOnChanges(changes);
  }

  showDialog() {
    this.isModalVisible = true;
  }

  handleManageColumsActions(action) {
    switch (action.type) {
      case "Manage Columns":
        this._handler.onManageColumns(action.payload);
        break;

      default:
        console.log(action);
    }
  }

}
