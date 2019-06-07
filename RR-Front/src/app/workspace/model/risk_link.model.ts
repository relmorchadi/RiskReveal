export class RiskLinkModel {
  listEdmRdm: {
    data: any,
    dataSelected: any,
    selectedListEDMAndRDM: any,
    totalNumberElement: number,
    searchValue: string,
    numberOfElement: number
  };
  display: {
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
  analysis: any;
  portfolios: any;
  selectedEDMOrRDM: string;
}
