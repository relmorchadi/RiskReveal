import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {fromEvent, Subscription} from "rxjs";
import {filter, switchMap} from "rxjs/operators";
import * as _ from "lodash";
import {DndDropEvent, DropEffect} from 'ngx-drag-drop';
import {Store} from '@ngxs/store';
import {Router} from '@angular/router';
import {BaseContainer} from '../../../shared/base';

@Component({
  selector: 'app-workspace-inuring-package',
  templateUrl: './workspace-inuring-package.component.html',
  styleUrls: ['./workspace-inuring-package.component.scss']
})
export class WorkspaceInuringPackageComponent extends BaseContainer implements OnInit {

  size;
  disabled;

  visibleIcon: Boolean;

  cols: any[];
  adjsArray: any[];
  visible: Boolean = false;
  tree: Boolean = false;
  pure: any = {};
  data = [
    {
      key: '1',
      name: 'John Brown',
      age: 32,
      address: 'New York No. 1 Lake Park'
    },
    {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park'
    },
    {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sidney No. 1 Lake Park'
    }, {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park'
    },
    {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sidney No. 1 Lake Park'
    }, {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park'
    },
    {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sidney No. 1 Lake Park'
    }
  ];
  hideThread: {};
  isVisible = false;
  singleValue: string = " ";
  basises: any[];
  categorySelectedFromBasis: string[] = [];
  columnPosition: number;
  selectAllBool = true;
  colunmName = null;
  stockedColumnName: string;
  listOfSelectedValue = [];
  @ViewChild('scrollOne') scrollOne: ElementRef;
  @ViewChild('scrollTwo') scrollTwo: ElementRef;
  divIn: boolean = false;
  supscription1: Subscription;
  supscription2: Subscription;
  dndBool: boolean = false;
  checked: boolean = false;
  onHoverIcon: any;
  lastCheckedBool: boolean = false;
  lastChecked;
  private currentDraggableEvent: DragEvent;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);

    this.adjsArray = [
      {id: 1, name: 'Missing Exp', value: 2.1, linear: false, category: "pd"},
      {id: 2, name: 'Port. Evo', value: 2.1, linear: false, category: "bd"},
      {id: 3, name: 'ALAE', value: 1.75, linear: false, category: "pd"},
      {id: 4, name: 'Model Calib', value: "RPB (EEF)", linear: true, category: "pd"}
    ];

    this.cols = [
      {field: 'vin', header: 'Vin'},
      {field: 'year', header: 'Year'},
      {field: 'brand', header: 'Brand'},
      {field: 'color', header: 'Color'}
    ];
    this.pure = {
      category: [
        {
          name: "Pre-Default",
          basis: [],
          showBol: true
        }, {
          name: "Default",
          basis: [],
          showBol: true
        }, {
          name: "Post-Default",
          basis: [],
          showBol: true
        }, {
          name: "Inuring",
          basis: [],
          showBol: true
        }
      ],
      dataTable: [
        {
          name: "Misk net [12233875]",
          thread: [
            {
              id: "122222",
              threadName: "APEQ-ID_GU_CFS PORT 1",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              adj: []
            }, {
              id: "122222",
              threadName: "APEQ-ID_GU_CFS PORT 2",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              adj: []
            }, {
              id: "122222",
              threadName: "APEQ-ID_GU_CFS PORT 3",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              adj: []
            },
            {
              id: "122223",
              threadName: "APEQ-ID_GU_LMF1.T1",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              adj: []
            },
            {
              id: "122224",
              threadName: "APEQ-ID_GU_LMF1.T11687",
              icon: 'icon-check-circle iconGreen',
              checked: false,
              adj: []
            }

          ]
        },
        {
          name: "Misk net [12233895]",
          thread: [
            {
              id: "122252", threadName: "APEQ-ID_GULM 1", icon: 'icon-lock-alt iconRed',
              checked: false,
              adj: []
            }, {
              id: "122252", threadName: "APEQ-ID_GULM 2", icon: 'icon-lock-alt iconRed',
              checked: false,
              adj: []
            },

          ]
        },
        {
          name: "CFS PORT MAR18 [12233895]",
          thread: [
            {
              id: "12299892", threadName: "Apk lap okol Pm 1", icon: 'icon-history-alt iconYellow',
              checked: false,
              adj: []
            }, {
              id: "12299892", threadName: "Apk lap okol Pm 2", icon: 'icon-history-alt iconYellow',
              checked: false,
              adj: []
            }

          ]
        },
      ]
    }
  }

  ngOnInit() {
    /** scroll principe ****/
    const scrollOne = this.scrollOne.nativeElement as HTMLElement;
    const scrollTwo = this.scrollTwo.nativeElement as HTMLElement;
    let scroll2Event$ = fromEvent(scrollTwo, 'scroll');
    let scroll1Event$ = fromEvent(scrollOne, 'scroll');
    let supscription1 = scroll2Event$.pipe(
      filter(() => this.divIn === true)
    ).pipe(this.unsubscribeOnDestroy).subscribe(
      value => {
        scrollOne.scrollTop = scrollTwo.scrollTop;
      }
    )
    let supscription2 = scroll1Event$.pipe(
      filter(() => this.divIn === false)
    ).pipe(this.unsubscribeOnDestroy).subscribe(
      value => {
        scrollTwo.scrollTop = scrollOne.scrollTop;
      });

    this.getAllBasises();

    /** drawer principe **/

    let c = 209;
    addEventListener('scroll', function () {
      let x = (document as any).getElementsByClassName("ant-drawer-content")[0].style = " height: 120%;position:absolute;top:" + c + "px";
      var y = 209 - pageYOffset
      c = y;
    });
  }

  open() {
    this.visible = true;
  }

  close() {
    this.visible = false;
  }

  afficheTree() {
    this.tree = true;
  }

  hide(name) {
    _.hasIn(this.hideThread, name) ? _.set(this.hideThread, name, !_.get(this.hideThread, name)) : this.hideThread = {
      ...this.hideThread,
      [name]: false
    };
  }

  returnHideThread(name) {
    return _.isNil(_.get(this.hideThread, name)) ? true : this.hideThread[name];
  }

  CloseTree() {
    this.tree = false;
  }

  returnBasis() {
    let number = 0;
    _.forIn(this.pure.category, function (value, key) {
      number += _.values(_.get(value, "basis")).length;
    });
    return number;
  }

  addColumn() {
    console.log("test test");
  }

  showModal(): void {
    this.isVisible = true;
  }

  addColumnToTableAndClose(a, b): void {

    let index = _.findIndex(this.pure.category, {name: b});
    //this.pure.category[index].basis.push({name: a})
    this.pure.category[index].basis.splice(this.columnPosition - 1, 0, {name: a});
    this.isVisible = false;
  }

  addColumnToTable(a, b) {
    let index = _.findIndex(this.pure.category, {name: b});
    //this.pure.category[index].basis.push({name: a})
    this.pure.category[index].basis.splice(this.columnPosition - 1, 0, {name: a});
    this.isVisible = true;
  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }

  selectBasis(basis) {
    let x;
    _.forIn(this.pure.category, function (value, key) {
      if (_.findIndex(value.basis, {name: basis}) != -1) {
        console.log(value.name);
        x = value.name;
      }
    })
    this.categorySelectedFromBasis = x;
    let index = _.findIndex(this.pure.category, {name: x});
    this.columnPosition = this.pure.category[index].basis.length + 1;

  }

  selectCategory(p) {
    let index = _.findIndex(this.pure.category, {name: p});
    this.columnPosition = this.pure.category[index].basis.length + 1;
    this.categorySelectedFromBasis = p;
  }

  getAllBasises() {
    let x = [];
    _.forIn(this.pure.category, function (value, key) {
      x = _.concat(x, value.basis)
    })
    this.basises = x;
    console.log(this.basises)
  }

  visibledIcon() {
    this.visibleIcon = true;
  }

  styleBorder(tdNumber, sizeOfBasis) {
    let x = sizeOfBasis - 1;
    if (tdNumber == x) {
      return '1px solid rgb(232, 232, 232)';
    } else return;
  }

  trackByFn(index, item) {
    return index; // or item.id
  }

  eyeVisible() {
    return this.visible ? "icon-visibility_off_24px" : "icon-remove_red_eye_24px";
  }

  cloneThread(l, threadData, pureIndex, threadIndex) {

    this.pure.dataTable[pureIndex].thread.splice(threadIndex + 1, 0, threadData);

  }

  deleteThread(l, threadData, pureIndex, threadIndex) {

    this.pure.dataTable[pureIndex].thread.splice(threadIndex, 1);

  }

  changeBackgroud(event, a) {
    let o = 0;
    let x;
    let i;
    if (event.ctrlKey) {
      _.forIn(this.pure.dataTable, function (value, key) {
        if (_.findIndex(value.thread, a) != -1) {
          o = i;
          x = _.findIndex(value.thread, a);
          if (value.thread[x].checked == false)
            value.thread[x].checked = true;
          else
            value.thread[x].checked = false;
        }
      })
    } else if (event.shiftKey) {
      let between = false;
      let lastChecked = this.lastChecked;
      console.log(lastChecked);
      if (!lastChecked) {
        lastChecked = this.pure.dataTable[0].thread[0]
        console.log("test")
      }
      console.log(lastChecked);

      _.forIn(this.pure.dataTable, function (value, key) {
        _.forIn(value.thread, function (plt, key) {
          if (between) {
            plt.checked = true
          }
          if (plt == lastChecked || plt == a) {
            between = !between;
            plt.checked = true
          }
        })
      })
    } else {
      console.log(a);
      this.lastChecked = false;
      let checked = a.checked;
      this.deselectAll();
      _.forIn(this.pure.dataTable, function (value, key) {
        if (_.findIndex(value.thread, a) != -1) {
          o = i;
          x = _.findIndex(value.thread, a);
          value.thread[x].checked = !checked;
        }
      })
    }
    if (!this.lastCheckedBool) {
      this.lastChecked = a;
    } else {
      this.lastCheckedBool = false;
    }

  }

  changeBackgroundCheckBox(event, a) {
    let checked = a.checked;
    console.log("event Check")
    let o = 0;
    let x;
    let i;
    if (event.shiftKey) {
      let between = false;
      let lastChecked = this.lastChecked;
      _.forIn(this.pure.dataTable, function (value, key) {
        _.forIn(value.thread, function (plt, key) {
          if (between) {
            plt.checked = true
          }
          if (plt == lastChecked || plt == a) {
            between = !between;
            plt.checked = true
          }
        })
      })
    } else {
      _.forIn(this.pure.dataTable, function (value, key) {
        if (_.findIndex(value.thread, a) != -1) {
          o = i;
          x = _.findIndex(value.thread, a);
          value.thread[x].checked = !checked;
        }
      })
    }
  }

  setBackgroud(a) {
    if (a)
      return "#FCF9D6";
    else
      return "#fff";
  }

  deselectAll() {
    this.lastCheckedBool = true;
    const checkboxs = document.querySelectorAll(".checkboxInput input[type='checkbox']");
    checkboxs.forEach((checkbox: any) => checkbox.checked = false);
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (plt, key) {
        plt.checked = false;
      })
    })
  }

  selectAllThread(selected) {
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (value, key) {
        value.checked = selected;
      })

    })
    this.selectAllBool = !this.selectAllBool;
  }

  feedColunmName(category, nameOfColumn) {

    if (category == "Default") {
      return;
    } else {
      if (this.colunmName == nameOfColumn) {
        console.log("already feeded!");
        this.colunmName = null;
      } else {
        this.stockedColumnName = null;
        this.colunmName = nameOfColumn;
      }
      console.log("******************")
      console.log("1st col: ", this.colunmName)
      console.log("2nd col: ", this.stockedColumnName)
      console.log("******************")
    }
  }

  iClearAdjustment(colunmName, locked) {

    if (this.colunmName === colunmName && locked !== "icon-lock-alt iconRed") {
      return "hidden";
    } else if (this.stockedColumnName === colunmName && locked !== "icon-lock-alt iconRed") {
      return "hidden";
    } else {
      return " "
    }
  }

  hideCatgegory(names: any[]) {
    console.log(names);
    this.hideAllCategories();
    for (let i = 0; i < names.length; i++) {
      let a = _.findIndex(this.pure.category, function (o: any) {
        return o.name == names[i]
      });
      console.log(a);
      this.pure.category[a].showBol = true;
      // _.set(this.pure.category[a], 'showBol', true);
    }

  }

  hideAllCategories() {
    _.forIn(this.pure.category, function (value, key) {
        _.set(value, 'showBol', false);
      }
    )
  }

  feedCatgegoryNameForHide(name) {
    let a = _.findIndex(this.pure.category, function (o: any) {
      return o.name == name
    });
    this.pure.category[a].showBol = false;
    for (let i = 0; i < this.listOfSelectedValue.length; i++) {
      if (this.listOfSelectedValue[i] == name) {
        this.listOfSelectedValue.splice(i, 1);
      }
    }
  }

  updateScroll() {
    /*  const scrollOne = this.scrollOne.nativeElement as HTMLElement;
      const scrollTwo = this.scrollTwo.nativeElement as HTMLElement;
      //scrollOne.scrollTop= scrollTwo.scrollTop; const scroll1Event$ =  fromEvent(scrollOne, 'scroll');
      */
  }


  updateScroll2() {
    const scrollOne = this.scrollOne.nativeElement as HTMLElement;
    const scrollTwo = this.scrollTwo.nativeElement as HTMLElement;
    //scrollOne.scrollTop= scrollTwo.scrollTop;
    const scroll1Event$ = fromEvent(scrollOne, 'scroll');
    const scroll2Event$ = fromEvent(scrollTwo, 'scroll');
    scroll2Event$.pipe(
      switchMap(event => {
        return scroll1Event$;
      })
    ).pipe(this.unsubscribeOnDestroy).subscribe(
      value => {
        scrollTwo.scrollTop = scrollOne.scrollTop
      }
    )

  }

  deleteColumn(name: string, name2: string) {

    let o = _.findIndex(this.pure.category, function (o: any) {
      return o.name == name
    });
    let a = _.findIndex(this.pure.category[o].basis, function (o: any) {
      return o.name == name2
    });
    this.pure.category[o].basis.splice(a, 1);
  }

  cursorOntable1() {
    this.divIn = true;
  }

  cursorOntable2() {
    this.divIn = false;
  }

  addAdjustmenet(adj) {
    console.log(adj)
    _.forIn(this.pure.dataTable, function (value, key) {

      _.forIn(value.thread, function (thraed, key) {
          if (thraed.checked == true) {
            thraed.adj.push(adj);
          }
        }
      )
    })
  }


  sleep(millis: number) {

    var date = new Date();
    var curDate = null;
    do {
      curDate = new Date();
    }
    while (curDate.getTime() - date.getTime() < millis);
  }

  onDragged(event: DragEvent, item: any, list: any[], effect: DropEffect, a?) {
    if (effect == "none") return;
    if (this.dndBool) {
      console.log("here1", this.dndBool)
      const index = list.indexOf(item);
      list.splice(index, 1);

    }
    if (item.content === "Block") {
      // simulate long running task
      this.sleep(1000);
    } else {
      // run long task in next loop
      setTimeout(() => this.sleep(1000), 0);
    }
  }

  onDrop(event: DndDropEvent, list: any[]) {
    let index = event.index;

    if (typeof index === "undefined") {

      index = list.length;
    }
    let id = event.event.toElement.id;
    event.data.category = id.charAt(id.length - 1) == '1' ? id.slice(0, id.length - 1) : id;
    console.log("heeeee. ", event.data.category)

    if (event.data.category == "bd" || event.data.category == "pd") {
      console.log("dropTrue", this.dndBool)
      this.dndBool = true;
      list.splice(index, 0, event.data);
    } else {
      this.dndBool = false;
      console.log("dropFalse", this.dndBool)
    }

  }

  onDragStart(event: DragEvent) {
  }

  onDragEnd(event: DragEvent) {

    this.currentDraggableEvent = event;
  }


  deleAdjustment(dataTableIndex, threadIndex, adjindex) {
    console.log(this.pure.dataTable[dataTableIndex].thread[threadIndex].adj);
    this.pure.dataTable[dataTableIndex].thread[threadIndex].adj.splice(adjindex, 1);
  }

  onMouseHover(threadIndex) {
    console.log("test", threadIndex);
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }
}
