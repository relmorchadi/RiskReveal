export const ADJUSTMENT_TYPE = [
  {id: 0, name: "none", abv: false},
  {id: 1, name: "Linear", abv: false},
  {id: 2, name: "Event Driven", abv: "Event Driven"},
  {id: 3, name: "Return Period Banding Severity (EEF)", abv: "RP (EEF)"},
  {id: 4, name: "Return Period Banding Severity (OEP)", abv: "RP (OEP)"},
  {id: 4, name: "Frequency (EEF)", abv: "Freq (EEF)"},
  {id: 4, name: "CAT XL", abv: "CAT XL"},
  {id: 4, name: "Quota Share", abv: "QS"}
];
export const PURE = {
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
        },
        {
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
        },
        {
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
        },
        {
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
        },
        {
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
};
export const SYSTEM_TAGS = [
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
export const USER_TAGS = [
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
export const ALL_ADJUSTMENTS = [
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
];
export const PLT_COLUMNS = [
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
];
export const LIST_OF_PLTS: Array<{
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
export const LIST_OF_DISPLAY_PLTS: Array<{
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
  ...LIST_OF_PLTS
];
export const DATA = [
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
  }
];
