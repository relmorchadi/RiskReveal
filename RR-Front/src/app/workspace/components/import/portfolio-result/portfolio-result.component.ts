import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import * as fromRiskLink from "../../../store/actions/risk_link.actions";
import {Select, Store} from "@ngxs/store";
import componentData from './data';
import * as _ from 'lodash';
import {WorkspaceState} from "../../../store/states";
import {take} from "rxjs/operators";
import * as fromWs from "../../../store/actions";

@Component({
    selector: 'portfolio-result',
    templateUrl: './portfolio-result.component.html',
    styleUrls: ['./portfolio-result.component.scss']
})
export class PortfolioResultComponent implements OnInit, OnChanges {

    isCollapsed;

    @Input('data')
    portfolios;

    @Input('context')
    context;

    scrollableColsSummary;

    frozenColsSummary;

    @Output('save')
    saveEmitter = new EventEmitter();

    @Select(WorkspaceState.getSelectedProject) selectedProject$;

    @ViewChild('exposureSummaryTable')
    tables: any;

    allCheckedItems:boolean;
    indeterminateItems:boolean;

    selectedRows;

    refs = {
        currencies: [
            {label: "Main Liablity Currency (MLC)", value: "USD"},
            {label: "Underlying Analysis Currency (UAC)", value: "UAC"},
            {label: "AED", value: "AED"},
            {label: "AFA", value: "AFA"},
            {label: "AFN", value: "AFN"},
            {label: "ALK", value: "ALK"},
            {label: "ALL", value: "ALL"},
            {label: "BBT", value: "BBT"},
            {label: "BBL", value: "BBL"},
            {label: "BMD", value: "BMD"},
            {label: "MGA", value: "MGA"},
            {label: "USD", value: "USD"},
            {label: "XXC", value: "XXC"}
        ]
    };


    contextSelectedItem: any;
    itemCm = [
        // {
        //   label: 'Edit', icon: 'pi pi-pencil', command: (event) => {
        //     // this.editRowPopUp = true;
        //   }
        // },
        {
            label: 'Delete Item',
            icon: 'pi pi-trash',
            command: (event) => this.deletePortfolioFromBasket(event)
        },
    ];

    constructor(private store: Store) {
    }

    ngOnInit() {
        this.loadTablesCols();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.context) {
            this.loadTablesCols();
        }
        this.updateTreeStateCheckBox();
    }

    private loadTablesCols() {
        if (_.toUpper(this.context) == 'FAC') {
            this.scrollableColsSummary = componentData.FAC.scrollableCols;
            this.frozenColsSummary = componentData.FAC.fixedCols;
        } else if (_.toUpper(this.context) == 'TREATY') {
            this.scrollableColsSummary = componentData.Treaty.scrollableCols;
            this.frozenColsSummary = componentData.Treaty.fixedCols;
        } else {
            console.error('Unknown Context ', this.context);
        }
    }

    resetSort(){
        this.tables.sortOrder = this.tables.defaultSortOrder;
        this.tables.sortField = '';
        this.tables.multiSortMeta = null;
        this.tables.tableService.onSort(null);
    }

    updateRowData(key, value, index) {
        this.store.dispatch(new fromRiskLink.PatchPortfolioResultAction({
            key, value, index
        }))
    }

    updateAllChecked(nextValue) {
        if(nextValue)
            this.store.dispatch(new fromRiskLink.TogglePortfolioResultSelectionAction({
                action: 'selectChunk', ids : _.map(this.portfolios, item => item.rlPortfolioId)
            }));
        else {
            this.store.dispatch(new fromRiskLink.TogglePortfolioResultSelectionAction({
                action: 'selectChunk', ids : []
            }));
        }
    }

    getSelection(data){
        console.log('get selection', data);
        //if (data.length > 1) {
        this.store.dispatch(new fromWs.TogglePortfolioResultSelectionAction({
            action: 'selectChunk',
            ids: _.map(data, a => a.rlPortfolioId)
        }));
        // }
    }

    updateSelection(id){
        if (!(window as any).event.ctrlKey && !(window as any).event.shiftKey) {
            this.store.dispatch(new fromRiskLink.TogglePortfolioResultSelectionAction({
                action: 'selectChunk', ids : [id]
            }));
        }
    }

    selectRows(rowData, index) {
        this.updateRowData('selected', rowData.selected, index);
    }

    deleteFromBasket(id, type) {

    }

    deletePortfolioFromBasket(event) {
        this.selectedProject$.pipe(take(1))
            .subscribe(({projectId}) => {
                const ids = _.map(
                    _.filter(this.portfolios, item => item.selected),
                    (item: any) => item.rlPortfolioId
                );
                this.store.dispatch(new fromRiskLink.DeleteFromImportBasketAction({
                    type: 'PORTFOLIO', projectId,
                    ids: _.size(ids) ? ids : [this.contextSelectedItem.rlPortfolioId],
                }))
            });
    }

    savePortfolioSelection() {
        this.saveEmitter.emit('PORTFOLIO');
    }

    private updateTreeStateCheckBox(){
        const selection = _.filter(this.portfolios, item => item.selected);
        this.allCheckedItems = selection.length === this.portfolios.length && selection.length > 0;
        this.indeterminateItems = (selection.length < this.portfolios.length && selection.length > 0);
    }

}
