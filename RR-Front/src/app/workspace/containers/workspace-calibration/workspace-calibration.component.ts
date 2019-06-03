import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as _ from 'lodash'
import {fromEvent, Subscription} from "rxjs";
import {filter, switchMap} from "rxjs/operators";
import {DndDropEvent, DropEffect} from "ngx-drag-drop";


@Component({
  selector: 'app-workspace-calibration',
  templateUrl: './workspace-calibration.component.html',
  styleUrls: ['./workspace-calibration.component.scss']
})
export class WorkspaceCalibrationComponent implements OnInit {
  inputValue;
  size;
  disabled;

  visibleIcon: Boolean;

  cols: any[];
  adjsArray: any[]=[];
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
  singleValue: any = "none";
  basises: any[];
  categorySelectedFromAdjustement: any;
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
  onHoverIcon: any;
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
  tagSpan: number = 3;
  pltSpan: number = 7;
  templateSpan: number = 14;
  pltSelectionSpan;
  allAdjsArray = [];
  extended: boolean = false;
  idPlt: string = null;
  AdjustementType: any[];
  categorySelected: string = "Selected Category";
  listOfPlts: Array<{
    pltId: number;
    systemTags: any;
    userTags: any;
    pathId: number;
    pltName: string;
    peril: string;
    regionPerilCode: string;
    regionPerilName: string;
    selected: boolean;
    grain: string;
    vendorSystem: string;
    rap: string;
    d: boolean;
    note: boolean;
    checked: boolean;
    [key: string]: any;
  }> = [
    {
      pltId: 1,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 1,
      pltName: "NATC-USM_RL_Imf.T1",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: false
    },
    {
      pltId: 2,
      systemTags: [],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T2",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: false,
      checked: true
    },
    {
      pltId: 3,
      systemTags: [{tagId: 1}, {tagId: 5}, {tagId: 7}],
      userTags: [{tagId: 3}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T3",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: false,
      checked: false
    },
    {
      pltId: 4,
      systemTags: [],
      userTags: [],
      pathId: 4,
      pltName: "NATC-USM_RL_Imf.T4",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: true
    },
    {
      pltId: 5,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 5}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 5,
      pltName: "NATC-USM_RL_Imf.T5",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: false,
      checked: false
    },
    {
      pltId: 6,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 1,
      pltName: "NATC-USM_RL_Imf.T6",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 7,
      systemTags: [{tagId: 5}, {tagId: 7}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T7",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 8,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T8",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: true
    },
    {
      pltId: 9,
      systemTags: [{tagId: 1}, {tagId: 5}, {tagId: 3}],
      userTags: [{tagId: 2}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T9",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: false,
      checked: false
    },
    {
      pltId: 10,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 2,
      pltName: "NATC-USM_RL_Imf.T10",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: false
    },
    {
      pltId: 11,
      systemTags: [],
      userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      pathId: 5,
      pltName: "NATC-USM_RL_Imf.T11",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    },
    {
      pltId: 12,
      systemTags: [{tagId: 5}, {tagId: 2}, {tagId: 7}],
      userTags: [{tagId: 2}, {tagId: 3}],
      pathId: 3,
      pltName: "NATC-USM_RL_Imf.T12",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: false,
      note: true,
      checked: true
    },
    {
      pltId: 13,
      systemTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
      userTags: [{tagId: 1}, {tagId: 2}],
      pathId: 4,
      pltName: "NATC-USM_RL_Imf.T13",
      peril: "TC",
      regionPerilCode: "NATC-USM",
      regionPerilName: "North Atlantic",
      selected: false,
      grain: "liberty-NAHU",
      vendorSystem: "RMS RiskLink",
      rap: "North Atlantic",
      d: true,
      note: true,
      checked: false
    }
  ];
  listOfDisplayPlts: Array<{
    pltId: number;
    systemTags: [];
    userTags: [];
    pathId: number; pltName: string;
    peril: string;
    regionPerilCode: string;
    regionPerilName: string;
    selected: boolean;
    grain: string;
    vendorSystem: string;
    rap: string;
    d: boolean;
    note: boolean;
    checked: boolean;
    [key: string]: any;
  }> = [
    ...this.listOfPlts
  ];
  addAdjustement: boolean;
  pltColumns = [
    {fields: 'check', header: '', width: '1%', sorted: false, filtred: false, icon: null, extended: true},
    {fields: '', header: 'User Tags', width: '10%', sorted: false, filtred: false, icon: null, extended: true},
    {fields: 'pltId', header: 'PLT ID', width: '12%', sorted: true, filtred: true, icon: null, extended: true},
    {fields: 'pltName', header: 'PLT Name', width: '14%', sorted: true, filtred: true, icon: null, extended: true},
    {fields: 'peril', header: 'Peril', width: '7%', sorted: true, filtred: true, icon: null, extended: false},
    {
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '13%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '13%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {fields: 'grain', header: 'Grain', width: '9%', sorted: true, filtred: true, icon: null, extended: false},
    {
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '11%',
      sorted: true,
      filtred: true,
      icon: null,
      extended: false
    },
    {fields: 'rap', header: 'RAP', width: '9%', sorted: true, filtred: true, icon: null, extended: false},
    {fields: 'action', header: '', width: '3%', sorted: false, filtred: false, icon: "icon-focus-add", extended: true},
    {fields: 'action', header: '', width: '3%', sorted: false, filtred: false, icon: "icon-note", extended: true}
  ]
  ColpasBool: boolean = true;
  linear: boolean = false;
  private currentDraggableEvent: DragEvent;

  constructor() {
    this.AdjustementType = [
      {id: 0, name: "none", abv: false},
      {id: 1, name: "Linear", abv: false},
      {id: 2, name: "Event Driven", abv: "Event Driven"},
      {id: 3, name: "Return Period Banding Severity (EEF)", abv: "RP (EEF)"},
      {id: 4, name: "Return Period Banding Severity (OEP)", abv: "RP (OEP)"},
      {id: 4, name: "Frequency (EEF)", abv: "Freq (EEF)"},
      {id: 4, name: "CAT XL", abv: "CAT XL"},
      {id: 4, name: "Quota Share", abv: "QS"}
    ];
    this.pure = {
      category: [
        {
          name: "Base",
          basis: [],
          showBol: true

        }, {
          name: "Default",
          basis: [],
          showBol: true,
          width: '10%'
        }, {
          name: "Client",
          basis: [],
          showBol: true
        }, {
          name: "Inuring",
          basis: [],
          showBol: true,
          width: '10%'
        }, {
          name: "Post-Inuring ",
          basis: [],
          showBol: true,
        },
      ],
      dataTable: [
        {
          name: "Misk net [12233875]",
          thread: [
            {
              id: "122232",
              threadName: "APEQ-ID_GU_CFS PORT 1",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              locked: false,
              adj: [],
              systemTags: [{tagId: 6}, {tagId: 7}],
              userTags: [{tagId: 1}, {tagId: 2}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            }, {
              id: "122242",
              threadName: "APEQ-ID_GU_CFS PORT 2",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              locked: false,
              adj: [],
              systemTags: [{tagId: 1}],
              userTags: [{tagId: 1}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            }, {
              id: "122252",
              threadName: "APEQ-ID_GU_CFS PORT 3",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              locked: false,
              adj: [],
              systemTags: [{tagId: 2}, {tagId: 6}, {tagId: 1}],
              userTags: [{tagId: 1}, {tagId: 2}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            },
            {
              id: "122263",
              threadName: "APEQ-ID_GU_LMF1.T1",
              icon: 'icon-history-alt iconYellow',
              checked: false,
              locked: false,
              adj: [],
              systemTags: [{tagId: 3}, {tagId: 5}],
              userTags: [{tagId: 2}, {tagId: 1}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            },
            {
              id: "122274",
              threadName: "APEQ-ID_GU_LMF1.T11687",
              icon: 'icon-check-circle iconGreen',
              checked: false,
              locked: false,
              adj: [],
              systemTags: [{tagId: 3}, {tagId: 4}, {tagId: 2}],
              userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            }

          ]
        },
        {
          name: "Misk net [12233895]",
          thread: [
            {
              id: "122282", threadName: "APEQ-ID_GULM 1", icon: 'icon-lock-alt iconRed',
              checked: false,
              locked: true,
              adj: [],
              systemTags: [{tagId: 3}, {tagId: 6}, {tagId: 7}],
              userTags: [{tagId: 2}, {tagId: 3}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            }, {
              id: "122292", threadName: "APEQ-ID_GULM 2", icon: 'icon-lock-alt iconRed',
              checked: false,
              locked: true,
              adj: [],
              systemTags: [{tagId: 3}, {tagId: 4}, {tagId: 6}],
              userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            },

          ]
        },
        {
          name: "CFS PORT MAR18 [12233895]",
          thread: [
            {
              id: "12299192", threadName: "Apk lap okol Pm 1", icon: 'icon-history-alt iconYellow',
              checked: false,
              locked: false,
              adj: [],
              systemTags: [{tagId: 4}, {tagId: 6}, {tagId: 3}],
              userTags: [{tagId: 1}, {tagId: 3}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            }, {
              id: "12295892", threadName: "Apk lap okol Pm 2", icon: 'icon-history-alt iconYellow',
              checked: false,
              locked: false,
              adj: [],
              systemTags: [{tagId: 7}, {tagId: 4}, {tagId: 5}],
              userTags: [{tagId: 1}, {tagId: 2}],
              peril: "TC",
              regionPerilCode: "NATC-USM",
              regionPerilName: "North Atlantic",
              selected: false,
              grain: "liberty-NAHU",
              vendorSystem: "RMS RiskLink",
              rap: "North Atlantic"
            }

          ]
        },
      ]
    }
    this.systemTags = [
      {tagId: '1', tagName: 'TC', tagColor: '#7bbe31', innerTagContent: '1', innerTagColor: '#a2d16f', selected: false},
      {
        tagId: '2',
        tagName: 'NATC-USM',
        tagColor: '#7bbe31',
        innerTagContent: '2',
        innerTagColor: '#a2d16f',
        selected: false
      },
      {
        tagId: '3',
        tagName: 'Post-Inured',
        tagColor: '#006249',
        innerTagContent: '9',
        innerTagColor: '#4d917f',
        selected: false
      },
      {
        tagId: '4',
        tagName: 'Pricing',
        tagColor: '#009575',
        innerTagContent: '0',
        innerTagColor: '#4db59e',
        selected: false
      },
      {
        tagId: '5',
        tagName: 'Accumulation',
        tagColor: '#009575',
        innerTagContent: '2',
        innerTagColor: '#4db59e',
        selected: false
      },
      {
        tagId: '6',
        tagName: 'Default',
        tagColor: '#06b8ff',
        innerTagContent: '1',
        innerTagColor: '#51cdff',
        selected: false
      },
      {
        tagId: '7',
        tagName: 'Non-Default',
        tagColor: '#f5a623',
        innerTagContent: '0',
        innerTagColor: '#f8c065',
        selected: false
      },
    ];
    this.userTags = [
      {
        tagId: '1',
        tagName: 'Pricing V1',
        tagColor: '#893eff',
        innerTagContent: '1',
        innerTagColor: '#ac78ff',
        selected: false
      },
      {
        tagId: '2',
        tagName: 'Pricing V2',
        tagColor: '#06b8ff',
        innerTagContent: '2',
        innerTagColor: '#51cdff',
        selected: false
      },
      {
        tagId: '3',
        tagName: 'Final Princing',
        tagColor: '#c38fff',
        innerTagContent: '5',
        innerTagColor: '#d5b0ff',
        selected: false
      }
    ];
    this.allAdjsArray = [
      {id: 1, name: 'Missing Exp ', value: 2.1, linear: false, category: "Client", hover: false, idAdjustementType: 5},
      {id: 2, name: 'Port. Evo ', value: 2.1, linear: false, category: "Base", hover: false, idAdjustementType: 6},
      {id: 3, name: 'ALAE ', value: 1.75, linear: false, category: "Client", hover: false, idAdjustementType: 7},
      {
        id: 4,
        name: 'Model Calib ',
        value: "RPB (EEF)",
        linear: true,
        category: "Base",
        hover: false,
        idAdjustementType: 8
      }
    ]
  }

  ngOnInit() {
    /** scroll principe ****/
    const scrollOne = this.scrollOne.nativeElement as HTMLElement;
    const scrollTwo = this.scrollTwo.nativeElement as HTMLElement;
    let scroll2Event$ = fromEvent(scrollTwo, 'scroll');
    let scroll1Event$ = fromEvent(scrollOne, 'scroll');
    let supscription1 = scroll2Event$.pipe(
      filter(() => this.divIn === true)
    ).subscribe(
      value => {
        scrollOne.scrollTop = scrollTwo.scrollTop;
      }
    )
    let supscription2 = scroll1Event$.pipe(
      filter(() => this.divIn === false)
    ).subscribe(
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
    if (inder) {
      this.indereterminate = true;
    } else {
      this.indereterminate = false;
    }
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

  hideCatgegory(names: string) {
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
    ).subscribe(
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

  generateId() {
    let lenght = 0;
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (thraed, key) {
          lenght = lenght + thraed.adj.length;
        }
      )
    })
    return lenght;
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

  onMouseHover(threadIndex) {
    console.log("test", threadIndex);
  }

  indeterminate() {
    _.forIn(this.pure.dataTable, function (value, key) {
      _.forIn(value.thread, function (plt, key) {
          if (plt.checked) {
            return true;
          }
        }
      )
    })
    return false;
  }

  /*
    checkedAll() {
      _.forIn(this.pure.dataTable, function (value, key) {
        _.forIn(value.thread, function (plt, key) {
            if (!plt.checked) {
             return false;
            }
          }
        )
      })
      return true;
    }*/

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

  haveTagUser(thread) {

    let currenUsreTag = this.currentUserTag;
    let number = _.findIndex(thread.userTags, function (o: any) {
      return o.tagId == currenUsreTag;
    });
    if (number < 0) {
      return false;
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

  Colpa() {
    this.ColpasBool = !this.ColpasBool;
    console.log(this.extended, "extended1");
    console.log(this.ColpasBool, "ColpasBool1");
    if (this.ColpasBool == true) {
      if (this.extended) {
        this.pltSpan = 14;
        this.templateSpan = 7;
      } else {
        this.pltSpan = 7;
        this.templateSpan = 14;
      }
    } else {
      if (this.extended) {
        this.pltSpan = 14;
        this.templateSpan = 9;
      } else {
        this.pltSpan = 9;
        this.templateSpan = 14;
      }
    }

  }

  exetende() {
    this.extended = !this.extended;
    console.log(this.extended, "extended2");
    console.log(this.ColpasBool, "ColpasBool2");
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
        this.templateSpan = 15;
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

    this.categorySelectedFromAdjustement=null;
    this.singleValue=null;
    this.columnPosition=null;
    this.linear=false;
  }
}
