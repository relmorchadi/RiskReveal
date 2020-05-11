/*
 * Date : 20/2/2020.
 * Author : Reda El Morchadi
 */

import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    EventEmitter,
    Input,
    OnDestroy,
    OnInit,
    Output
} from "@angular/core";
import {Store} from "@ngxs/store";
import {ExposuresMainTableConfig} from "../../../model/exposures-main-table-config.model";
import {FAKEDATA} from "../../../containers/workspace-exposures/fakeExposuresData";

@Component({
    selector: 'exposures-main-table',
    templateUrl: './exposures-main-table.component.html',
    styleUrls: ['./exposures-main-table.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})

export class ExposuresMainTableComponent implements OnInit, AfterViewInit, OnDestroy {


    @Input('tableConfig') tableConfig: ExposuresMainTableConfig;
    @Output('actionDispatcher') actionDispatcher: EventEmitter<any> = new EventEmitter<any>();
    @Input('sortConfig') sortConfig: any;
    @Input('numberConfig') numberConfig: any;
    private selectedRowRegionPeril: any;
    private hoveredRow: any;
    private filteredColumn: string;
    private frozenColumns: any;

    constructor(private store: Store, changeDetectorRef: ChangeDetectorRef) {
        this.selectedRowRegionPeril = null;
        this.hoveredRow = null;
        this.frozenColumns = FAKEDATA.frozenColumns;
    }

    ngOnInit(): void {

    }

    ngAfterViewInit(): void {

    }

    ngOnDestroy(): void {

    }

    onSort($event: any) {
        this.filteredColumn = $event.field != this.filteredColumn ? $event.field : null;
        if (this.filteredColumn == 'totalTiv') this.filteredColumn = 'total';
        this.actionDispatcher.emit({type: 'sortTableColumn', payload: this.filteredColumn});
    }

    incrementSort(order) {
        switch (order) {
            case 0 :
                return 1;
            case 1 :
                return -1;
            case -1 :
                return 0;
        }
    }

    filterRowRegionPeril(rowData, rowIndex) {
        if (this.selectedRowRegionPeril != rowIndex) {
            this.actionDispatcher.emit({type: 'filterRowRegionPeril', payload: rowData});
            this.selectedRowRegionPeril = rowIndex;
        } else {
            this.actionDispatcher.emit({type: 'removeFilter', payload: null});
            this.selectedRowRegionPeril = null;
        }
    }
}