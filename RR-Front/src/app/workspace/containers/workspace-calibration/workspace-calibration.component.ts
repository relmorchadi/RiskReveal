import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as _ from 'lodash'
import {NzTableComponent} from "ng-zorro-antd";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {GridsterConfig} from "angular-gridster2";

@Component({
  selector: 'app-workspace-calibration',
  templateUrl: './workspace-calibration.component.html',
  styleUrls: ['./workspace-calibration.component.scss']
})
export class WorkspaceCalibrationComponent implements OnInit {


  visibleIcon: Boolean;

  cols: any[];
  visible: Boolean = false;
  tree: Boolean = false;
  pure: any = {};
  listOfData = [
    {
      key: '1',
      id: '121315',
      address: 'New York No. 1 Lake Park',
      icon: 'icon-history-alt iconYellow'
    },
    {
      key: '2',
      id: '121315',
      address: 'London No. 1 Lake Park',
      icon: 'icon-history-alt iconYellow'
    },
    {
      key: '3',
      id: '121319',
      address: 'Sidney No. 1 Lake Park',
      icon: 'icon-check-circle iconGreen'
    },
    {
      key: '3',
      id: '121319',
      address: 'Sidney No. 1 Lake Park',
      icon: 'icon-lock-alt iconRed'
    },
    {
      key: '3',
      id: '121319',
      address: 'Sidney No. 1 Lake Park',
      icon: 'icon-check-circle iconGreen'
    },
    {
      key: '3',
      id: '121319',
      address: 'Sidney No. 1 Lake Park',
      icon: 'icon-check-circle iconGreen'
    },
    {
      key: '3',
      id: '121319',
      address: 'Sidney No. 1 Lake Park',
      icon: 'icon-history-alt iconYellow'
    }
  ];
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
  categorySelectedFromBasis: string[]= [];
  columnPosition: number;
  selectAllBool = true;
  colunmName = null;
  stockedColumnName: string;
  listOfSelectedValue=[];
  @ViewChild('scrollOne') scrollOne: ElementRef;
  @ViewChild('scrollTwo') scrollTwo: ElementRef;

  constructor() {
    this.cols = [
      {field: 'vin', header: 'Vin'},
      {field: 'year', header: 'Year'},
      {field: 'brand', header: 'Brand'},
      {field: 'color', header: 'Color'}
    ];
    this.pure = {
      category: [
        {
          name: "Base",
          basis: [
            {name: "Base"}
          ],
          showBol: false
        }, {
          name: "Default",
          basis: [
            {name: "bb"},
            {name: "aa"}
          ],
          showBol: false
        }, {
          name: "Client",
          basis: [
            {name: "cc"},
            {name: "dd"}
          ],
          showBol: false
        }, {
          name: "Analyst",
          basis: [
            {name: "ff"},
            {name: "ee"}
          ],
          showBol: false
        }
      ],
      dataTable: [
        {
          name: "Misk net [12233875]",
          thread: [
            {
              id: "122222",
              threadName: "APEQ-ID_GU_CFS PORT",
              icon: 'icon-history-alt iconYellow',
              backgoundIcon: "deselected"
            }, {
              id: "122222",
              threadName: "APEQ-ID_GU_CFS PORT",
              icon: 'icon-history-alt iconYellow',
              backgoundIcon: "deselected"
            }, {
              id: "122222",
              threadName: "APEQ-ID_GU_CFS PORT",
              icon: 'icon-history-alt iconYellow',
              backgoundIcon: "deselected"
            },
            {
              id: "122223",
              threadName: "APEQ-ID_GU_LMF1.T1",
              icon: 'icon-history-alt iconYellow',
              backgoundIcon: "deselected"
            },
            {
              id: "122224",
              threadName: "APEQ-ID_GU_LMF1.T11687",
              icon: 'icon-check-circle iconGreen',
              backgoundIcon: "deselected"
            }

          ]
        },
        {
          name: "Misk net [12233895]",
          thread: [
            {
              id: "122252", threadName: "APEQ-ID_GULM", icon: 'icon-lock-alt iconRed',
              backgoundIcon: "deselected"
            },{
              id: "122252", threadName: "APEQ-ID_GULM", icon: 'icon-lock-alt iconRed',
              backgoundIcon: "deselected"
            },

          ]
        },
        {
          name: "CFS PORT MAR18 [12233895]",
          thread: [
            {
              id: "12299892", threadName: "Apk lap okol Pm 1", icon: 'icon-history-alt iconYellow',
              backgoundIcon: "deselected"
            },{
              id: "12299892", threadName: "Apk lap okol Pm 1", icon: 'icon-history-alt iconYellow',
              backgoundIcon: "deselected"
            }

          ]
        },
      ]
    }
  }

  ngOnInit() {
    this.getAllBasises();
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

  changeBackgroud(a) {
    let o = 0;
    let x;
    let i;
    _.forIn(this.pure.dataTable, function (value, key) {
      if (_.findIndex(value.thread, a) != -1) {
        o = i;
        x = _.findIndex(value.thread, a);
        if (value.thread[x].backgoundIcon == "selected")
          value.thread[x].backgoundIcon = "deselected";
        else
          value.thread[x].backgoundIcon = "selected";
      }
    })

  }

  setBackgroud(a) {

    if (a == "selected")
      return "#FCF9D6";
    else
      return "#fff";
  }

  selectAllThread(selected) {
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (value, key) {
        value.backgoundIcon = selected;
      })

    })
    this.selectAllBool = !this.selectAllBool;
  }

  feedColunmName(category, nameOfColumn) {

    if (category == "Default") {
      return;
    } else {
      if (this.colunmName == nameOfColumn) {
        console.log("already feeded!")
        this.colunmName = null;
      } else {
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

  hideCatgegory(names: string) {
    console.log(names);
      this.hideAllCategories();
      for (let i = 0; i < names.length; i++) {
        let a = _.findIndex(this.pure.category, function (o: any) {
          return o.name == names[i]
        });
        console.log(a);
        this.pure.category[a].showBol=true;
        // _.set(this.pure.category[a], 'showBol', true);
    }

  }

  hideAllCategories() {
    _.forIn(this.pure.category, function (value,key) {
        _.set(value, 'showBol', false);
      }
    )
  }
  feedCatgegoryNameForHide(name){
    let a = _.findIndex(this.pure.category, function (o: any) {
      return o.name == name
    });
    this.pure.category[a].showBol=false;
    for (let i=0;i<this.listOfSelectedValue.length;i++){
      if(this.listOfSelectedValue[i]==name){
        this.listOfSelectedValue.splice(i,1);
      }
    }
  }

  updateScroll(){
    const scrollOne = this.scrollOne.nativeElement as HTMLElement;
    const scrollTwo = this.scrollTwo.nativeElement as HTMLElement;
    scrollTwo.scrollTop= scrollOne.scrollTop;
  }

  updateScroll2() {
    const scrollOne = this.scrollOne.nativeElement as HTMLElement;
    const scrollTwo = this.scrollTwo.nativeElement as HTMLElement;
     scrollOne.scrollTop=scrollTwo.scrollTop;
  }

  deleteColumn(name: string, name2: string) {
    console.log(name,name2,"here");
    let o=_.findIndex(this.pure.category,function (o:any) {
      return o.name==name
    });
    console.log(o,"category");
    let a=_.findIndex(this.pure.category[o].basis,function (o:any) {
      return o.name==name2
    });
    console.log(a,"basis");
    this.pure.category[o].basis.splice(a,1);
  }
}
