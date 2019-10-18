const scrollableColsAnalysis = [
  {field: '', header: '', width: '0px', type: '', filtered: true},
  {field: 'description', header: 'Description', width: '150px', type: 'text', filtered: true},
  {field: 'engineVersion', header: 'Engine Version', width: '110px', type: 'text', filtered: true},
  {field: 'groupType', header: 'Group Type', width: '110px', type: 'text', filtered: true},
  {field: 'cedant', header: 'Cedant', width: '110px', type: 'text', filtered: true},
  {field: 'lobName', header: 'LOB', width: '110px', type: 'text', filtered: true},
  {field: 'engineType', header: 'Engine Type', width: '110px', type: 'text', filtered: true},
  {field: 'runDate', header: 'Run Date', width: '110px', type: 'text', filtered: true},
  {field: 'typeName', header: 'Type', width: '110px', type: 'text', filtered: true},
  {field: 'peril', header: 'Peril', width: '110px', type: 'text', filtered: true},
  {field: 'subPeril', header: 'Sub Peril', width: '110px', type: 'text', filtered: true},
  {field: 'lossAmplification', header: 'Loss Amplification', width: '110px', type: 'text', filtered: true},
  {field: 'region', header: 'Region', width: '110px', type: 'text', filtered: true},
  {field: 'modeName', header: 'Mode', width: '110px', type: 'text', filtered: true},
  {field: 'user1', header: 'User 1', width: '110px', type: 'text', filtered: true},
  {field: 'user2', header: 'User 2', width: '110px', type: 'text', filtered: true},
  {field: 'user3', header: 'User 3', width: '110px', type: 'text', filtered: true},
  {field: 'user4', header: 'User 4', width: '110px', type: 'text', filtered: true},
  {field: 'analysisCurrency', header: 'Source Currency', width: '110px', type: 'text', filtered: true},
  {field: 'regionName', header: 'Region Name', width: '110px', type: 'text', filtered: true},
  {field: 'statusDescription', header: 'Status Description', width: '110px', type: 'text', filtered: true},
  {field: 'grouping', header: 'Grouping', width: '110px', type: 'text', filtered: true},
];

const frozenColsAnalysis = [
  {field: 'selected', header: 'selected', width: '20px', type: 'select', filtered: false},
  {field: 'imported', header: 'Imported', width: '20px', type: 'icon', filtered: false},
  {field: 'analysisId', header: 'id', width: '30px', type: 'text', filtered: true},
  {field: 'analysisName', header: 'name', width: '190px', type: 'text', filtered: true}
];

const scrollableColsPortfolio = [
  {field: '', header: '', width: '0px', type: '', filtered: true},
  {field: 'dataSourceName', header: 'Name', width: '150px', type: 'text', filtered: true},
  {field: 'creationDate', header: 'Creation Date', width: '180px', type: 'date', filtered: true},
  {field: 'descriptionType', header: 'Description Type', width: '180px', type: 'text', filtered: true},
  {field: 'type', header: 'Type', width: '180px', type: 'text', filtered: true},
  {field: 'agCedent', header: 'Cedant', width: '120px', type: 'text', filtered: true},
  {field: 'agCurrency', header: 'Currency', width: '120px', type: 'text', filtered: true},
  {field: 'agSource', header: 'Source', width: '120px', type: 'text', filtered: true},
  {field: 'peril', header: 'Peril', width: '120px', type: 'text', filtered: true},
];

const frozenColsPortfolio = [
  {field: 'selected', header: 'selected', width: '20px', type: 'select', filtered: false},
  {field: 'imported', header: 'Imported', width: '20px', type: 'icon', filtered: false},
  {field: 'dataSourceId', header: 'id', width: '30px', type: 'text', filtered: true},
  {field: 'number', header: 'Number', width: '190px', type: 'text', filtered: true}
];

