import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PltTableService {

  constructor() {
    PltTableService.pltColumns = [
      {
        sortDir: 1,
        fields: 'selected',
        header: 'Select Box',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '40',
        unit: 'px',
        icon: null,
        type: 'checkbox',
        active: true
      },
      {
        sortDir: 1,
        fields: 'tags',
        header: 'User Tags',
        sorted: false,
        filtred: false,
        resizable: true,
        width: '66',
        unit: 'px',
        icon: null,
        type: 'tags',
        active: true
      },
      {
        sortDir: 1,
        fields: 'pltId',
        header: 'PLT ID',
        textAlign: "right",
        sorted: true,
        filtred: true,
        resizable: true,
        icon: null,
        width: '60',
        unit: 'px',
        type: 'id',
        active: true
      },
      {
        sortDir: 1,
        fields: 'pltName',
        header: 'PLT Name',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'pltType',
        header: 'PLT Type',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'pltStatus',
        header: 'PLT Status',
        sorted: true,
        filtred: false,
        resizable: false,
        width: '40',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'groupedPlt',
        header: 'Grouped PLT',
        sorted: true,
        filtred: false,
        resizable: false,
        width: '70',
        unit: 'px',
        type: 'indicator',
        active: true
      },
      {
        sortDir: 1,
        fields: 'grain',
        header: 'Grain',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'arcPublication',
        header: '',
        sorted: true,
        filtred: false,
        icon: 'icon-focus-add',
        type: 'icon',
        width: '35',
        unit: 'px',
        active: true,
        tooltip: "Published for Accumulation",
        highlight: 'Accumulated'
      },
      {
        sortDir: 1,
        fields: 'xactPublication',
        header: '',
        sorted: true,
        filtred: false,
        resizable: false,
        icon: 'icon-note',
        type: 'icon',
        width: '35',
        unit: 'px',
        active: true,
        tooltip: "Published for Pricing",
        highlight: 'Published'
      },
      {
        sortDir: 1,
        fields: 'xactPriced',
        header: '',
        sorted: true,
        filtred: false,
        resizable: false,
        icon: 'icon-dollar-alt',
        type: 'icon',
        width: '35',
        unit: 'px',
        active: true,
        tooltip: "Priced",
        highlight: 'Priced'
      },
      {
        sortDir: 1,
        fields: 'perilGroupCode',
        header: 'Peril Group Code',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'peril',
        active: true
      },
      {
        sortDir: 1,
        fields: 'regionPerilCode',
        header: 'Region Peril Code',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'regionPerilDesc',
        header: 'Region Peril Description',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '340',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'minimumGrainRPCode',
        header: 'Minimum Grain RP Code',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'financialPerspective',
        header: 'Financial Perspective',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'targetRAPCode',
        header: 'Target RAP Code',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '300',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'targetRAPDesc',
        header: 'Target RAP Description',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '300',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'rootRegionPeril',
        header: 'Root Region Peril',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'vendorSystem',
        header: 'Vendor System',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'modellingDataSource',
        header: 'Modelling Data Source',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'analysisId',
        header: 'Analysis Id',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '60',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'analysisName',
        header: 'Analysis Name',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'defaultOccurenceBasis',
        header: 'Default Occurence Basis',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '75',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'occurenceBasis',
        header: 'Occurence Basis',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '75',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'baseAdjustment',
        header: 'B',
        sorted: true,
        filtred: false,
        resizable: false,
        width: '45',
        unit: 'px',
        icon: null,
        type: 'indicator',
        active: true
      },
      {
        sortDir: 1,
        fields: 'defaultAdjustment',
        header: 'D',
        sorted: true,
        filtred: false,
        resizable: false,
        width: '45',
        unit: 'px',
        icon: null,
        type: 'indicator',
        active: true
      },
      {
        sortDir: 1,
        fields: 'clientAdjustment',
        header: 'C',
        sorted: true,
        filtred: false,
        resizable: false,
        width: '45',
        unit: 'px',
        icon: null,
        type: 'indicator',
        active: true
      },
      {
        sortDir: 1,
        fields: 'projectId',
        header: 'Project Id',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'projectName',
        header: 'Project Name',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'workspaceContextCode',
        header: 'Workspace Context Code',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'client',
        header: 'Client',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '150',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'uwYear',
        header: 'uwYear',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '53',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'clonedPlt',
        header: 'Cloned Plt',
        sorted: true,
        filtred: false,
        resizable: false,
        width: '35',
        unit: 'px',
        icon: null,
        type: 'indicator',
        active: true
      },
      {
        sortDir: 1,
        fields: 'clonedSourcePlt',
        header: 'Cloned Source Plt',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'clonedSourceProject',
        header: 'Cloned Source Project',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'clonedSourceWorkspace',
        header: 'Cloned Source Workspace',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '80',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'pltCcy',
        header: 'PLT Ccy',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '40',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'aal',
        header: 'AAL',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'cov',
        header: 'COV',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'stdDev',
        header: 'Standard Deviation',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'oep10',
        header: 'OEP 10',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'oep50',
        header: 'OEP 50',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'oep100',
        header: 'OEP 100',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'oep250',
        header: 'OEP 250',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'oep500',
        header: 'OEP 500',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'oep1000',
        header: 'OEP 1000',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'aep10',
        header: 'AEP 10',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'aep50',
        header: 'AEP 50',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'aep100',
        header: 'AEP 100',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'aep250',
        header: 'AEP 250',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'aep500',
        header: 'AEP 500',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'aep1000',
        header: 'AEP 1000',
        textAlign: 'right',
        sorted: true,
        filtred: true,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        isNumeric: true,
        active: true
      },
      {
        sortDir: 1,
        fields: 'createdDate',
        header: 'Created Date',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'date',
        active: true
      },
      {
        sortDir: 1,
        fields: 'importedBy',
        header: 'Imported By',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'importedOn',
        header: 'Imported On',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'date',
        active: true
      },
      {
        sortDir: 1,
        fields: 'publishedBy',
        header: 'Published By',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      },
      {
        sortDir: 1,
        fields: 'archived',
        header: 'Archived',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'date',
        active: true
      },
      {
        sortDir: 1,
        fields: 'archivedDate',
        header: 'Archived Date',
        sorted: true,
        filtred: true,
        resizable: false,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'date',
        active: true
      },
      {
        sortDir: 1,
        fields: 'deletedBy',
        header: 'Deleted By',
        sorted: false,
        filtred: false,
        resizable: true,
        width: '100',
        unit: 'px',
        icon: null,
        type: 'field',
        active: true
      }
    ]

    PltTableService.config = {
      columns: [{field:"pltId", cellStyle: { textAlign: 'center' }, filter: "agNumberColumnFilter"},{field:"pltName"},{field:"pltType"},{field:"pltStatus", filter: "agSetColumnFilter",cellStyle: { display: 'flex', justifyContent: 'center' }, filterParams: { values: [ "Valid", 'In progress','Locked', 'Fail', 'Pending', 'Requires Regeneration' ] }, cellRenderer: 'statusCellRenderer'},{field:"groupedPlt",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"grain"},{field:"arcPublication",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"perilGroupCode"},{field:"regionPerilCode"},{field:"regionPerilDesc"},{field:"minimumGrainRPCode"},{field:"minimumGrainRPDescription"},{field:"financialPerspective"},{field:"targetRAPCode"},{field:"targetRAPDesc"},{field:"rootRegionPeril"},{field:"vendorSystem"},{field:"modellingDataSource"},{field:"analysisId", cellStyle: { textAlign: 'center' }},{field:"analysisName"},{field:"defaultOccurenceBasis"},{field:"occurenceBasis"},{field:"baseAdjustment",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"defaultAdjustment",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"clientAdjustment",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"projectId", cellStyle: { textAlign: 'center' }, filter: "agNumberColumnFilter"},{field:"projectName"},{field:"workspaceContextCode"},{field:"client"},{field:"uwYear"},{field:"clonedPlt",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"clonedSourcePlt"},{field:"clonedSourceProject"},{field:"clonedSourceWorkspace"},{field:"pltCcy"},{field:"aal", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"cov", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"stdDev", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep10", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep50", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep100", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep250", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep500", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"oep1000", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep10", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep50", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep100", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep250", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep500", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"aep1000", filter: 'agNumberColumnFilter', cellRenderer: 'numberCellRenderer'},{field:"createdDate", filter: 'agDateColumnFilter', cellRenderer: 'dateCellRenderer'},{field:"importedBy"},{field:"publishedBy"},{field:"archived"},{field:"archivedDate", filter: 'agDateColumnFilter', cellRenderer: 'dateCellRenderer'},{field:"deletedBy"},{field:"deletedDue"},{field:"deletedOn"},{field:"xactPublicationDate", filter: 'agDateColumnFilter', cellRenderer: 'dateCellRenderer'},{field:"xactPublication",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'},{field:"xactPriced",floatingFilterComponent: 'customBooleanFloatingFilter', filter: 'customBooleanFilter', cellRenderer: 'booleanCellRenderer'}],
      filter: {},
      sort: []
    }
  }

  public static pltColumns: any[];
  public static config: any;
}
