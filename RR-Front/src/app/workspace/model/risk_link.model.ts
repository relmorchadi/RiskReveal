export class RiskLinkModel {
  listEdmRdm: {
    data: any,
    selectedListEDMAndRDM: any,
  };
  display: {
    displayDropdownRDMEDM: boolean,
    displayListRDMEDM: boolean,
    displayTable: boolean,
    displayImport: boolean,
  };
  collapse: {
    collapseHead: boolean,
    collapseAnalysis: boolean,
    collapseResult: boolean,
  };
  checked: {
    checkedARC: boolean,
    checkedPricing: boolean,
  };
  financialValidator: {
    rmsInstance: any,
    financialPerspectiveELT: any,
    financialPerspectiveEPM: any,
    targetCurrency: any,
    calibration: any,
  };
  selectedAnalysisAndPortoflio: {
    selectedAnalysis: any;
    selectedPortfolio: any;
  };
  currentStep: number;
}
