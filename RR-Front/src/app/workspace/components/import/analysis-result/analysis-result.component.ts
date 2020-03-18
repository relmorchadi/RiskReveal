import {
    ChangeDetectorRef,
    Component,
    EventEmitter,
    Input,
    OnChanges,
    OnInit,
    Output,
    SimpleChanges,
    ViewChild
} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import * as fromRiskLink from "../../../store/actions/risk_link.actions";
import componentData from "./data";
import * as _ from 'lodash';
import {WorkspaceState} from "../../../store/states";
import {take} from "rxjs/operators";
import * as fromWs from "../../../store/actions";
import {Debounce} from "../../../../shared/decorators";

@Component({
    selector: 'analysis-result',
    templateUrl: './analysis-result.component.html',
    styleUrls: ['./analysis-result.component.scss']
})
export class AnalysisResultComponent implements OnInit, OnChanges {

    @Input('context')
    context;

    @ViewChild('analysisResultTable')
    tables: any;

    @Input('data')
    data: { analysis, epCurves, targetRaps, regionPerilsByAnalysis } = {
        analysis: [],
        epCurves: [],
        targetRaps: [],
        regionPerilsByAnalysis: {}
    };

    @Output('save')
    saveEmitter = new EventEmitter();

    @Select(WorkspaceState.getSelectedProject) selectedProject$;

