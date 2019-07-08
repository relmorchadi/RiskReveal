const scrollableColsAnalysis = [
  {field: 'description', header: 'Description', width: '150px', type: 'text'},
  {field: 'engineVersion', header: 'Engine Version', width: '110px', type: 'text'},
  {field: 'groupType', header: 'Group Type', width: '110px', type: 'text'},
  {field: 'cedant', header: 'Cedant', width: '110px', type: 'text'},
  {field: 'lobName', header: 'LOB', width: '110px', type: 'text'},
  {field: 'engineType', header: 'Engine Type', width: '110px', type: 'text'},
  {field: 'runDate', header: 'Run Date', width: '110px', type: 'text'},
  {field: 'typeName', header: 'Type', width: '110px', type: 'text'},
  {field: 'peril', header: 'Peril', width: '110px', type: 'text'},
  {field: 'subPeril', header: 'Sub Peril', width: '110px', type: 'text'},
  {field: 'lossAmplification', header: 'Loss Amplification', width: '110px', type: 'text'},
  {field: 'region', header: 'Region', width: '110px', type: 'text'},
  {field: 'modeName', header: 'Mode', width: '110px', type: 'text'},
  {field: 'user1', header: 'User 1', width: '110px', type: 'text'},
  {field: 'user2', header: 'User 2', width: '110px', type: 'text'},
  {field: 'user3', header: 'User 3', width: '110px', type: 'text'},
  {field: 'user4', header: 'User 4', width: '110px', type: 'text'},
  {field: 'analysisCurrency', header: 'Source Currency', width: '110px', type: 'text'},
  {field: 'regionName', header: 'Region Name', width: '110px', type: 'text'},
  {field: 'statusDescription', header: 'Status Description', width: '110px', type: 'text'},
  {field: 'grouping', header: 'Grouping', width: '110px', type: 'text'},
];

const frozenColsAnalysis = [
  {field: 'selected', header: 'selected', width: '20px', type: 'select'},
  {field: 'analysisId', header: 'id', width: '30px', type: 'text'},
  {field: 'analysisName', header: 'name', width: '190px', type: 'text'}
];

const scrollableColsPortfolio = [
  {field: 'dataSourceName', header: 'Name', width: '150px', type: 'text'},
  {field: 'creationDate', header: 'Creation Date', width: '180px', type: 'date'},
  {field: 'descriptionType', header: 'Description Type', width: '180px', type: 'text'},
  {field: 'type', header: 'Type', width: '180px', type: 'text'},
  {field: 'agCedent', header: 'Cedant', width: '120px', type: 'text'},
  {field: 'agCurrency', header: 'Currency', width: '120px', type: 'text'},
  {field: 'agSource', header: 'Source', width: '120px', type: 'text'},
  {field: 'peril', header: 'Peril', width: '120px', type: 'text'},
];

const frozenColsPortfolio = [
  {field: 'selected', header: 'selected', width: '20px', type: 'select'},
  {field: 'dataSourceId', header: 'id', width: '30px', type: 'text'},
  {field: 'number', header: 'Number', width: '190px', type: 'text'}
];

const scrollableColsSummary = [
  {field: 'exposedLocation', header: 'Exposed Location', width: '90px', type: 'indicator', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'analysisCurrency', header: 'Source Currency', width: '100px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'targetCurrency', header: 'Target Currency', width: '100px', type: 'text', sorted: false, filtered: true, highlight: true, visible: true},
  {field: 'unitMultiplier', header: 'Unit Multiplier', width: '90px', type: 'number', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'proportion', header: 'Proportion', width: '70px', type: 'percentage', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'edmName', header: 'EDM', width: '150px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
/*  {field: 'action', header: '', width: '25px', type: 'icon', sorted: false, filtered: false, highlight: false},*/
];

const frozenColsSummary = [
  {field: 'scan', header: '', width: '25px', type: 'scan', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'status', header: 'Status', width: '40px', type: 'progress', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'dataSourceId', header: 'ID', width: '40px', type: 'text', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'number', header: 'Number', width: '190px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'dataSourceName', header: 'Name', width: '190px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
];

const scrollableColsResults = [
  {field: 'peril', header: 'Region Peril', width: '70px', type: 'text', sorted: false, filtered: true, highlight: true, visible: true},
  {field: 'analysisCurrency', header: 'Source Currency', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'targetCurrency', header: 'Target Currency', width: '80px', type: 'text', sorted: false, filtered: true, highlight: true, visible: true},
  {field: 'financialPerspective', header: 'ELT FIN PERSP', width: '70px', type: 'multiple', sorted: false, filtered: true, highlight: true, visible: true},
  {field: 'occurrenceBasis', header: 'Occurrence Basis', width: '90px', type: 'text', sorted: false, filtered: true, highlight: true, visible: true},
  {field: 'unitMultiplier', header: 'Unit Multiplier', width: '80px', type: 'number', sorted: false, filtered: true, highlight: true, visible: true},
  // {field: 'targetRap', header: 'Target RAP', width: '80px', type: 'number', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'peqt', header: 'PEQT', width: '80px', type: 'number', sorted: false, filtered: true, highlight: false, visible: true},
/*  {field: 'action', header: '', width: '25px', type: 'icon', sorted: false, filtered: false, highlight: false},*/
];

const frozenColsResults = [
  {field: 'scan', header: '', width: '25px', type: 'scan', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'status', header: 'Status', width: '40px', type: 'progress', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'analysisId', header: 'ID', width: '40px', type: 'text', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'analysisName', header: 'Name', width: '140px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'description', header: 'Description', width: '200px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
];

const scrollableColsLinking = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'icon', header: '', width: '25px', type: 'iconIndicator', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'id', header: 'ID', width: '40px', type: 'text', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'dataSourceName', header: 'Name', width: '100px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'description', header: 'Description', width: '200px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'engineVersion', header: 'Eng Version', width: '70px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'iconManager', header: '', width: '25px', type: 'icon', sorted: false, filtered: false, highlight: false, visible: true},
];

const colsFinancialAnalysis = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'id', header: 'ID', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'nameDesc', header: 'Name & Description', width: '300px', type: 'branched', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'regionPeril', header: 'Region Peril', width: '100px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'ty', header: 'TY', width: '70px', type: 'iconIndicator', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'financialPerspective', header: 'Financial Perspective', width: '150px', type: 'tags', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'manager', header: '', width: '25px', type: 'icon', sorted: false, filtered: false, highlight: false, visible: true},
];

const colsFinancialStandard = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'code', header: 'Code', width: '40px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'financialPerspective', header: 'Financial Perspective', width: '300px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'ccy', header: 'CCY', width: '60px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'aal', header: 'AAL', width: '100px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'stdDev', header: 'STD DEV', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'firstTarget', header: 'OEP 100', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'secondTarget', header: 'OEP 200', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'thirdTarget', header: 'OEP 250', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'manager', header: '', width: '25px', type: 'icon', sorted: false, filtered: false, highlight: false, visible: true},
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
  financialStandarContent
};
