import * as _ from "lodash";

export class RiskLink {
    type;
    listEdmRdm: {
        data: {},
        searchValue: string,
        totalNumberElement: number,
        numberOfElement: number
    };
    selection: {
        edms: {},
        rdms: {},
        analysis: {},
        portfolios: {},
        currentDataSource: number
    };
    collapse: {
        collapseHead: boolean
    };
    summary: {
        analysis: {},
        portfolios: {},
        sourceEpHeaders: [],
        targetRaps: [],
        regionPerils: []
    };
    display: {
        displayListRDMEDM: boolean,
        displayTable: boolean,
        displayImport: boolean
    };
    financialValidator: {
        rmsInstance: {
            data: any,
            selected: any
        },
        financialPerspectiveELT: {
            data: any,
            selected: any
        },
        targetCurrency: {
            data: any,
            selected: any
        },
        division: {
            data: any,
            selected: any
        },
    };
    facSelection: any;
    analysis: any;
    portfolios: any;

    constructor() {
        this.listEdmRdm = {
            data: {},
            searchValue: '',
            totalNumberElement: 0,
            numberOfElement: 0
        };
        this.display = {
            displayListRDMEDM: false,
            displayTable: false,
            displayImport: false
        };
        this.selection = {
            edms: {},
            rdms: {},
            analysis: {},
            portfolios: {},
            currentDataSource: null
        };
        this.collapse = {
            collapseHead: true
        };
        this.summary = {
            analysis: {},
            portfolios: {},
            sourceEpHeaders: [],
            targetRaps: [],
            regionPerils: []
        };
        this.financialValidator = {
            rmsInstance: {data: [], selected: ''},
            financialPerspectiveELT: {data: [], selected: ''},
            targetCurrency: {data: [], selected: 'Main Liability Currency (MLC)'},
            division: {data: [], selected: 'Division N°1'},
        };
        this.facSelection = {};
        this.analysis = {};
        this.portfolios = {};
    }

    setRefData(refData) {
        this.financialValidator = {
            rmsInstance: {
                data: refData.rmsInstances,
                selected: refData.rmsInstances[0]
            },
            financialPerspectiveELT: {
                data: refData.financialPerspectives,
                selected: refData.financialPerspectives[0]
            },
            targetCurrency: {
                data: refData.currencies,
                selected: refData.currencies[0]
            },
            division: {
                data: refData.division,
                selected: !_.isEmpty(refData.division) ? refData.division[0] : null
            },
        };
        this.facSelection = refData.division === null ? {} : Object.assign({}, ...refData.division.map(item => ({
                [item.divisionNumber]: {
                    analysis: {},
                    portfolios: {}
                }
            }
        )));
    }

    setType(type) {
        this.type = type;
    }

}
