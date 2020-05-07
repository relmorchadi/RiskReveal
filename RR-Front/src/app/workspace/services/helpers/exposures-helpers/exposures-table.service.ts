import {Injectable} from '@angular/core';
import {Observable, BehaviorSubject, forkJoin} from "rxjs";
import {ExposuresMainTableConfig} from "../../../model/exposures-main-table-config.model";
import {ExposuresApi} from "../../api/exposures.api";
import {map} from "rxjs/operators";
import {FAKEDATA} from "../../../containers/workspace-exposures/fakeExposuresData";
import * as _ from "lodash";

@Injectable()
export class ExposuresTableService {


    private tableConfig: any[];
    tableConfig$: BehaviorSubject<ExposuresMainTableConfig>;


    constructor(private _api: ExposuresApi) {
        this.tableConfig$ = new BehaviorSubject<ExposuresMainTableConfig>(new ExposuresMainTableConfig())
    }

    public loadTableConfig(headerConfig) {
        return this._api.loadTableConfig(
            this.constructHeaderConfig(headerConfig)
        ).pipe(map((tableConfig: any) => (this.constructTableConfig(tableConfig)))) as Observable<ExposuresMainTableConfig>;
    }

    public sortTableColumn(sortConfig) {
        return this._api.loadTableConfig(
            this.constructHeaderConfig(sortConfig)
        ).pipe(map((tableConfig: any) => (this.constructTableConfig(tableConfig)))) as Observable<ExposuresMainTableConfig>;
    }


    exportTable(headerConfig) {
        return this._api.exportTable(this.constructHeaderConfig(headerConfig));
    }

    filterRowRegionPeril(tableConfig,rowData: any) {
        return {
            data: tableConfig.data,
            columns:(tableConfig.columns.filter(row => rowData[row.field] != null)),
            frozenRow: tableConfig.frozenRow,
            frozenColumns: tableConfig.frozenColumns
        };
    }

    loadTableColumnsConfig() {
        return this._api.loadTableColumnsConfig();
    }

    constructTableConfig(tableConfig) {
        const res =  {
            data: tableConfig.data != null ? _.map(tableConfig.data, row => ({...row, ...row.regionPerils})):null,
            columns: _.map(tableConfig.columns, column => ({field: column, header: column})),
            frozenRow: tableConfig.frozenRow.regionPerils != {} ? [{...tableConfig.frozenRow, ...tableConfig.frozenRow.regionPerils}] : [],
            frozenColumns: tableConfig.data ? FAKEDATA.frozenColumns : []
        };
        console.log('tableConfig ===========> ',res);
        return res;
    }

    constructHeaderConfig(headerConfig) {

        return {
            currency:headerConfig.currency,
            division:headerConfig.division.divisionNumber,
            financialPerspective:headerConfig.financialPerspective,
            page:1,
            pageSize:20,
            portfolioName:headerConfig.portfolio,
            projectId:headerConfig.projectId,
            requestTotalRow:true,
            summaryType:'Summary By Country',
            regionPerilFilter: headerConfig.regionPerilFilter ? headerConfig.regionPerilFilter : null,
            type:headerConfig.exposureView
        }
    }
}