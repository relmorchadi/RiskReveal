
export class GeneralConfig {
  general: {
    dateFormat: {
      shortDate: string;
      longDate: string;
      shortTime: string;
      longTime: string;
    };
    timeZone: any;
    numberFormat: {
      numberOfDecimals: number;
      decimalSeparator: string;
      decimalThousandSeparator: string;
      negativeFormat: string;
      numberHistory: string;
    };
  };
  riskLink: {
    importPage: any;
    financialPerspectiveELT: any;
    financialPerspectiveEPM: any;
    targetCurrency: any;
    rmsInstance: any;
  };
  contractOfInterest: {
    country: any;
    uwUnit: any;
  };
  epCurves: {
    returnPeriod: any;
    display: any;
  };
}
