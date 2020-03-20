import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import * as _ from "lodash";
import {NotificationService} from "../../../../../shared/services";
import * as fromWs from "../../../../store/actions";
import * as fromRiskLink from "../../../../store/actions/risk_link.actions";

@Component({
    selector: 'financial-persp-selection-dialog',
    templateUrl: './financial-persp-selection-dialog.component.html',
    styleUrls: ['./financial-persp-selection-dialog.component.scss']
})
export class FinancialPerspSelectionDialogComponent implements OnInit, OnChanges {

    @Input('visible')
    isVisible = false;

    @Input('data')
    data: { analysis, epCurves };

    /**
     * Context to define if the Workspace Type is Fac or Treaty
     */
    @Input('context')
    context;

    @Output('close')
    closeEmitter = new EventEmitter();

    @Output('loadEpCurves')
    loadEpCurvesEmitter = new EventEmitter();

    @Output('override')
    overrideEmitter = new EventEmitter();

    rdmFilter = 'All';

    distinctRdms = [];

    changes = {analysis: {}, epCurves: {}, fpApplication: 'currentSelection'};

    allCheckedAnalysis: boolean = false;
    indeterminateAnalysis: boolean = false;
    allCheckedEpc: boolean = false;
    indeterminateEpc: boolean = false;

    selectedRowsAnalysis;
    selectedRowsEpc;

    loadingEpc:boolean= true;

    colsFinancialAnalysis = [
        {
            field: 'selected',
            header: '',
            width: '25px',
            type: 'select',
            sorting: '',
            filtered: false,
            highlight: false,
            visible: true
        },
        {
            field: 'rlId',
            header: 'ID',
            width: '35px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'analysisName',
            header: 'Name',
            width: '100px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'analysisDescription',
            header: 'Description',
            width: '150px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'rpCode',
            header: 'Region Peril',
            width: '70px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'ty',
            header: 'TY',
            width: '40px',
            type: 'iconIndicator',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'financialPerspectives',
            header: 'Financial Perspective',
            width: '150px',
            type: 'tags',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
    ];

    colsFinancialStandard = [
        {
            field: 'selected',
            header: '',
            width: '25px',
            type: 'select',
            sorting: '',
            filtered: false,
            highlight: false,
            visible: true
        },
        {
            field: 'financialPerspective',
            header: 'Code',
            width: '40px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'description',
            header: 'Financial Perspective',
            width: '300px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'ccy',
            header: 'CCY',
            width: '60px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true
        },
        {
            field: 'purePremium',
            header: 'AAL',
            width: '100px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true,
            number: true
        },
        {
            field: 'stdDev',
            header: 'STD DEV',
            width: '120px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true,
            number: true
        },
        {
            field: 'oep50',
            header: 'OEP 50',
            width: '50px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true,
            number: true
        },
        {
            field: 'oep100',
            header: 'OEP 100',
            width: '50px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true,
            number: true
        },
        {
            field: 'oep250',
            header: 'OEP 250',
            width: '50px',
            type: 'text',
            sorting: '',
            filtered: true,
            highlight: false,
            visible: true,
            number: true
        },
    ];

    constructor(private _notificationService: NotificationService) {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.data) {
            this._loadEpCurvesChanges();
        }
        this.updateTreeStateCheckBoxAnalysis();
        this.updateTreeStateCheckBoxEpc();
    }


    ngOnInit() {
        this._loadAnalysisChanges();
        this.distinctRdms = ['All', ..._.uniq(_.map(this.data.analysis, analysis => analysis.rdmName))];
        const firstKey = _.first(_.keys(this.changes.analysis));
        this.changes.analysis[firstKey].selected = true;
        this.loadEpCurvesEmitter.emit(firstKey);
    }

    updateAllChecked(nextValue, type) {
        // console.log('check all', this.changes.analysis, nextValue);
        if (type == 'analysis') {
            _.forEach(this.changes.analysis, (analysis:any, index) => this.changes.analysis[index] = {
                ...analysis,
                selected: nextValue
            });
            this.updateTreeStateCheckBoxAnalysis();
        } else if (type == 'epc') {
            _.forEach(this.changes.epCurves, (epc:any, index) => this.changes.epCurves[index] = {
                ...epc,
                selected: nextValue
            });
            this.updateTreeStateCheckBoxEpc();
        }
    }

    getSelection(data, type) {
        // console.log('getSelction', {data, type, changes: this.changes.epCurves} );
        if (type == 'analysis') {
            const ids = _.map(data, a => a.rlAnalysisId);
            _.forEach(this.changes.analysis, (analysis, index) => {
                this.changes.analysis[index].selected = _.includes(ids, Number(index));
            });
            this.updateTreeStateCheckBoxAnalysis();
        } else if (type == 'epc') {
            const codes = _.map(data, d => d.financialPerspective);
            _.forEach(this.changes.epCurves, (epc:any, index) => {
                this.changes.epCurves[index].selected = _.includes(codes, epc.code);
            });
            this.updateTreeStateCheckBoxEpc();
        }
    }

    selectRdm(item) {
        // console.log('select RDM', item);
    }

    checkAnalysisRow(analysisId) {
        this.changes.analysis[analysisId].selected = !this.changes.analysis[analysisId].selected;
        this.loadingEpc= true;
        if (this._countSelectedAnalysis() == 1)
            this.loadEpCurvesEmitter.emit(analysisId);
        this.updateTreeStateCheckBoxAnalysis();
    }

    toggleAnalysisSelection(analysisId) {
        _.forEach(this.changes.analysis, (analysis, index) => {
            this.changes.analysis[index].selected = analysisId == index;
        });
        this.loadingEpc= true;
        if (this._countSelectedAnalysis() == 1)
            this.loadEpCurvesEmitter.emit(analysisId);
        this.updateTreeStateCheckBoxAnalysis();
    }

