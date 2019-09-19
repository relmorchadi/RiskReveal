export class RiskLinkModel {
  listEdmRdm: {
    data: any,
    dataSelected: any,
    selectedListEDMAndRDM: any,
    totalNumberElement: number,
    searchValue: string,
    numberOfElement: number
  };
  linking: {
    edm: any,
    rdm: any,
    autoLinks: any,
    linked: any,
    analysis: any,
    portfolio: any,
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
    targetCurrency: any,
    calibration: any,
  };
  financialPerspective: {
    rdm: any,
    analysis: any,
    treaty: any,
    standard: any,
    target: any,
  };
  analysis: any;
  portfolios: any;
  results: any;
  summaries: any;
  selectedEDMOrRDM: string;
  activeAddBasket: boolean;
}
