import {Component, OnInit} from '@angular/core';
import * as _ from 'lodash'
import {DndDropEvent, DropEffect} from "ngx-drag-drop";
import {
  ADJUSTMENT_TYPE,
  ALL_ADJUSTMENTS,
  DATA,
  LIST_OF_DISPLAY_PLTS,
  LIST_OF_PLTS,
  PLT_COLUMNS,
  PURE,
  SYSTEM_TAGS,
  USER_TAGS
} from "./data";


@Component({
  selector: 'app-workspace-calibration',
  templateUrl: './workspace-calibration.component.html',
  styleUrls: ['./workspace-calibration.component.scss']
})
export class WorkspaceCalibrationComponent implements OnInit {
  inputValue;
  size;
  disabled;


  adjsArray: any[] = [];
  visible: Boolean = false;
  tree: Boolean = false;
  pure: any = {};
  data = DATA;
  hideThread: {};
  isVisible = false;
  singleValue: any = "none";
  basises: any[];
  categorySelectedFromAdjustement: any;
  columnPosition: number;
  selectAllBool = true;

  divIn: boolean = false;
  dndBool: boolean = false;
  checked: boolean = false;
  lastChecked;
  lastCheckedBool: boolean = false;
  firstChecked;
  checkedAll: boolean = false;
  indereterminate: boolean = false;
  systemTags = [];
  userTags = [];
  currentSystemTag = null;
  currentUserTag = null;
  sortName: string | null = null;
  sortValue: string | null = null;
  searchAddress: string;
  pltSpan: number = 7;
  templateSpan: number = 14;
  allAdjsArray = [];
  extended: boolean = false;
  idPlt: string = null;
  AdjustementType: any[];
  categorySelected: string = "Selected Category";
  listOfPlts = LIST_OF_PLTS;
  listOfDisplayPlts = LIST_OF_DISPLAY_PLTS;
  addAdjustement: boolean;
  pltColumns = PLT_COLUMNS;
  ColpasBool: boolean = true;
  linear: boolean = false;
  private currentDraggableEvent: DragEvent;

  constructor() {
    this.AdjustementType = ADJUSTMENT_TYPE;
    this.pure = PURE;
    this.systemTags = SYSTEM_TAGS;
    this.userTags = USER_TAGS;
    this.allAdjsArray = ALL_ADJUSTMENTS;
  }

  ngOnInit() {
    this.getAllBasises();

    /** drawer principe **/

    let c = 209;
    addEventListener('scroll', function () {
      let x = (document as any).getElementsByClassName("ant-drawer-content")[0].style = " height: 120%;position:absolute;top:" + c + "px";
      var y = 209 - pageYOffset
      c = y;
    });
  }

  hideIcon(adj) {
    console.log("out")
    adj.hover = false;
  }

  open() {
    this.visible = true;
  }

  close() {
    this.visible = false;
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


  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }

  selectBasis(adjustement) {
    if (adjustement.id == 1) {
      this.linear = true;
    } else {
      this.linear = false;
    }
  }

  selectCategory(p) {
    this.categorySelectedFromAdjustement = p;
    this.categorySelected = p.category;
    console.log(p);
  }

