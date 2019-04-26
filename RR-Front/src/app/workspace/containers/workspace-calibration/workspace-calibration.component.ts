import {Component, OnInit} from '@angular/core';
import * as _ from 'lodash'

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
  categorySelectedFromBasis: any = " ";
  columnPosition:number;


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
          ]
        }, {
          name: "Default",
          basis: [
            {name: "bb"},
            {name: "aa"}
          ]
        }, {
          name: "Client",
          basis: [
            {name: "cc"},
            {name: "dd"}
          ]
        }, {
          name: "Analyst",
          basis: [
            {name: "ff"},
            {name: "ee"}
          ]
        }
      ],
      dataTable: [
        {
          name: "Misk net [12233875]",
          thread: [
            {
              id: "122222",
              threadName: "New York No. 1 Lake Park",
              icon: 'icon-history-alt iconYellow'
            },
            {
              id: "122223",
              threadName: "New York No. 1 Lake Park",
              icon: 'icon-history-alt iconYellow'
            },
            {
              id: "122224",
              threadName: "New York No. 1 Lake Park",
              icon: 'icon-check-circle iconGreen'
            }

          ]
        },
        {
          name: "Misk net [12233895]",
          thread: [
            {id: "122252", threadName: "New York No. 1 Lake Park", icon: 'icon-lock-alt iconRed'},
            {id: "122252", threadName: "New York No. 1 Lake Park", icon: 'icon-history-alt iconYellow'},
            {id: "122272", threadName: "Apk lap okol Pm 1", icon: 'icon-check-circle iconGreen'}

          ]
        },
        {
          name: "CFS PORT MAR18 [12233895]",
          thread: [
            {id: "12299892", threadName: "Apk lap okol Pm 1", icon: 'icon-history-alt iconYellow'},
            {id: "122222787", threadName: "Apk lap okol Pm 1", icon: 'icon-lock-alt iconRed'}

          ]
        },
      ]
    }
  }

  ngOnInit() {
    this.getAllBasises();
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
    this.pure.category[index].basis.splice(this.columnPosition-1, 0, {name: a});
    this.isVisible = false;
  }
  addColumnToTable(a, b){
    let index = _.findIndex(this.pure.category, {name: b});
    //this.pure.category[index].basis.push({name: a})
    this.pure.category[index].basis.splice(this.columnPosition-1, 0, {name: a});
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
    this.columnPosition=this.pure.category[index].basis.length+1;

  }

  selectCategory(p) {
    let index = _.findIndex(this.pure.category, {name: p});
    this.columnPosition=this.pure.category[index].basis.length+1;
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
    console.log("test")
    this.visibleIcon = true;
  }

}
