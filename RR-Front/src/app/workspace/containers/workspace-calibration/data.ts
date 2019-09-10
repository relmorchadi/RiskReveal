export const ADJUSTMENT_TYPE = [
  {id: 6, name: "CAT XL", abv: "CAT XL"},
  {id: 2, name: "Event Driven", abv: "Event Driven"},
  {id: 5, name: "Frequency (EEF)", abv: "Freq (EEF)"},
  {id: 1, name: "Linear", abv: false},
  {id: 3, name: "Return Period Banding Severity (EEF)", abv: "RP (EEF)"},
  {id: 4, name: "Return Period Banding Severity (OEP)", abv: "RP (OEP)"},
  {id: 7, name: "Quota Share", abv: "QS"}
];
// export const PURE = {
//   category: [
//     {
//       name: "Base",
//       basis: [],
//       showBol: true
//
//     }, {
//       name: "Default",
//       basis: [],
//       showBol: true,
//       width: '10%'
//     }, {
//       name: "Client",
//       basis: [],
//       showBol: true
//     }, {
//       name: "Inuring",
//       basis: [],
//       showBol: true,
//       width: '10%'
//     }, {
//       name: "Post-Inuring ",
//       basis: [],
//       showBol: true,
//     },
//   ],
//   dataTable: [
//     {
//       name: "Misk net [12233875]",
//       thread: [
//         {
//           id: "122232",
//           threadName: "APEQ-ID_GU_CFS PORT 1",
//           icon: 'icon-history-alt iconYellow',
//           checked: true,
//           locked: false,
//           adj: [],
//           systemTags: [{tagId: 6}, {tagId: 7}],
//           userTags: [{tagId: 1}, {tagId: 2}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'in progress'
//         },
//         {
//           id: "122242",
//           threadName: "APEQ-ID_GU_CFS PORT 2",
//           icon: 'icon-history-alt iconYellow',
//           checked: false,
//           locked: false,
//           adj: [],
//           systemTags: [{tagId: 1}],
//           userTags: [{tagId: 1}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'in progress'
//         },
//         {
//           id: "122252",
//           threadName: "APEQ-ID_GU_CFS PORT 3",
//           icon: 'icon-history-alt iconYellow',
//           checked: false,
//           locked: false,
//           adj: [],
//           systemTags: [{tagId: 2}, {tagId: 6}, {tagId: 1}],
//           userTags: [{tagId: 1}, {tagId: 2}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'in progress'
//         },
//         {
//           id: "122263",
//           threadName: "APEQ-ID_GU_LMF1.T1",
//           icon: 'icon-history-alt iconYellow',
//           checked: false,
//           locked: false,
//           adj: [],
//           systemTags: [{tagId: 3}, {tagId: 5}],
//           userTags: [{tagId: 2}, {tagId: 1}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'in progress'
//         },
//         {
//           id: "122274",
//           threadName: "APEQ-ID_GU_LMF1.T11687",
//           icon: 'icon-check-circle iconGreen',
//           checked: false,
//           locked: false,
//           adj: [],
//           systemTags: [{tagId: 3}, {tagId: 4}, {tagId: 2}],
//           userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'checked'
//         }
//
//       ]
//     },
//     {
//       name: "Misk net [12233895]",
//       thread: [
//         {
//           id: "122282", threadName: "APEQ-ID_GULM 1", icon: 'icon-lock-alt iconRed',
//           checked: false,
//           locked: true,
//           adj: [],
//           systemTags: [{tagId: 3}, {tagId: 6}, {tagId: 7}],
//           userTags: [{tagId: 2}, {tagId: 3}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'locked'
//         },
//         {
//           id: "122292", threadName: "APEQ-ID_GULM 2", icon: 'icon-lock-alt iconRed',
//           checked: false,
//           locked: true,
//           adj: [],
//           systemTags: [{tagId: 3}, {tagId: 4}, {tagId: 6}],
//           userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'locked'
//         },
//
//       ]
//     },
//     {
//       name: "CFS PORT MAR18 [12233895]",
//       thread: [
//         {
//           id: "12299192", threadName: "Apk lap okol Pm 1", icon: 'icon-history-alt iconYellow',
//           checked: false,
//           locked: false,
//           adj: [],
//           systemTags: [{tagId: 4}, {tagId: 6}, {tagId: 3}],
//           userTags: [{tagId: 1}, {tagId: 3}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'in progress'
//         },
//         {
//           id: "12295892", threadName: "Apk lap okol Pm 2", icon: 'icon-history-alt iconYellow',
//           checked: false,
//           locked: false,
//           adj: [],
//           systemTags: [{tagId: 7}, {tagId: 4}, {tagId: 5}],
//           userTags: [{tagId: 1}, {tagId: 2}],
//           peril: "TC",
//           regionPerilCode: "NATC-USM",
//           regionPerilName: "North Atlantic",
//           selected: false,
//           grain: "liberty-NAHU",
//           vendorSystem: "RMS RiskLink",
//           rap: "North Atlantic",
//           status: 'in progress'
//         }
//
//       ]
//     },
//   ]
// };
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
export const ADJUSTMENTS_ARRAY = [
  {
    "id": 1,
    "adjustmentBasis": "Wind Pool",
    "category": "Base",
    "name": "Wind Pool",
    "description": "An adjustment to be applied on the basis that wind pools may relieve or contribute to losses.",
    "exposureFlag": "UNCAPPED",
    "sequence": 13,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 1,
    "favored": false
  },
  {
    "id": 2,
    "adjustmentBasis": "Allocated Loss Adjustment Expense (ALAE)",
    "category": "Client",
    "name": "ALAE",
    "description": "An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.",
    "exposureFlag": "UNCAPPED",
    "sequence": 7,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 2,
    "favored": false
  },
  {
    "id": 4,
    "adjustmentBasis": "Exposure Adjustment (Portfolio Evolution)",
    "category": "Base",
    "name": "Portfolio Evolution",
    "description": "An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.",
    "exposureFlag": "UNCAPPED",
    "sequence": 1,
    "isExposureGrowth": true,
    "value": "2,1",
    "idAdjustementType": 4,
    "favored": false
  },
  {
    "id": 5,
    "adjustmentBasis": "Missing Exposure",
    "category": "Base",
    "name": "Missing Exposures",
    "description": "An adjustment to be applied on the basis that exposure is believed to be missing from the client's data.",
    "exposureFlag": "UNCAPPED",
    "sequence": 2,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 5,
    "favored": false
  },
  {
    "id": 6,
    "adjustmentBasis": "Cedant Historical Claims",
    "category": "Client",
    "name": "Cedant Claims",
    "description": "An adjustment to be applied on the basis of client claims data (contextualization).",
    "exposureFlag": "CAP",
    "sequence": 11,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 6,
    "favored": false
  },
  {
    "id": 7,
    "adjustmentBasis": "Demand Surge Or Post-Loss Amplification",
    "category": "Base",
    "name": "Post-Loss Amp",
    "description": "An adjustment to be applied on the basis that losses are amplified by post event factors (e.g. lumber costs increase).",
    "exposureFlag": "CAP",
    "sequence": 15,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 7,
    "favored": false
  },
  {
    "id": 8,
    "adjustmentBasis": "Fire Following Earthquake",
    "category": "Base",
    "name": "Fire Following",
    "description": "An adjustment to be applied on the basis that fire losses are not reflective of the SCOR or analysts' views.",
    "exposureFlag": "CAP",
    "sequence": 17,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 8,
    "favored": false
  },
  {
    "id": 9,
    "adjustmentBasis": "Storm Surge",
    "category": "Base",
    "name": "Storm Surge",
    "description": "An adjustment to be applied on the basis that flooding losses are not reflective of the SCOR or analysts' views.",
    "exposureFlag": "CAP",
    "sequence": 16,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 9,
    "favored": false
  },
  {
    "id": 10,
    "adjustmentBasis": "Hours Clause",
    "category": "Client",
    "name": "Hours Clause",
    "description": "An adjustment to be applied on the basis that an hours clause has modified losses.",
    "exposureFlag": "CAP",
    "sequence": 12,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 10,
    "favored": false
  },
  {
    "id": 14,
    "adjustmentBasis": "Cedant \"Quality Index\" (QI)",
    "category": "Client",
    "name": "Cedant QI",
    "description": "An adjustment to be applied on the basis of a client rating index.",
    "exposureFlag": "CAP",
    "sequence": 3,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 14,
    "favored": false
  },
  {
    "id": 15,
    "adjustmentBasis": "Hazardous Waste Removal",
    "category": "Client",
    "name": "Haz. Waste Removal",
    "description": "An adjustment to be applied on the basis of risk being under or overstated.",
    "exposureFlag": "CAP",
    "sequence": 21,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 15,
    "favored": false
  },
  {
    "id": 16,
    "adjustmentBasis": "Loss Inflation due to Political Pressure",
    "category": "Client",
    "name": "Political Pressure",
    "description": "An adjustment to be applied on the basis of risk being under or overstated.",
    "exposureFlag": "CAP",
    "sequence": 23,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 16,
    "favored": false
  },
  {
    "id": 17,
    "adjustmentBasis": "Indirect Business Interruption",
    "category": "Client",
    "name": "Indirect BI",
    "description": "An adjustment to be applied on the basis of business interruption risk being under or overstated.",
    "exposureFlag": "CAP",
    "sequence": 14,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 17,
    "favored": false
  },
  {
    "id": 18,
    "adjustmentBasis": "Replacement Values (RV) Underestimation",
    "category": "Client",
    "name": "RV Underestimation",
    "description": "An adjustment to be applied on the basis of replacement values believed to be understated.",
    "exposureFlag": "UNCAPPED",
    "sequence": 8,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 18,
    "favored": false
  },
  {
    "id": 19,
    "adjustmentBasis": "Replacement Values (RV) Overestimation",
    "category": "Client",
    "name": "RV Overestimation",
    "description": "An adjustment to be applied on the basis of replacement values believed to be overstated.",
    "exposureFlag": "UNCAPPED",
    "sequence": 9,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 19,
    "favored": false
  },
  {
    "id": 20,
    "adjustmentBasis": "High % Unknown Primary Modifiers (e.g. Uncertainty Loading)",
    "category": "Client",
    "name": "Unknown Primary Mod.",
    "description": "An adjustment to be applied on the basis of risk being under or overstated.",
    "exposureFlag": "CAP",
    "sequence": 4,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 20,
    "favored": false
  },
  {
    "id": 21,
    "adjustmentBasis": "Missing Critical Primary Characteristics (Unknown Floor Level For Flood)",
    "category": "Client",
    "name": "Missing Primary Char.",
    "description": "An adjustment to be applied on the basis of risk being under or overstated.",
    "exposureFlag": "CAP",
    "sequence": 5,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 21,
    "favored": false
  },
  {
    "id": 24,
    "adjustmentBasis": "Looting",
    "category": "Client",
    "name": "Looting",
    "description": "An adjustment to be applied on the basis of risk being under or overstated.",
    "exposureFlag": "CAP",
    "sequence": 24,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 24,
    "favored": false
  },
  {
    "id": 25,
    "adjustmentBasis": "Other Consequential Losses",
    "category": "Client",
    "name": "Other Conseq. Losses",
    "description": "An adjustment to be applied on the basis of risk being under or overstated.",
    "exposureFlag": "CAP",
    "sequence": 22,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 25,
    "favored": false
  },
  {
    "id": 26,
    "adjustmentBasis": "Simple Inuring",
    "category": "Inuring",
    "name": "Simple Inuring",
    "description": "An adjustment representative of Quota Share or Cat XL inuring.",
    "exposureFlag": "CAP",
    "sequence": 27,
    "isExposureGrowth": false,
    "value": "2,1",
    "idAdjustementType": 26,
    "favored": false
  }
].map(item => ({...item, linear: false, hover: false}));
export const PLT_COLUMNS = [
  {
    sortDir: 1,
    fields: 'checkbox',
    header: '',
    width: '43',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'checkbox',
    style: 'border: none !important',
    extended: true,
    frozen: true,
  },
  {
    sortDir: 1,
    fields: 'userTags',
    header: 'User Tags',
    width: '80',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'checkbox',
    style: 'border: none !important',
    extended: true,
    frozen: true,
  },
  {
    sortDir: 1,
    fields: 'pltId',
    header: 'PLT ID',
    width: '80',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: true,
    frozen: true,
  },
  {
    sortDir: 1,
    fields: 'pltName',
    header: 'PLT Name',
    width: '150',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: true,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'peril',
    header: 'Peril',
    width: '80',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'regionPerilCode',
    header: 'Region Peril Code',
    width: '80',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'regionPerilName',
    header: 'Region Peril Name',
    width: '130',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'grain',
    header: 'Grain',
    width: '160',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'vendorSystem',
    header: 'Vendor System',
    width: '90',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'rap',
    header: 'RAP',
    width: '70',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'action',
    header: '',
    width: '25',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: 'icon-ballot_24px',
    type: 'icon',
    style: 'border: none !important',
    extended: true,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'action',
    header: '',
    width: '25',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: 'icon-note',
    type: 'icon',
    style: 'border: none !important',
    extended: true,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'overallLMF',
    header: 'Overall LMF',
    width: '60',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'base',
    header: 'Base',
    width: '260',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'default',
    header: 'Default',
    width: '118',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'client',
    header: 'Client',
    width: '260',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'inuring',
    header: 'Inuring',
    width: '118',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'postInuring',
    header: 'Post-Inuring',
    width: '220',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
];
export const EPM_COLUMNS = [
  {
    sortDir: 1,
    fields: 'checkbox',
    header: '',
    width: '43',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'checkbox',
    style: 'border: none !important',
    extended: true,
    frozen: true,
  },
  {
    sortDir: 1,
    fields: 'userTags',
    header: 'User Tags',
    width: '80',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'checkbox',
    style: 'border: none !important',
    extended: true,
    frozen: true,
  },
  {
    sortDir: 1,
    fields: 'pltId',
    header: 'PLT ID',
    width: '80',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: true,
    frozen: true,
  },
  {
    sortDir: 1,
    fields: 'pltName',
    header: 'PLT Name',
    width: '150',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: true,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'peril',
    header: 'Peril',
    width: '80',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'regionPerilCode',
    header: 'Region Peril Code',
    width: '80',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'regionPerilName',
    header: 'Region Peril Name',
    width: '130',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'grain',
    header: 'Grain',
    width: '160',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'vendorSystem',
    header: 'Vendor System',
    width: '90',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'rap',
    header: 'RAP',
    width: '70',
    dragable: false,
    sorted: true,
    filtred: true,
    icon: null,
    type: 'field',
    style: 'border: none !important',
    extended: false,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'action',
    header: '',
    width: '25',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: 'icon-ballot_24px',
    type: 'icon',
    style: 'border: none !important',
    extended: true,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'action',
    header: '',
    width: '25',
    dragable: false,
    sorted: false,
    filtred: false,
    icon: 'icon-note',
    type: 'icon',
    style: 'border: none !important',
    extended: true,
    frozen: true
  },
  {
    sortDir: 1,
    fields: 'AAL',
    header: 'AAL',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM2',
    header: '2',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM5',
    header: '5',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM10',
    header: '10',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM25',
    header: '25',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM50',
    header: '50',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM100',
    header: '100',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM250',
    header: '250',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM500',
    header: '500',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM1000',
    header: '1000',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM5000',
    header: '5000',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
  {
    sortDir: 1,
    fields: 'EPM10000',
    header: '10000',
    width: '80',
    dragable: true,
    sorted: false,
    filtred: false,
    icon: null,
    type: 'field',
    style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
    extended: true,
    frozen: false
  },
];
export const EPMS = ["AEP", "AEP-TVAR", "OEP", "OEP-TVAR"];
export const UNITS = [
  {id: '3', label: 'Billion'},
  {id: '1', label: 'Thousands'},
  {id: '2', label: 'Million'},
  {id: '4', label: 'Unit'}
];
export const DEPENDENCIES = [
  {id: 1, title: 'ETL', content: 'RDM: CC_IT1607_XYZ_Surplus_R', chip: 'Analysis ID: 149'},
  {id: 2, title: 'PTL', content: 'ID 9867', chip: 'Pure PLT'},
  {id: 2, title: 'PTL', content: 'ID 9888', chip: 'Thead PLT'},
  {id: 2, title: 'PTL', content: 'ID 9901', chip: 'Cloned PLT'}
];
export const SYSTEM_TAGS_MAPPING = {
  grouped: {
    regionPerilCode: 'Region Peril',
    currency: 'Currency',
    sourceModellingVendor: 'Modelling Vendor',
    sourceModellingSystem: 'Model System',
    targetRapCode: 'Target RAP',
    userOccurrenceBasis: 'User Occurence Basis',
    pltType: 'Loss Asset Type',
  },
  nonGrouped: {}
};
export const CURRENCIES = [
  {id: '1', name: 'Euro', label: 'EUR'},
  {id: '2', name: 'Us Dollar', label: 'USD'},
  {id: '3', name: 'Britsh Pound', label: 'GBP'},
  {id: '4', name: 'Canadian Dollar', label: 'CAD'},
  {id: '5', name: 'Moroccan Dirham', label: 'MAD'},
  {id: '5', name: 'Swiss Franc', label: 'CHF'},
  {id: '5', name: 'Saudi Riyal', label: 'SAR'},
  {id: '6', name: 'Bitcoin', label: 'XBT'},
  {id: '7', name: 'Hungarian forint', label: 'HUF'},
  {id: '8', name: 'Singapore Dollars', label: 'SGD'}
];
export const PURE = {
    "SPLTH-000735433": {
      "workspaceId": "17P3398",
      "uwy": 2016,
      "pltId": "SPLTH-000735433",
      "pltName": "EUCS-DE_GR_LMF1.03.T2",
      "peril": "CS",
      "regionPerilCode": "EUCS-DE",
      "regionPerilName": "Europe (Germany)",
      "grain": "AXA-3rdCov_05PA753_Hail_ALL_Client",
      "vendorSystem": "RMS RL",
      "targetRapCode": null,
      "isScorCurrent": "true",
      "isScorDefault": "true",
      "isScorGenerated": "true",
      "project": "P-000007071",
      "pltType": "Thread",
      "projectName": "TEST",
      "creationDate": "11/02/2019",
      "year": 2019,
      "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175309_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-2_ID-SPLTH-000735484_Job-1.bin",
      "sourceModellingVendor": "RMS",
      "sourceModellingSystem": "RiskLink",
      "dataSourceName": "CG1901_DEU_EQportfolio_R",
      "analysisId": "59",
      "currency": null,
      "userOccurrenceBasis": null,
      "xactPublicationDate": null
    },
    "SPLTH-000735434": {
      "workspaceId": "17P3398",
      "uwy": 2016,
      "pltId": "SPLTH-000735434",
      "pltName": "DEFL_GR_DefAdj_LMF0.52.T3",
      "peril": "FL",
      "regionPerilCode": "DEFL",
      "regionPerilName": "Germany",
      "grain": "AXA-3rdCov_05PA753_FL_ENG_Client",
      "vendorSystem": "RMS RL",
      "targetRapCode": null,
      "isScorCurrent": "true",
      "isScorDefault": "true",
      "isScorGenerated": "true",
      "project": "P-000007071",
      "pltType": "Thread",
      "projectName": "TEST",
      "creationDate": "11/02/2019",
      "year": 2019,
      "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175313_RMS-RiskLink_RL17_DEFL_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-438_N-100_P-T-3_ID-SPLTH-000735487_Job-1.bin",
      "sourceModellingVendor": "RMS",
      "sourceModellingSystem": "RiskLink",
      "dataSourceName": "CG1901_DEU_EQportfolio_R",
      "analysisId": "59",
      "currency": null,
      "userOccurrenceBasis": null,
      "xactPublicationDate": null
    },
    "SPLTH-000735435": {
      "workspaceId": "17P3398",
      "uwy": 2016,
      "pltId": "SPLTH-000735435",
      "pltName": "EUET_GR_LMF1.03_RPadj.T2",
      "peril": "WS",
      "regionPerilCode": "EUET",
      "regionPerilName": "Europe",
      "grain": "AXA-3rdCov_05PA753_WS_ENG_Client",
      "vendorSystem": "RMS RL",
      "targetRapCode": null,
      "isScorCurrent": "true",
      "isScorDefault": "true",
      "isScorGenerated": "true",
      "project": "P-000007071",
      "pltType": "Thread",
      "projectName": "TEST",
      "creationDate": "11/02/2019",
      "year": 2019,
      "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175426_RMS-RiskLink_RL17_EUET_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-442_N-100_P-T-2_ID-SPLTH-000735494_Job-1.bin",
      "sourceModellingVendor": "RMS",
      "sourceModellingSystem": "RiskLink",
      "dataSourceName": "CG1901_DEU_EQportfolio_R",
      "analysisId": "59",
      "currency": null,
      "userOccurrenceBasis": null,
      "xactPublicationDate": null
    }
  };
export const ONE_PLT = {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735505",
    "pltName": "EUCS-DE_GR_LMF1.03.T3",
    "peril": "CS",
    "regionPerilCode": "EUCS-DE",
    "regionPerilName": "Europe (Germany)",
    "grain": "AXA-3rdCov_05PA753_Hail_ALL_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [{
      "tagId": 60061,
      "tagName": "ko",
      "tagColor": "#0700cf",
      "pltHeaders": [{"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }, {
      "tagId": 60060,
      "tagName": "tag2",
      "tagColor": "#64ffda",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }],
    "xactPublicationDate": null
  }
export const PLT_DATA = {
      'SPLTH-000735484': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735484',
        pltName: 'EUCS-DE_GR_LMF1.03.T2',
        peril: 'CS',
        regionPerilCode: 'EUCS-DE',
        regionPerilName: 'Europe (Germany)',
        grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175309_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-2_ID-SPLTH-000735484_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [
          {
            tagId: 60059,
            tagName: 'tag1',
            tagColor: '#d81b60',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              }
            ]
          },
          {
            tagId: 60060,
            tagName: 'tag2',
            tagColor: '#64ffda',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              },
              {
                id: 'SPLTH-000735505'
              }
            ]
          }
        ],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'failed',
        newPlt: true,
        EPM: [
          '1700875',
          921266,
          -59
        ],
        calibrate: true,
        toCalibrate: true,
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007830',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'new',
            newPlt: true,
            EPM: [
              '1956614',
              945455,
              75
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735484'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007246',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'in progress',
            newPlt: true,
            EPM: [
              '1599559',
              915888,
              -88
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735484'
          }
        ]
      },
      'SPLTH-000735487': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735487',
        pltName: 'DEFL_GR_DefAdj_LMF0.52.T3',
        peril: 'FL',
        regionPerilCode: 'DEFL',
        regionPerilName: 'Germany',
        grain: 'AXA-3rdCov_05PA753_FL_ENG_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175313_RMS-RiskLink_RL17_DEFL_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-438_N-100_P-T-3_ID-SPLTH-000735487_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [
          {
            tagId: 60059,
            tagName: 'tag1',
            tagColor: '#d81b60',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              }
            ]
          },
          {
            tagId: 60060,
            tagName: 'tag2',
            tagColor: '#64ffda',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              },
              {
                id: 'SPLTH-000735505'
              }
            ]
          }
        ],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'new',
        newPlt: true,
        EPM: [
          '1117882',
          718515,
          30
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735435',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007715',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'in progress',
            newPlt: false,
            EPM: [
              '973763',
              426753,
              40
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735487'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007714',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'locked',
            newPlt: true,
            EPM: [
              '1629828',
              741304,
              84
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735487'
          }
        ]
      },
      'SPLTH-000735494': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735494',
        pltName: 'EUET_GR_LMF1.03_RPadj.T2',
        peril: 'WS',
        regionPerilCode: 'EUET',
        regionPerilName: 'Europe',
        grain: 'AXA-3rdCov_05PA753_WS_ENG_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175426_RMS-RiskLink_RL17_EUET_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-442_N-100_P-T-2_ID-SPLTH-000735494_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [
          {
            tagId: 60059,
            tagName: 'tag1',
            tagColor: '#d81b60',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              }
            ]
          },
          {
            tagId: 60062,
            tagName: 'lol',
            tagColor: '#ef5350',
            pltHeaders: [
              {
                id: 'SPLTH-000735496'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              }
            ]
          },
          {
            tagId: 60060,
            tagName: 'tag2',
            tagColor: '#64ffda',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              },
              {
                id: 'SPLTH-000735505'
              }
            ]
          }
        ],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'failed',
        newPlt: false,
        EPM: [
          '1037580',
          363531,
          -62
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735435',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007153',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'requires regeneration',
            newPlt: true,
            EPM: [
              '1697629',
              347329,
              -78
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735494'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007402',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'requires regeneration',
            newPlt: true,
            EPM: [
              '1669965',
              367284,
              30
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735494'
          }
        ]
      },
      'SPLTH-000735496': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735496',
        pltName: 'EUET_GR_LMF1.03_RPadj.T2',
        peril: 'WS',
        regionPerilCode: 'EUET',
        regionPerilName: 'Europe',
        grain: 'AXA-3rdCov_05PA753_WS_NonEng_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175532_RMS-RiskLink_RL17_EUET_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-442_N-100_P-T-2_ID-SPLTH-000735496_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [
          {
            tagId: 60062,
            tagName: 'lol',
            tagColor: '#ef5350',
            pltHeaders: [
              {
                id: 'SPLTH-000735496'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              }
            ]
          }
        ],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'in progress',
        newPlt: true,
        EPM: [
          '1395838',
          912777,
          31
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735435',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007171',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'new',
            newPlt: false,
            EPM: [
              '1254964',
              927599,
              46
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735496'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007285',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'failed',
            newPlt: true,
            EPM: [
              '1849940',
              773080,
              3
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735496'
          }
        ]
      },
      'SPLTH-000735501': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735501',
        pltName: 'EUEQ-DE_GR_DefAdj_LMF0.82._NonEng.T4',
        peril: 'EQ',
        regionPerilCode: 'EUEQ-DE',
        regionPerilName: 'Europe (Germany)',
        grain: 'AXA-3rdCov_05PA753_EQ_NonEng_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180258_RMS-RiskLink_RL17_EUEQ-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-436_N-100_P-T-4_ID-SPLTH-000735501_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [
          {
            tagId: 60061,
            tagName: 'ko',
            tagColor: '#0700cf',
            pltHeaders: [
              {
                id: 'SPLTH-000735501'
              },
              {
                id: 'SPLTH-000735505'
              }
            ]
          },
          {
            tagId: 60062,
            tagName: 'lol',
            tagColor: '#ef5350',
            pltHeaders: [
              {
                id: 'SPLTH-000735496'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              }
            ]
          },
          {
            tagId: 60060,
            tagName: 'tag2',
            tagColor: '#64ffda',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              },
              {
                id: 'SPLTH-000735505'
              }
            ]
          }
        ],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'locked',
        newPlt: false,
        EPM: [
          '1865114',
          483584,
          14
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735435',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007109',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'in progress',
            newPlt: true,
            EPM: [
              '1991370',
              738203,
              42
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735501'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007236',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'valid',
            newPlt: true,
            EPM: [
              '1693388',
              318097,
              -61
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735501'
          }
        ]
      },
      'SPLTH-000735505': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735505',
        pltName: 'EUCS-DE_GR_LMF1.03.T3',
        peril: 'CS',
        regionPerilCode: 'EUCS-DE',
        regionPerilName: 'Europe (Germany)',
        grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [
          {
            tagId: 60061,
            tagName: 'ko',
            tagColor: '#0700cf',
            pltHeaders: [
              {
                id: 'SPLTH-000735501'
              },
              {
                id: 'SPLTH-000735505'
              }
            ]
          },
          {
            tagId: 60060,
            tagName: 'tag2',
            tagColor: '#64ffda',
            pltHeaders: [
              {
                id: 'SPLTH-000735487'
              },
              {
                id: 'SPLTH-000735484'
              },
              {
                id: 'SPLTH-000735494'
              },
              {
                id: 'SPLTH-000735501'
              },
              {
                id: 'SPLTH-000735505'
              }
            ]
          }
        ],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'in progress',
        newPlt: true,
        EPM: [
          '996423',
          443058,
          -57
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735435',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007436',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'requires regeneration',
            newPlt: true,
            EPM: [
              '1634933',
              220502,
              6
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735505'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007764',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'locked',
            newPlt: false,
            EPM: [
              '1231177',
              792818,
              -8
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735505'
          }
        ]
      },
      'SPLTH-000735507': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735507',
        pltName: 'DEFL_GR_DefAdj_LMF0.52._ENG.T4',
        peril: 'FL',
        regionPerilCode: 'DEFL',
        regionPerilName: 'Germany',
        grain: 'AXA-3rdCov_05PA753_FL_ENG_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180305_RMS-RiskLink_RL17_DEFL_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-438_N-100_P-T-4_ID-SPLTH-000735507_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'in progress',
        newPlt: false,
        EPM: [
          '1045736',
          695213,
          95
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735433',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007701',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'failed',
            newPlt: false,
            EPM: [
              '1632274',
              193837,
              55
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735507'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007422',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'new',
            newPlt: true,
            EPM: [
              '1197568',
              639109,
              37
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735507'
          }
        ]
      },
      'SPLTH-000735510': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000735510',
        pltName: 'DEFL_GR_DefAdj_LMF0.52._NonEng.T4',
        peril: 'YY',
        regionPerilCode: 'DEFL',
        regionPerilName: 'Germany',
        grain: 'AXA-3rdCov_05PA753_FL_NonEng_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180310_RMS-RiskLink_RL17_DEFL_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-438_N-100_P-T-4_ID-SPLTH-000735510_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'new',
        newPlt: false,
        EPM: [
          '1390154',
          334823,
          26
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735434',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007664',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'new',
            newPlt: true,
            EPM: [
              '1937959',
              511833,
              29
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735510'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007661',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'failed',
            newPlt: true,
            EPM: [
              '1550852',
              813394,
              31
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000735510'
          }
        ]
      },
      'SPLTH-000736014': {
        workspaceId: '17P3398',
        uwy: 2016,
        pltId: 'SPLTH-000736014',
        pltName: 'EUET_GR_LMF1.03_RPadj._NonEng_RPeventXL.T4',
        peril: 'WS',
        regionPerilCode: 'EUET',
        regionPerilName: 'Europe',
        grain: 'AXA-3rdCov_05PA753_WS_NonEng_Client',
        vendorSystem: 'RMS RL',
        targetRapCode: null,
        isScorCurrent: 'true',
        isScorDefault: 'true',
        isScorGenerated: 'true',
        project: 'P-000007071',
        pltType: 'Thread',
        projectName: 'TEST',
        creationDate: '11/02/2019',
        year: 2019,
        fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181019-144458_RMS-RiskLink_RL17_EUET_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-442_N-100_P-T-4_ID-SPLTH-000736014_Job-1.bin',
        sourceModellingVendor: 'RMS',
        sourceModellingSystem: 'RiskLink',
        dataSourceName: 'CG1901_DEU_EQportfolio_R',
        analysisId: '59',
        currency: null,
        userOccurrenceBasis: null,
        userTags: [],
        xactPublicationDate: null,
        selected: false,
        visible: true,
        tagFilterActive: false,
        opened: false,
        status: 'failed',
        newPlt: true,
        EPM: [
          '1267921',
          257738,
          34
        ],
        calibrate: true,
        toCalibrate: true,
        pureId: 'SPLTH-000735434',
        threads: [
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007317',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'new',
            newPlt: true,
            EPM: [
              '1750363',
              144490,
              51
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000736014'
          },
          {
            workspaceId: '17P3398',
            uwy: 2016,
            pltId: 'SPLTH-0007707',
            pltName: 'EUCS-DE_GR_LMF1.03.T3',
            peril: 'CS',
            regionPerilCode: 'EUCS-DE',
            regionPerilName: 'Europe (Germany)',
            grain: 'AXA-3rdCov_05PA753_Hail_ALL_Client',
            vendorSystem: 'RMS RL',
            targetRapCode: null,
            isScorCurrent: 'true',
            isScorDefault: 'true',
            isScorGenerated: 'true',
            project: 'P-000007071',
            pltType: 'Thread',
            projectName: 'TEST',
            creationDate: '11/02/2019',
            year: 2019,
            fileName: 'T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin',
            sourceModellingVendor: 'RMS',
            sourceModellingSystem: 'RiskLink',
            dataSourceName: 'CG1901_DEU_EQportfolio_R',
            analysisId: '59',
            currency: null,
            userOccurrenceBasis: null,
            userTags: [
              {
                tagId: 60061,
                tagName: 'ko',
                tagColor: '#0700cf',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              },
              {
                tagId: 60060,
                tagName: 'tag2',
                tagColor: '#64ffda',
                pltHeaders: [
                  {
                    id: 'SPLTH-000735487'
                  },
                  {
                    id: 'SPLTH-000735484'
                  },
                  {
                    id: 'SPLTH-000735494'
                  },
                  {
                    id: 'SPLTH-000735501'
                  },
                  {
                    id: 'SPLTH-000735505'
                  }
                ]
              }
            ],
            xactPublicationDate: null,
            selected: false,
            visible: true,
            tagFilterActive: false,
            opened: false,
            status: 'failed',
            newPlt: false,
            EPM: [
              '1091461',
              647125,
              51
            ],
            calibrate: true,
            toCalibrate: true,
            pureId: 'SPLTH-000736014'
          }
        ]
      }
  }