    isCollapsed;
    scrollableColsResults;
    frozenColsResults;

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
        ],
        financialPerspective: {
            'CL': 'Client Loss',
            'FA': 'Facultative Reinsurance Loss',
            'GR': 'Gross Loss',
            'GU': 'Ground Up Loss',
            'QS': 'Quota Share Treaty Loss',
            'RC': 'Net Loss Post Corporate Cat',
            'RG': 'Reinsurance Gross Loss',
            'RL': 'Net Loss Pre Cat',
            'RN': 'Reinsurance Net Loss',
            'RP': 'Net Loss Post Cat',
            'SS': 'Surplus Share Treaty Loss',
            'TY': 'Treaty Loss',
            'WX': 'Working Excess Treaty Loss'
        },
        targetRaps: [
            {value: 'RL_ACEQ_Mv9.0_Ev10.0_S-56-LTR (Default)'},
            {value: 'RL_ACEQ_Mv9.0_Ev11.0_S-56-LTR (Default)'},
            {value: 'RL_ACEQ_Mv9.0_Ev13.0_S-56-LTR (Default)'},
            {value: 'RL_ACEQ_Mv9.0_Ev13.1_S-56-LTR (Default)'},
            {value: 'RL_ACEQ_Mv9.0_Ev15.0_S-56-LTR (Default)'},
            {value: 'RL_ACEQ_Mv9.0_Ev16.0_S-56-LTR (Default)'},
            {value: 'RL_ACEQ_Mv9.0_Ev9.0_S-56-LTR (Default)'},
            {value: 'RL_AHEQ_Mv9.0_Ev10.0_S-60-LTR (Default)'},
            {value: 'RL_AHEQ_Mv9.0_Ev11.0_S-60-LTR (Default)'},
            {value: 'RL_EUEQ_Mv11.0_Ev11.0_S-61-LTR (Default)'},
            {value: 'RL_EUWS_Mv11.2_Ev11.0_S-65-LTR'},
        ].map(item => ({...item, selected: false}))
    };

    selectedRows: any;
    showOverrideRpDialog = false;
    showSelectFinancialPerspDialog = false;
    showOverridePEQTDialog = false;
    showOverrideOccurrenceBasisDialog = false;
    lastAnalysisIndex = null;

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
            command: (event) => this.deleteAnalysisFromBasket(event)
        },
    ];

    allCheckedItems=false;
    indeterminateItems=false;

    constructor(private store: Store, private cdr:ChangeDetectorRef) {
    }

    ngOnInit() {
        this._loadDataTableCols();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.context && changes.context.currentValue) {
            this._loadDataTableCols();
        }
        this.updateTreeStateCheckBox();
    }

    resetSort() {
        this.tables.sortOrder = this.tables.defaultSortOrder;
        this.tables.sortField = '';
        this.tables.multiSortMeta = null;
        this.tables.tableService.onSort(null);
    }

    private _loadDataTableCols() {
        if (_.toUpper(this.context) == 'FAC') {
            this.scrollableColsResults = componentData.FAC.scrollableCols;
            this.frozenColsResults = componentData.FAC.fixedCols;
        } else if (_.toUpper(this.context) == 'TREATY') {
            this.scrollableColsResults = componentData.Treaty.scrollableCols;
            this.frozenColsResults = componentData.Treaty.fixedCols;
        }
    }

    onFilter(kw, field, mode){
        return this.tables.filter(kw, field, 'contains');
    }

    updateAllChecked(nextValue) {
        if(nextValue)
            this.store.dispatch(new fromRiskLink.ToggleAnalysisResultSelectionAction({
                action: 'selectChunk', ids : _.map(this.data.analysis, item => item.rlAnalysisId)
            }));
        else {
            this.store.dispatch(new fromRiskLink.ToggleAnalysisResultSelectionAction({
                action: 'selectChunk', ids : []
            }));
        }
    }

    sortChange(field, sorting, target) {
        console.log('sort', {field, sorting, target});
    }

    updateRowData(key, value, index) {
        this.store.dispatch(new fromRiskLink.PatchAnalysisResultAction({
            key, value, index
        }));
    }

    updateSelection(id){
        if (!(window as any).event.ctrlKey && !(window as any).event.shiftKey) {
            this.store.dispatch(new fromRiskLink.ToggleAnalysisResultSelectionAction({
                action: 'selectChunk', ids : [id]
            }));
        }
    }

    getSelection(data){
        //if (data.length > 1) {
            this.store.dispatch(new fromWs.ToggleAnalysisResultSelectionAction({
                action: 'selectChunk',
                ids: _.map(data, a => a.rlAnalysisId)
            }));
       // }
    }

    openFinancialP(fp) {
        this.showSelectFinancialPerspDialog = true;
    }

    overrideRegionPerilOccurrenceBasis(row, colType, index) {
        this.lastAnalysisIndex = row.rlAnalysisId;
        if (colType == 'Rp') {
            this.showOverrideRpDialog = true;
        } else if (colType == 'Ob') {
            this.showOverrideOccurrenceBasisDialog = true;
        }
    }

    overrideRegionPeril(changes) {
        this.store.dispatch(new fromRiskLink.OverrideAnalysisRegionPeril(changes));
        this.showOverrideRpDialog = false;
    }

    loadSourceEpCurveHeaders(rlAnalysisId) {
        this.store.dispatch(new fromRiskLink.LoadSourceEpCurveHeaders({rlAnalysisId}));
    }

    overrideFinancialPersp(changes) {
        this.store.dispatch(new fromRiskLink.OverrideFinancialPerspective(changes));
        this.showSelectFinancialPerspDialog = false;
    }

    loadTargetRaps(rlAnalysisId) {
        this.store.dispatch(new fromRiskLink.LoadTargetRaps({rlAnalysisId}));
    }

    overridePEQTs(changes) {
        this.closePEQTOverrideDialog();
        this.store.dispatch(new fromRiskLink.OverrideTargetRaps({changes}));
    }

    closePEQTOverrideDialog() {
        this.showOverridePEQTDialog = false;
        this.store.dispatch(new fromRiskLink.ClearTargetRaps());
    }

    overrideOccurrenceBasis(occurrenceBasis: any) {
        this.showOverrideOccurrenceBasisDialog = false;
        this.store.dispatch(new fromRiskLink.OverrideOccurrenceBasis({
            occurrenceBasis,
            analysisIndex: this.lastAnalysisIndex
        }));
    }

    deleteAnalysisFromBasket(event) {
        this.selectedProject$.pipe(take(1))
            .subscribe(({projectId}) => {
                const ids = _.map(
                    _.filter(this.data.analysis, item => item.selected),
                    (item: any) => item.rlAnalysisId
                );
                this.store.dispatch(new fromRiskLink.DeleteFromImportBasketAction({
                    type: 'ANALYSIS', projectId,
                    ids: _.size(ids) ? ids : [this.contextSelectedItem.rlAnalysisId]
                }))
            });
    }

    saveAnalysisSelection() {
        this.saveEmitter.emit('ANALYSIS');
    }

    private updateTreeStateCheckBox(){
        const selection = _.filter(this.data.analysis, item => item.selected);
        this.allCheckedItems = selection.length === this.data.analysis.length && selection.length > 0;
        this.indeterminateItems = (selection.length < this.data.analysis.length && selection.length > 0);
    }
}
