const scrollableColsTreaty = [
  {field: 'bouquet', header: 'Bouquet', width: '130px', display: true, filtered: false, type: 'text', indicator: true, color: '#8C54FF'},
  {field: 'programId', header: 'Program', width: '80px', display: true, filtered: false, type: 'text', indicator: true, color: '#03DAC4'},
  {field: 'programName', header: '', width: '180px', display: false, filtered: false, type: 'text', indicator: false, color: null},
  {field: 'inception', header: 'Inception', width: '70px', display: true, filtered: false, type: 'text', indicator: false, color: null},
  {field: 'expiry', header: 'Expiry', width: '70px', display: true, filtered: false, type: 'text', indicator: false, color: null},
  {field: 'subsidiary', header: 'Subsidiary', width: '90px', display: true, filtered: false, type: 'text', indicator: false, color: null},
  {field: 'subsidiaryLedger', header: 'Subsidiary Ledger', width: '120px', display: true, filtered: false, type: 'text', indicator: false, color: null},
  {field: 'treatyStatus', header: 'Treaty Status', width: '100px', display: true, filtered: false, type: 'text', indicator: false, color: null},
  {field: 'sectionStatus', header: 'Section Status', width: '100px', display: true, filtered: false, type: 'text', indicator: false, color: null},
  {field: 'treatyLabel', header: 'Treaty Label', width: '120px', display: true, filtered: false, type: 'text', indicator: false, color: null},
];

const frozenColsTreaty = [
  {field: 'selected', header: 'selected', width: '20px', display: false, filtered: false, type: 'select', indicator: false, color: null},
  {field: 'treatySection', header: 'Treaty Section', width: '120px', display: true, filtered: false, type: 'text', indicator: true, color: '#FFAA06'},
];

const scrollableColsFac = [
  {field: 'principal', header: 'Is Principal', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, display: true},
  {field: 'lob', header: 'LOB', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, display: true},
  {field: 'coverage', header: 'Coverage', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, display: true},
  {field: 'currency', header: 'Currency', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, display: true},
];

const frozenColsFac = [
  {field: 'selected', header: 'selected', width: '20px', display: false, filtered: false, type: 'select', indicator: false, color: null},
  {field: 'divisionNo', header: 'Division No', width: '120px', display: true, filtered: true, type: 'text', indicator: true, color: '#FFAA06'},
];

const colsReinstatement = [
  {field: 'uwYear', header: 'U/W Year', width: '60px', type: 'text', display: true, filtered: false, indicator: false, color: null},
  {field: 'treatySection', header: 'Treaty Section', width: '150px', type: 'text', display: true, filtered: false, indicator: false, color: null},
  {field: 'reinstLabel', header: 'Reinst. Label', width: '100px', type: 'text', display: true, filtered: false, indicator: false, color: null},
  {field: 'rank', header: 'Rank', width: '70px', type: 'text', display: true, filtered: false, indicator: false, color: null},
  {field: 'premium', header: 'Premium', width: '70px', type: 'status', display: true, filtered: false, indicator: false, color: null},
  {field: 'cededRate', header: '% Ceded Rate', width: '70px', type: 'status', display: true, filtered: false, indicator: false, color: null},
  {field: 'proRataTemporis', header: 'Pro-Rata Temporis', width: '100px', type: 'icon', display: true, filtered: false, indicator: false, color: null}
];

const colsRegionPeril = [
  {field: 'regionPerilCode', header: 'Region Peril Code', width: '60px', type: 'text', display: true, filtered: false, indicator: false, color: null},
  {field: 'regionPerilDesc', header: 'Region Peril Description', width: '150px', type: 'text', display: true, filtered: false, indicator: false, color: null},
  {field: 'reinstLabel', header: 'Reinst. Label', width: '100px', type: 'text', display: true, filtered: false, indicator: false, color: null},
];