export const API_RESPONSE = {
  "plts": [{
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735484",
    "pltName": "EUCS-DE_GR_LMF1.03.T2",
    "peril": "CS",
    "regionPerilCode": "EUCS-DE",
    "regionPerilName": "Europe (Germany)",
    "grain": "AXA-3rdCov_05PA753_Hail_ALL_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175309_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-2_ID-SPLTH-000735484_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [{
      "tagId": 60059,
      "tagName": "tag1",
      "tagColor": "#d81b60",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}]
    }, {
      "tagId": 60060,
      "tagName": "tag2",
      "tagColor": "#64ffda",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735487",
    "pltName": "DEFL_GR_DefAdj_LMF0.52.T3",
    "peril": "FL",
    "regionPerilCode": "DEFL",
    "regionPerilName": "Germany",
    "grain": "AXA-3rdCov_05PA753_FL_ENG_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175313_RMS-RiskLink_RL17_DEFL_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-438_N-100_P-T-3_ID-SPLTH-000735487_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [{
      "tagId": 60059,
      "tagName": "tag1",
      "tagColor": "#d81b60",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}]
    }, {
      "tagId": 60060,
      "tagName": "tag2",
      "tagColor": "#64ffda",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735494",
    "pltName": "EUET_GR_LMF1.03_RPadj.T2",
    "peril": "WS",
    "regionPerilCode": "EUET",
    "regionPerilName": "Europe",
    "grain": "AXA-3rdCov_05PA753_WS_ENG_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175426_RMS-RiskLink_RL17_EUET_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-442_N-100_P-T-2_ID-SPLTH-000735494_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [{
      "tagId": 60059,
      "tagName": "tag1",
      "tagColor": "#d81b60",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}]
    }, {
      "tagId": 60062,
      "tagName": "lol",
      "tagColor": "#ef5350",
      "pltHeaders": [{"id": "SPLTH-000735496"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}]
    }, {
      "tagId": 60060,
      "tagName": "tag2",
      "tagColor": "#64ffda",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735496",
    "pltName": "EUET_GR_LMF1.03_RPadj.T2",
    "peril": "WS",
    "regionPerilCode": "EUET",
    "regionPerilName": "Europe",
    "grain": "AXA-3rdCov_05PA753_WS_NonEng_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-175532_RMS-RiskLink_RL17_EUET_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-442_N-100_P-T-2_ID-SPLTH-000735496_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [{
      "tagId": 60062,
      "tagName": "lol",
      "tagColor": "#ef5350",
      "pltHeaders": [{"id": "SPLTH-000735496"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}]
    }],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735501",
    "pltName": "EUEQ-DE_GR_DefAdj_LMF0.82._NonEng.T4",
    "peril": "EQ",
    "regionPerilCode": "EUEQ-DE",
    "regionPerilName": "Europe (Germany)",
    "grain": "AXA-3rdCov_05PA753_EQ_NonEng_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180258_RMS-RiskLink_RL17_EUEQ-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-436_N-100_P-T-4_ID-SPLTH-000735501_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [{
      "tagId": 60061,
      "tagName": "ko",
      "tagColor": "#0700cf",
      "pltHeaders": [{"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }, {
      "tagId": 60062,
      "tagName": "lol",
      "tagColor": "#ef5350",
      "pltHeaders": [{"id": "SPLTH-000735496"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}]
    }, {
      "tagId": 60060,
      "tagName": "tag2",
      "tagColor": "#64ffda",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735505",
    "pltName": "EUCS-DE_GR_LMF1.03.T3",
    "peril": "CS",
    "regionPerilCode": "EUCS-DE",
    "regionPerilName": "Europe (Germany)",
    "grain": "AXA-3rdCov_05PA753_Hail_ALL_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180302_RMS-RiskLink_RL17_EUCS-DE_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-435_N-100_P-T-3_ID-SPLTH-000735505_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [{
      "tagId": 60061,
      "tagName": "ko",
      "tagColor": "#0700cf",
      "pltHeaders": [{"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }, {
      "tagId": 60060,
      "tagName": "tag2",
      "tagColor": "#64ffda",
      "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
    }],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735507",
    "pltName": "DEFL_GR_DefAdj_LMF0.52._ENG.T4",
    "peril": "FL",
    "regionPerilCode": "DEFL",
    "regionPerilName": "Germany",
    "grain": "AXA-3rdCov_05PA753_FL_ENG_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180305_RMS-RiskLink_RL17_DEFL_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-438_N-100_P-T-4_ID-SPLTH-000735507_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000735510",
    "pltName": "DEFL_GR_DefAdj_LMF0.52._NonEng.T4",
    "peril": "YY",
    "regionPerilCode": "DEFL",
    "regionPerilName": "Germany",
    "grain": "AXA-3rdCov_05PA753_FL_NonEng_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181018-180310_RMS-RiskLink_RL17_DEFL_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-438_N-100_P-T-4_ID-SPLTH-000735510_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [],
    "xactPublicationDate": null
  }, {
    "workspaceId": "17P3398",
    "uwy": 2016,
    "pltId": "SPLTH-000736014",
    "pltName": "EUET_GR_LMF1.03_RPadj._NonEng_RPeventXL.T4",
    "peril": "WS",
    "regionPerilCode": "EUET",
    "regionPerilName": "Europe",
    "grain": "AXA-3rdCov_05PA753_WS_NonEng_Client",
    "vendorSystem": "RMS RL",
    "targetRapCode": null,
    "isScorCurrent": "true",
    "isScorDefault": "true",
    "isScorGenerated": "true",
    "project": "P-000007071",
    "pltType": "Thread",
    "projectName": "TEST",
    "creationDate": "11/02/2019",
    "year": 2019,
    "fileName": "T_P_AXA-GLOBAL-RE_05PA753_01_2019-01_PLT_20181019-144458_RMS-RiskLink_RL17_EUET_UF_EUR_P-000007071_FT_INTERNAL_DAT_T_RAP-442_N-100_P-T-4_ID-SPLTH-000736014_Job-1.bin",
    "sourceModellingVendor": "RMS",
    "sourceModellingSystem": "RiskLink",
    "dataSourceName": "CG1901_DEU_EQportfolio_R",
    "analysisId": "59",
    "currency": null,
    "userOccurrenceBasis": null,
    "userTags": [],
    "xactPublicationDate": null
  }],
  "userTags": [{
    "tagId": 60059,
    "tagName": "tag1",
    "tagColor": "#d81b60",
    "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}]
  }, {
    "tagId": 60060,
    "tagName": "tag2",
    "tagColor": "#64ffda",
    "pltHeaders": [{"id": "SPLTH-000735487"}, {"id": "SPLTH-000735484"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
  }, {
    "tagId": 60061,
    "tagName": "ko",
    "tagColor": "#0700cf",
    "pltHeaders": [{"id": "SPLTH-000735501"}, {"id": "SPLTH-000735505"}]
  }, {
    "tagId": 60062,
    "tagName": "lol",
    "tagColor": "#ef5350",
    "pltHeaders": [{"id": "SPLTH-000735496"}, {"id": "SPLTH-000735494"}, {"id": "SPLTH-000735501"}]
  }]
}
export const ADJUSTMENT_APPLICATION = {
  'SPLTH-0007246': [
    {
      id: 'SPLTH-0007246-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    },
    {
      id: 'SPLTH-0007246-1010',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    },
    {
      id: 'SPLTH-0007246-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    }
  ],
  'SPLTH-0007715': [
    {
      id: 'SPLTH-0007715-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    },
    {
      id: 'SPLTH-0007715-126',
      adjustmentBasis: 'Allocated Loss Adjustment Expense (ALAE)',
      category: 'Client',
      name: 'ALAE',
      description: 'An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.',
      exposureFlag: 'UNCAPPED',
      sequence: 7,
      isExposureGrowth: false,
      value: 'Event Driven',
      idAdjustementType: 2,
      favored: false,
      linear: true,
      hover: false,
      ref: 875
    },
    {
      id: 'SPLTH-0007715-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    }
  ],
  'SPLTH-0007153': [
    {
      id: 'SPLTH-0007153-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    },
    {
      id: 'SPLTH-0007153-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    }
  ],
  'SPLTH-0007171': [
    {
      id: 'SPLTH-0007171-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    }
  ],
  'SPLTH-0007436': [
    {
      id: 'SPLTH-0007436-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    },
    {
      id: 'SPLTH-0007436-126',
      adjustmentBasis: 'Allocated Loss Adjustment Expense (ALAE)',
      category: 'Client',
      name: 'ALAE',
      description: 'An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.',
      exposureFlag: 'UNCAPPED',
      sequence: 7,
      isExposureGrowth: false,
      value: 'Event Driven',
      idAdjustementType: 2,
      favored: false,
      linear: true,
      hover: false,
      ref: 875
    },
    {
      id: 'SPLTH-0007436-611',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    }
  ],
  'SPLTH-0007422': [
    {
      id: 'SPLTH-0007422-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    },
    {
      id: 'SPLTH-0007422-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    },
    {
      id: 'SPLTH-0007422-611',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    }
  ],
  'SPLTH-0007317': [
    {
      id: 'SPLTH-0007317-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    }
  ],
  'SPLTH-0007707': [
    {
      id: 'SPLTH-0007707-297',
      adjustmentBasis: 'Exposure Adjustment (Portfolio Evolution)',
      category: 'Base',
      name: 'Portfolio Evolution',
      description: 'An adjustment to be applied on the basis that exposure is expected to grow or shrink for a client.',
      exposureFlag: 'UNCAPPED',
      sequence: 1,
      isExposureGrowth: true,
      value: '1.3',
      idAdjustementType: 4,
      favored: false,
      linear: false,
      hover: false,
      ref: 930
    },
    {
      id: 'SPLTH-0007707-1010',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    },
    {
      id: 'SPLTH-0007707-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    }
  ],
  'SPLTH-0007830': [
    {
      id: 'SPLTH-0007830-126',
      adjustmentBasis: 'Allocated Loss Adjustment Expense (ALAE)',
      category: 'Client',
      name: 'ALAE',
      description: 'An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.',
      exposureFlag: 'UNCAPPED',
      sequence: 7,
      isExposureGrowth: false,
      value: 'Event Driven',
      idAdjustementType: 2,
      favored: false,
      linear: true,
      hover: false,
      ref: 875
    },
    {
      id: 'SPLTH-0007830-1010',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    }
  ],
  'SPLTH-0007402': [
    {
      id: 'SPLTH-0007402-126',
      adjustmentBasis: 'Allocated Loss Adjustment Expense (ALAE)',
      category: 'Client',
      name: 'ALAE',
      description: 'An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.',
      exposureFlag: 'UNCAPPED',
      sequence: 7,
      isExposureGrowth: false,
      value: 'Event Driven',
      idAdjustementType: 2,
      favored: false,
      linear: true,
      hover: false,
      ref: 875
    },
    {
      id: 'SPLTH-0007402-1010',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    }
  ],
  'SPLTH-0007109': [
    {
      id: 'SPLTH-0007109-126',
      adjustmentBasis: 'Allocated Loss Adjustment Expense (ALAE)',
      category: 'Client',
      name: 'ALAE',
      description: 'An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.',
      exposureFlag: 'UNCAPPED',
      sequence: 7,
      isExposureGrowth: false,
      value: 'Event Driven',
      idAdjustementType: 2,
      favored: false,
      linear: true,
      hover: false,
      ref: 875
    },
    {
      id: 'SPLTH-0007109-1010',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    }
  ],
  'SPLTH-0007664': [
    {
      id: 'SPLTH-0007664-126',
      adjustmentBasis: 'Allocated Loss Adjustment Expense (ALAE)',
      category: 'Client',
      name: 'ALAE',
      description: 'An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.',
      exposureFlag: 'UNCAPPED',
      sequence: 7,
      isExposureGrowth: false,
      value: 'Event Driven',
      idAdjustementType: 2,
      favored: false,
      linear: true,
      hover: false,
      ref: 875
    }
  ],
  'SPLTH-0007661': [
    {
      id: 'SPLTH-0007661-126',
      adjustmentBasis: 'Allocated Loss Adjustment Expense (ALAE)',
      category: 'Client',
      name: 'ALAE',
      description: 'An adjustment to be applied on the basis that losses are expected to be higher than anticipated due to legal costs, business costs, and other associated losses outside of the exposures themselves.',
      exposureFlag: 'UNCAPPED',
      sequence: 7,
      isExposureGrowth: false,
      value: 'Event Driven',
      idAdjustementType: 2,
      favored: false,
      linear: true,
      hover: false,
      ref: 875
    },
    {
      id: 'SPLTH-0007661-1010',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    },
    {
      id: 'SPLTH-0007661-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    }
  ],
  'SPLTH-0007236': [
    {
      id: 'SPLTH-0007236-1010',
      adjustmentBasis: 'Missing Exposure',
      category: 'Base',
      name: 'Missing Exposures',
      description: 'An adjustment to be applied on the basis that exposure is believed to be missing from the client\'s data.',
      exposureFlag: 'UNCAPPED',
      sequence: 2,
      isExposureGrowth: false,
      value: 'RP (OEP)',
      idAdjustementType: 5,
      favored: false,
      linear: true,
      hover: false,
      ref: 123
    }
  ],
  'SPLTH-0007285': [
    {
      id: 'SPLTH-0007285-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    }
  ],
  'SPLTH-0007701': [
    {
      id: 'SPLTH-0007701-655',
      adjustmentBasis: 'Cedant Historical Claims',
      category: 'Client',
      name: 'Cedant Claims',
      description: 'An adjustment to be applied on the basis of client claims data (contextualization).',
      exposureFlag: 'CAP',
      sequence: 11,
      isExposureGrowth: false,
      value: '1.3',
      idAdjustementType: 6,
      favored: false,
      linear: false,
      hover: false,
      ref: 863
    }
  ]
}