  getAllBasises() {
    let x = [];
    _.forIn(this.pure.category, function (value, key) {
      x = _.concat(x, value.basis)
    })
    this.basises = x;
    console.log(this.basises)
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
      this.lastCheckedBool = true;
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
      this.lastCheckedBool = true;
      let between = false;
      if (_.isNil(this.firstChecked)) {
        this.firstChecked = this.pure.dataTable[0].thread[0];
      }
      let lastChecked = this.firstChecked;
      this.deselectAll(lastChecked);
      if (lastChecked == a) {
        return;
      }
      _.forIn(this.pure.dataTable, function (value, key) {
        _.forIn(value.thread, function (plt, key) {
          if (between) {
            plt.checked = true
          }

          if (plt == lastChecked || plt == a) {
            plt.checked = true;
            between = !between;
          }
        })
      })
    } else {
      let checked = a.checked;
      let booShift = this.lastCheckedBool;
      this.deselectAll(null);
      this.firstChecked = a;
      let firstChecked = this.firstChecked;
      _.forIn(this.pure.dataTable, function (value, key) {
        if (_.findIndex(value.thread, a) != -1) {
          o = i;
          x = _.findIndex(value.thread, a);
          if (!booShift) {
            value.thread[x].checked = !checked;
          } else {
            value.thread[x].checked = true;
          }

        }
      })
      this.lastCheckedBool = false;
    }
    this.cheackBoxPrincipe();
  }

  changeBackgroundCheckBox(event, a) {

    let checked = a.checked;
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
    this.cheackBoxPrincipe();
  }

  cheackBoxPrincipe() {
    let inder = false;
    let selectedAll = false;
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (plt, key) {
          if (plt.checked) {
            inder = true;
          } else {
            selectedAll = true;
          }
        }
      )
    })
    inder ? this.indereterminate = true : this.indereterminate = false;
    if (selectedAll) {
      this.checkedAll = false;
    } else {
      this.checkedAll = true;
      this.indereterminate = false;
    }
  }

  setBackgroud(a) {
    if (a)
      return "#FCF9D6";
    else
      return "#fff";
  }

  deselectAll(lastChecked) {
    this.firstChecked = lastChecked;
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (plt, key) {
        plt.checked = false;
      })
    })
  }

  InuringBack(a) {
    if (a == 'Inuring') {
      return "antiquewhite";
    }
  }

  selectAllThread(selected) {
    this.indereterminate = false;
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (value, key) {
        value.checked = selected;
      })
    })
    this.selectAllBool = !this.selectAllBool;
    _.forIn(this.pure.dataTable, function (value1, key) {
      _.forIn(value1.thread, function (plt, key) {
        if (plt.checked === true) {
          console.log("test5");
        }
      })
    })

  }


  cursorOntable1() {
    this.divIn = true;
  }

  cursorOntable2() {
    this.divIn = false;
  }

  addAdjustmenet(adj) {
    var today = new Date();
    var milliseconds = today.getMilliseconds();
    let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (thraed, key) {
          let myThread = thraed;
          console.log(thraed.adj, "thread")
          if (myThread.checked == true) {
            let newAdj = {...adj};
            newAdj.id = numberAdjs;
            myThread.adj.push(newAdj);
            return;
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
    console.log("ondrag")

    let index = event.index;

    if (typeof index === "undefined") {

      index = list.length;
    }
    let id = event.event.toElement.id;
    console.log("heeeee.1 ", event.data.category)
    //event.data.category = id.charAt(id.length - 1) == '1' ? id.slice(0, id.length - 1) : id;
    console.log("heeeee.2 ", event.data.category)

    if (event.data.category == "Base" || event.data.category == "Client") {
      console.log("dropTrue", this.dndBool)
      this.dndBool = true;
      list.splice(index, 0, event.data);
    } else {
      this.dndBool = false;
      console.log("dropFalse", this.dndBool)
    }

  }

  onDropDiv(event: DndDropEvent, list: any[]) {
    console.log("ondragDiv")

    let index = event.index;

    if (typeof index === "undefined") {

      index = list.length;
    }
    let id = event.event.toElement.id;
    //event.data.category = id.charAt(id.length - 1) == '1' ? id.slice(0, id.length - 1) : id;
    console.log("heeeee. ", event.data.category)

    if (event.data.category == "Base" || event.data.category == "Client" || event.data.category == "poin") {
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


  deleAdjustment(dataTableIndex, threadIndex, adjindex, adj) {
    console.log("hhh")
    if (this.pure.dataTable[dataTableIndex].thread[threadIndex].locked) return;
    _.forIn(this.pure.dataTable[dataTableIndex].thread, function (value, key) {
      _.remove(value.adj, function (n) {
        console.log("here");
        return n == adj;
      });
    })
    // this.pure.dataTable[dataTableIndex].thread[threadIndex].adj.splice(adjindex, 1);
  }


  haveTagSystem(thread: any) {
    let currenSystemTag = this.currentSystemTag;
    let currentUserTag = this.currentUserTag;
    if (!_.isNil(this.currentUserTag)) {
      var numberUsre = _.findIndex(thread.userTags, function (o: any) {
        return o.tagId == currentUserTag.tagId
      });
    }
    if (!_.isNil(this.currentSystemTag)) {
      var numberSystem = _.findIndex(thread.systemTags, function (o: any) {
        return o.tagId == currenSystemTag.tagId
      });
    }
    if (_.isNil(this.currentSystemTag)) {
      if (_.isNil(this.currentUserTag)) return true;
      else {
        if (numberUsre < 0) {
          return false;
        } else {
          return true;
        }
      }
    } else {
      if (numberSystem < 0) {
        return false;
      } else {
        if (_.isNil(this.currentUserTag)) return true;
        else {
          if (numberUsre < 0) {
            return false;
          } else {
            return true;
          }
        }
      }
    }

  }

  getColorTag(tag) {
    return _.find(this.userTags, function (o) {
      return o.tagId == tag.tagId;
    }).tagColor;
  }

  applyToAllAdjustement(adjustement, valueOfAdj) {
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (thread, key) {
        _.forIn(thread.adj, function (adj, key) {
          console.log("yes1")
          if (adj.idAdjustementType == adjustement.idAdjustementType) {
            console.log(valueOfAdj);
            adj.value = valueOfAdj;
          }
        })
      })
    })
  }

  onChangeAdjValue(adj, event) {
    adj.value = event.target.value;
  }

  applyToSelectedPlt(adjustement, valueOfAdj) {
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (thread, key) {
        _.forIn(thread.adj, function (adj, key) {
          console.log("yes1")
          if (adj.idAdjustementType == adjustement.idAdjustementType && thread.checked == true) {
            adj.value = valueOfAdj;
          }
        })
      })
    })
  }

  sort(sort: { key: string; value: string }): void {
    this.sortName = sort.key;
    this.sortValue = sort.value;
    this.search();
  }

  search(): void {
    /** filter data **/
      // const filterFunc = (item: { name: string; age: number; address: string }) =>
      //   (this.searchAddress ? item.address.indexOf(this.searchAddress) !== -1 : true) &&
      //   (this.listOfSearchName.length ? this.listOfSearchName.some(name => item.name.indexOf(name) !== -1) : true);
    const data = this.listOfPlts;
    // .filter(item => filterFunc(item));
    /** sort data **/
    if (this.sortName && this.sortValue) {
      this.listOfDisplayPlts = data.sort((a, b) =>
        this.sortValue === 'ascend'
          ? a[this.sortName!] > b[this.sortName!]
          ? 1
          : -1
          : b[this.sortName!] > a[this.sortName!]
          ? 1
          : -1
      );
    } else {
      this.listOfDisplayPlts = data;
    }
  }

  applyToAll(adj) {
    var today = new Date();
    var milliseconds = today.getMilliseconds();
    let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (thraed, key) {
          let myThread = thraed;
          let newAdj = {...adj};
          newAdj.id = numberAdjs;
          myThread.adj.push(newAdj);
        }
      )
    })
  }

  extend() {
    this.extended = !this.extended;
    if (this.extended) {
      if (!this.ColpasBool) {
        this.pltSpan = 15;
        this.templateSpan = 8;
      } else {
        this.pltSpan = 14;
        this.templateSpan = 7;
      }
      _.forIn(this.pltColumns, function (value: any, key) {
        value.extended = true;
      })
    } else {
      if (this.ColpasBool == true) {
        this.pltSpan = 7;
        this.templateSpan = 14;
      } else {
        this.pltSpan = 9;
        this.templateSpan = 14;
      }
      _.forIn(this.pltColumns, function (value: any, key) {
        if (value.fields == "check" || value.header == "User Tags" || value.fields == "pltId" || value.fields == "pltName" || value.fields == "action") {
          value.extended = true;
        } else {
          value.extended = false;
        }

      })
    }
  }

  changeValue(adj: any, event) {
    adj.value = event.target.value;
  }

  clickButtonPlus(bool, data?: any) {
    if (!bool) {
      this.idPlt = data.id;
      this.addAdjustement = true;
    } else {
      this.addAdjustement = false;
    }
    this.isVisible = true;
  }

  addAdjustmentFromPlusIcon(boolAdj, adjustementType?, adjustement?) {
    if (this.addAdjustement) {
      if (boolAdj) {
        this.isVisible = false;
      }
      let idPlt = this.idPlt;
      var today = new Date();
      var milliseconds = today.getMilliseconds();
      let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
      adjustement.id = numberAdjs;
      if (adjustementType.id == 1) {
        adjustement.linear = false;
        adjustement.value = this.columnPosition;
      } else {
        adjustement.linear = true;
        adjustement.value = adjustementType.abv;
      }
      let newAdj = {...adjustement};
      console.log(adjustement, this.idPlt);
      _.forIn(this.pure.dataTable, function (value, key) {
        _.forIn(value.thread, function (plt, key) {
          console.log()
          if (plt.id == idPlt) {
            console.log("true");
            plt.adj.push(newAdj);
            return;
          }
        })
      })
    } else {
      if (boolAdj) {
        this.isVisible = false;
      }
      let idPlt = this.idPlt;
      var today = new Date();
      var milliseconds = today.getMilliseconds();
      let numberAdjs = today.getMilliseconds() + today.getSeconds() + today.getHours();
      adjustement.id = numberAdjs;
      if (adjustementType.id == 1) {
        adjustement.linear = false;
        adjustement.value = this.columnPosition;
      } else {
        adjustement.linear = true;
        adjustement.value = adjustementType.abv;
      }
      let newAdj = {...adjustement};
      this.adjsArray.push(newAdj);
    }

    this.categorySelectedFromAdjustement = null;
    this.singleValue = null;
    this.columnPosition = null;
    this.linear = false;
  }

  onNotify(tag: any) {
    switch (tag.type) {
      case 'system':
        this.currentSystemTag = tag.tag;
        break;
      case 'user':
        this.currentUserTag = tag.tag;
        break;
    }
  }
}