const scrollableColsSummary = [
  // {field: 'exposedLocation', header: 'Exposed Location', width: '90px', type: 'indicator', sorting: ', filtered: true, highlight: false, visible: true},
  {field: 'analysisCurrency', header: 'Source Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'targetCurrency', header: 'Target Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: true, visible: true, edit: true},
  {field: 'division', header: 'Division', width: '80px', type: 'table', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'unitMultiplier', header: 'Unit Multiplier', width: '90px', type: 'number', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'proportion', header: 'Proportion', width: '70px', type: 'percentage', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'edmName', header: 'EDM', width: '250px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
/*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
];

const frozenColsSummary = [
  {field: 'selected', header: '', width: '25px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'scan', header: '', width: '25px', type: 'scan', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'status', header: 'Status', width: '40px', type: 'progress', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'dataSourceId', header: 'ID', width: '40px', type: 'text', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'number', header: 'Number', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'dataSourceName', header: 'Name', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
];

const scrollableColsResults = [
  {field: '', header: '', width: '80px', type: '', filtered: false, visible: false, highlight: false, edit: false},
  {field: 'regionPeril', header: 'Region Peril', width: '80px', type: 'Rp', sorting: '', filtered: true, highlight: true, visible: true, edit: false},
  {field: 'analysisCurrency', header: 'Source Currency', width: '90px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'targetCurrency', header: 'Target Currency', width: '80px', type: 'text', sorting: '', filtered: true, highlight: true, visible: true, edit: true},
  {field: 'financialPerspective', header: 'ELT FIN PERSP', width: '80px', type: 'multiple', sorting: '', filtered: true, highlight: true, visible: true, edit: false},
  {field: 'occurrenceBasis', header: 'Occurrence Basis', width: '100px', type: 'Ob', sorting: '', filtered: true, highlight: true, visible: true, edit: false},
  // {field: 'targetRap', header: 'Target RAP', width: '80px', type: 'number', sorting: ', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'peqt', header: 'PEQT', width: '80px', type: 'Peqt', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'division', header: 'Division', width: '80px', type: 'table', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'unitMultiplier', header: 'Unit Multiplier', width: '80px', type: 'number', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'proportion', header: 'Proportion', width: '70px', type: 'percentage', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'rdmName', header: 'RDM', width: '210px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'importId', header: 'Import ID', width: '80px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'dateImport', header: 'Date Import', width: '70px', type: 'date', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'user', header: 'User', width: '70px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'publishAcc', header: 'Publish For Accumulation', width: '30px', type: 'check', icon: 'icon-focus-add', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'publishPri', header: 'Publish For Pricing', width: '30px', type: 'check', icon: 'icon-note', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
/*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
];

const frozenColsResults = [
  {field: 'selected', header: '', width: '25px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true, edit: false},
  {field: 'scan', header: '', width: '25px', type: 'scan', sorting: '', filtered: false, highlight: false, visible: true, edit: false},
  {field: 'status', header: 'Status', width: '40px', type: 'progress', sorting: '', filtered: false, highlight: false, visible: true, edit: false},
  {field: 'analysisId', header: 'ID', width: '40px', type: 'text', sorting: '', filtered: false, highlight: false, visible: true, edit: false},
  {field: 'analysisName', header: 'Name', width: '170px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
  {field: 'description', header: 'Description', width: '200px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
];

const scrollableColsLinking = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'id', header: 'ID', width: '30px', type: 'text', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'dataSourceName', header: 'Name', width: '90px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'description', header: 'Description', width: '150px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'engineVersion', header: 'Eng Version', width: '70px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'iconManager', header: '', width: '25px', type: 'icon', sorting: '', filtered: false, highlight: false, visible: true},
];

const colsFinancialAnalysis = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'id', header: 'ID', width: '35px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'analysisName', header: 'Name', width: '100px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'description', header: 'Description', width: '150px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'regionPeril', header: 'Region Peril', width: '70px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'ty', header: 'TY', width: '40px', type: 'iconIndicator', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'financialPerspective', header: 'Financial Perspective', width: '150px', type: 'tags', sorting: '', filtered: true, highlight: false, visible: true},
];

const colsFinancialStandard = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'code', header: 'Code', width: '40px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'financialPerspective', header: 'Financial Perspective', width: '300px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'ccy', header: 'CCY', width: '60px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'aal', header: 'AAL', width: '100px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'stdDev', header: 'STD DEV', width: '120px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'firstTarget', header: 'OEP 100', width: '50px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'secondTarget', header: 'OEP 200', width: '50px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'thirdTarget', header: 'OEP 250', width: '50px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
];

const financialStandarContent = [
  {id: '1', selected: false, code: 'CL', financialPerspective: 'Client Loss', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
  {id: '2', selected: false, code: 'FA', financialPerspective: 'Facultative Reinsurance Loss', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
  {id: '3', selected: false, code: 'GR', financialPerspective: 'Gross Loss', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
  {id: '4', selected: false, code: 'GU', financialPerspective: 'Ground Up Loss', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
  {id: '5', selected: false, code: 'QS', financialPerspective: 'Quota Share Treaty Loss', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
  {id: '6', selected: false, code: 'RC', financialPerspective: 'Net Loss Post Corporate Cat', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
  {id: '7', selected: false, code: 'RG', financialPerspective: 'Reinsurance Gross Loss', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
  {id: '8', selected: false, code: 'RL', financialPerspective: 'Net Loss Pre Cat', ccy: 'USD', aal: '55,837,813', stdDev: '158,455,991', firstTarget: '2,84', secondTarget: '2,84', thirdTarget: '2,84'},
];

const regionPerilDataTable = [
  {field: 'analysisId', header: 'ID', width: '50px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'analysisName', header: 'Analysis Name', width: '90px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'regionPeril', header: 'Region Peril', width: '80px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'override', header: 'Override', width: '250px', type: 'override', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'copy', header: 'Copy', width: '50px', type: 'function', sorting: '', filtered: false, highlight: false, visible: true},
  {field: 'reason', header: 'Reason', width: '170px', type: 'comment', sorting: '', filtered: false, highlight: false, visible: true},
];

const peqtDatatable = [
  {field: 'expand', header: '', width: '20px', type: 'expand', sorting: false, filtered: false, highlight: false, visible: true},
  {field: 'title', header: 'Region Peril', width: '80px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  {field: 'selectedItems', header: 'Selection', width: '210px', type: 'info', sorting: '', filtered: true, highlight: false, visible: true},
];

const targetRapDataTable = [
  {field: 'selected', header: '', width: '20px', type: 'select', sorting: false, filtered: false, highlight: false, visible: true},
  {field: 'title', header: 'target Rap', width: '150px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
];

export const DataTables = {
  scrollableColsAnalysis,
  scrollableColsPortfolio,
  scrollableColsSummary,
  scrollableColsResults,
  scrollableColsLinking,
  frozenColsAnalysis,
  frozenColsPortfolio,
  frozenColsSummary,
  frozenColsResults,
  colsFinancialAnalysis,
  colsFinancialStandard,
  financialStandarContent,
  regionPerilDataTable,
  peqtDatatable,
  targetRapDataTable
};