const treatyData = [
  {
    id: 1,
    selected: false,
    treatySection: '02T019977 / 1',
    bouquet: '',
    programId: '02PY558',
    programName: 'XS DAB EVTS NATURELS',
    inception: '1/1/12',
    expiry: '31/1/12',
    subsidiary: 'Scor Reass.',
    subsidiaryLedger: 'Paris',
    treatyStatus: 'Renewed',
    sectionStatus: 'Renewed',
    treatyLabel: '1XS DAB EVTS NATURE'
  },
  {
    id: 2,
    selected: false,
    treatySection: '02T019977 / 1',
    bouquet: '',
    programId: '02PY558',
    programName: 'XS DAB EVTS NATURELS',
    inception: '1/1/12',
    expiry: '31/1/12',
    subsidiary: 'Scor Reass.',
    subsidiaryLedger: 'Paris',
    treatyStatus: 'Renewed',
    sectionStatus: 'Renewed',
    treatyLabel: '1XS DAB EVTS NATURE'
  },
  {
    id: 3,
    selected: false,
    treatySection: '02T019977 / 1',
    bouquet: '',
    programId: '02PY558',
    programName: 'XS DAB EVTS NATURELS',
    inception: '1/1/12',
    expiry: '31/1/12',
    subsidiary: 'Scor Reass.',
    subsidiaryLedger: 'Paris',
    treatyStatus: 'Renewed',
    sectionStatus: 'Renewed',
    treatyLabel: '1XS DAB EVTS NATURE'
  },
  {
    id: 4,
    selected: false,
    treatySection: '02T019977 / 1',
    bouquet: '',
    programId: '02PY558',
    programName: 'XS DAB EVTS NATURELS',
    inception: '1/1/12',
    expiry: '31/1/12',
    subsidiary: 'Scor Reass.',
    subsidiaryLedger: 'Paris',
    treatyStatus: 'Renewed',
    sectionStatus: 'Renewed',
    treatyLabel: '1XS DAB EVTS NATURE'
  },
];

const reinstatementData = [
  {
    uwYear: '2012',
    treatySection: '1 XS DAB EVTS NATURELS',
    reinstLabel: '2',
    rank: '2',
    premium: 100,
    cededRate: 100,
    proRataTemporis: true
  },
  {
    uwYear: '2012',
    treatySection: '1 XS DAB EVTS NATURELS',
    reinstLabel: '2',
    rank: '2',
    premium: 100,
    cededRate: 100,
    proRataTemporis: true
  },
  {
    uwYear: '2012',
    treatySection: '1 XS DAB EVTS NATURELS',
    reinstLabel: '2',
    rank: '2',
    premium: 100,
    cededRate: 100,
    proRataTemporis: true
  }
];

const listStandardContent = {
  primary: [
    {
      title: 'Ceded Share:',
      value: 100
    },
    {
      title: 'Written Share:',
      value: 100
    },
    {
      title: 'Signed Share:',
      value: 9
    },
    {
      title: 'Expected Ceded Share:',
      value: 9
    }
  ],
  secondary: [
    {
      title: 'Line of Business:',
      value: 'Property'
    },
    {
      title: 'Scope of Business:',
      value: 'Residential/Pers'
    },
    {
      title: 'Type of Policy:',
      value: 'PD&Bi All Risk'
    },
    {
      title: 'General Nature:',
      value: 'Excess of Loss'
    }
  ]
};

const listSecondaryContent = {
  liability: {
    capacity: 80000000,
    attachmentPoint: 80000000,
    annualDeductible: 0,
    isUnlimited: false,
    limitPerEvent: 0,
    annualLimit: 240000000
  },
  premium: {
    subjectPremium: '',
    subjectPremiumBasis: '',
    scorEGPI: '',
    unlimitedReinst: false
  }

};

const coveragesElement = [
  {
    country: 'France',
    flags: [
      {color: '#E70010', content: 'EQ'},
      {color: '#CCCCCC', content: 'FL'},
      {color: '#7BBE31', content: 'WS'}
    ]
  },
  {
    country: 'France',
    flags: [
      {color: '#E70010', content: 'EQ'},
      {color: '#008694', content: 'FL'},
      {color: '#7BBE31', content: 'WS'}
    ]
  },
  {
    country: 'France',
    flags: [
      {color: '#E70010', content: 'EQ'},
      {color: '#008694', content: 'FL'},
      {color: '#CCCCCC', content: 'WS'}
    ]
  },
  {
    country: 'France',
    flags: [
      {color: '#CCCCCC', content: 'EQ'},
      {color: '#008694', content: 'FL'},
      {color: '#7BBE31', content: 'WS'}
    ]
  },
];

export const ContractData = {
  scrollableColsTreaty,
  frozenColsTreaty,
  scrollableColsFac,
  frozenColsFac,
  colsReinstatement,
  colsRegionPeril,
  treatyData,
  reinstatementData,
  listStandardContent,
  coveragesElement,
  listSecondaryContent
};