    checkRpCurveRow(rowIndex) {
        this.changes.epCurves[rowIndex].selected = !this.changes.epCurves[rowIndex].selected;
        this.updateTreeStateCheckBoxEpc();
    }

    toggleEpCurveSelection(rowIndex) {
        _.forEach(this.changes.epCurves, (epc, index) => {
            this.changes.epCurves[index].selected = rowIndex == index;
        });
        this.updateTreeStateCheckBoxEpc();
    }

    onFpApplicationChange(evt) {
        // console.log('fp application change', evt);
        this.changes.fpApplication = evt;
    }

    applyChanges() {
        /**
         * Check that no multiple FPs selection is applied
         */
        if (_.upperCase(this.context) == 'FAC' && !this.isUniqueFinancialPerspective(_.values(this.changes.analysis))) {
            // Alert that you cannot do it for multiple FP per analysis
            this._notificationService.createNotification('Error',
                'Please make sure that you choose one Financial Perspective per Analysis!',
                'error', 'bottomRight', 4000);
            return;
        }
        this.overrideEmitter.emit(this.changes.analysis);
    }

    close() {
        this.closeEmitter.emit();
    }

    removeFP(analysisId, value: any) {
        event.stopPropagation();
        this.changes.analysis[analysisId].financialPerspectives = _.filter(
            this.changes.analysis[analysisId].financialPerspectives,
            item => item != value);
    }

    addFinancialPersp() {
        const selectedFps = this._getSelectedFps();
        // console.log('FP value', this.changes.fpApplication);
        switch (this.changes.fpApplication) {
            case 'All':
                this._addFpToAllAnalysis(selectedFps);
                break;
            case 'currentRDM':
                if (this.rdmFilter != 'All')
                    _.forEach(this.changes.analysis, (analysis: any, index) => {
                        if (analysis.rdmName == this.rdmFilter)
                            analysis.financialPerspectives = _.uniq([...analysis.financialPerspectives, ...selectedFps]);
                    });
                else
                    this._addFpToAllAnalysis(selectedFps);
                break;
            case 'currentSelection':
                _.forEach(
                    _.filter(this.changes.analysis, (item: any) => item.selected),
                    (analysis: any) => {
                        analysis.financialPerspectives = _.uniq([...analysis.financialPerspectives, ...selectedFps]);
                    });
                break;
        }
    }

    replaceFinancialPersp() {
        const selectedFps = this._getSelectedFps();
        switch (this.changes.fpApplication) {
            case 'All':
                this._replaceFpToAllAnalysis(selectedFps);
                break;
            case 'currentRDM':
                if (this.rdmFilter != 'All')
                    _.forEach(this.changes.analysis, (analysis: any) => {
                        if (analysis.rdmName == this.rdmFilter)
                            analysis.financialPerspectives = _.uniq([...selectedFps]);
                    });
                else
                    this._addFpToAllAnalysis(selectedFps);
                break;
            case 'currentSelection':
                _.forEach(
                    _.filter(this.changes.analysis, (item: any) => item.selected),
                    (analysis: any) => {
                        analysis.financialPerspectives = _.uniq([...selectedFps]);
                    });
                break;
        }
    }

    private _addFpToAllAnalysis(selectedFps: string[]) {
        _.forEach(this.changes.analysis, (analysis: any) => {
            analysis.financialPerspectives = _.uniq([...analysis.financialPerspectives, ...selectedFps]);
        });
    }

    private _replaceFpToAllAnalysis(selectedFps: string[]) {
        _.forEach(this.changes.analysis, (analysis: any) => {
            analysis.financialPerspectives = _.uniq([...selectedFps]);
        });
    }

    private _getSelectedFps(): string[] {
        return _.toArray(this.changes.epCurves)
            .filter((item: any) => item.selected)
            .map((item: any) => item.code);
    }


    private _loadAnalysisChanges() {
        this.changes.analysis = _.reduce(this.data.analysis, (result, value, index) => {
            const {financialPerspectives, rlAnalysisId} = value;
            return _.merge(result, {[rlAnalysisId]: {selected: false, financialPerspectives, rdmName: value.rdmName}})
        }, {});
    }

    private _loadEpCurvesChanges() {
        this.changes.epCurves = _.reduce(this.data.epCurves, (result, value, index) => {
            return _.merge(result, {[index]: {selected: false, code: value.financialPerspective}})
        }, {});
        this.loadingEpc= false;
    }

    private _countSelectedAnalysis() {
        return _.toArray(this.changes.analysis).filter((item: any) => item.selected).length;
    }

    private isUniqueFinancialPerspective(data) {
        for (let item of data) {
            if (_.size(item.financialPerspectives) != 1)
                return false;
        }
        return true;
    }

    private updateTreeStateCheckBoxAnalysis() {
        const selectionAnalysis = _.filter(this.data.analysis, item => (this.changes.analysis[item.rlAnalysisId] || {selected: false}).selected);
        this.allCheckedAnalysis = selectionAnalysis.length === this.data.analysis.length && selectionAnalysis.length > 0;
        this.indeterminateAnalysis = (selectionAnalysis.length < this.data.analysis.length && selectionAnalysis.length > 0);
    }

    private updateTreeStateCheckBoxEpc() {
        const selectedEpCurves = _.filter(this.data.epCurves, (item, index) => (this.changes.epCurves[index] || {selected: false}).selected);
        this.allCheckedEpc = selectedEpCurves.length === this.data.epCurves.length && selectedEpCurves.length > 0;
        this.indeterminateEpc = (selectedEpCurves.length < this.data.epCurves.length && selectedEpCurves.length > 0);
    }

}
