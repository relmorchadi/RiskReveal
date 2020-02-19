import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from "@angular/core";
import {Store} from "@ngxs/store";
import {Observable, Subscription} from "rxjs";

@Component({
    selector: 'exposures-main-table',
    templateUrl: './exposures-main-table.component.html',
    styleUrls: ['./exposures-main-table.component.scss']
})

export class ExposuresMainTableComponent implements OnInit, OnDestroy {


    @Input('tableConfig') tableConfig: Observable<any>;
    @Output('actionDispatcher') actionDispatcher: EventEmitter<any> = new EventEmitter<any>();
    tableConfigSub: Subscription;
    columns: [];
    frozenColumns: [];
    values: [];
    frozenValues: [];

    constructor(private store: Store) {

    }


    ngOnInit(): void {
        this.tableConfigSub = this.tableConfig.subscribe((config: any) => {
            this.columns = config.columns;
            this.frozenColumns = config.frozenColumns;
            this.values = config.values;
            this.frozenValues = config.frozenValues;
        })
    }

    ngOnDestroy(): void {
        this.tableConfigSub.unsubscribe();
    }


    onMouseEnter(rowData) {
        rowData.hover = true;
    }

    onMouseLeave(rowData) {
        rowData.hover = false;
    }

    onSort($event: any) {
        this.actionDispatcher.emit({field: $event.field, order: $event.order})
    }
}