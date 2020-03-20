/*
 * Date : 21/2/2020.
 * Author : Reda El Morchadi
 */

export const FAKEDATA = {
    columns:[
    { field: 'euws', header: 'EUWS' },
    { field: 'iteq', header: 'ITEQ' },
    { field: 'greq', header: 'GREQ' },
    { field: 'treq', header: 'TREQ' },
    { field: 'pteq', header: 'PTEQ' },
    { field: 'nzeq', header: 'NZEQ' },
    { field: 'nahu', header: 'NAHU' },
    { field: 'useq', header: 'USEQ' },
    { field: 'cneq', header: 'CNEQ' },
    { field: 'cnty', header: 'CNTY' },
    { field: 'jpws', header: 'JPWS' }],
    frozenColumns:[
    { field: 'country', header: 'Metric' },
    { field: 'expectedTiv', header: 'Expected' },
    { field: 'totalTiv', header: 'TOTAL' },
    { field: 'diff', header: 'Diff' }],
    values:[
    { metric: "Australia",state:"", stateCount:1, expected:8020743, total:8020743, diff:null, euws:2352215411, iteq:5326451254,nahu:2552153234},
    { metric: "Austria",state:"" , stateCount:1, expected:786786, total:8020743, diff:null, pteq:2352215411, nzeq:5326451254,nahu:2552153234 },
    { metric: "Belgium", state:"", stateCount:1, expected:7867867, total:786786786, diff:null, nahu:2552153234,useq:355471821 },
    { metric: "Canada", state:"", stateCount:7, expected:54431687, total:8020743,  diff:null, iteq:5326451254, },
    { metric: "Canada", stateCount:0, state:"British Columbia", expected:54431687, total:8020743,  diff:null, iteq:5326451254, },
    { metric: "Canada", stateCount:0, state:"Manitoba", expected:78967456, total:8020743, diff:null, pteq:2352215411, nzeq:5326451254,useq:355471821 },
    { metric: "Canada", stateCount:0, state:"Quebec", expected:78967456, total:8020743, diff:null, pteq:2352215411, nzeq:5326451254,useq:355471821 },
    { metric: "China", state:"", stateCount:1, expected:78967456, total:8020743, diff:null, pteq:2352215411, nzeq:5326451254,useq:355471821 },
    { metric: "Czech Republic", state:"", stateCount:1, expected:8020743, total:8020743, diff:null, pteq:2352215411, nzeq:5326451254},
    { metric: "Canada", stateCount:0, state:"Alberta", expected:54431687, total:8020743,  diff:null, iteq:5326451254 },
    { metric: "Canada", stateCount:0, state:"Saskatchewa", expected:54431687, total:8020743,  diff:null, iteq:5326451254 },
    { metric: "Denmark", state:"", stateCount:1, expected:8020743, total:8020743, diff:null,  euws:2352215411, iteq:5326451254,treq:355471821},
    { metric: "France", state:"", stateCount:1, expected:46767866, total:8020743, diff:null,  nzeq:5326451254,useq:355471821 },
    { metric: "Germany", state:"", stateCount:1, expected:798564645, total:8020743, diff:null, pteq:2352215411,nahu:2552153234},
    { metric: "Canada", stateCount:0, state:"Ontario", expected:78967456, total:8020743, diff:null, pteq:2352215411, nzeq:5326451254,useq:355471821 },
    { metric: "Indonesia", state:"", stateCount:1, expected:46878678, total:8020743, diff:null,   euws:2352215411, iteq:5326451254,greq:2552153234,treq:355471821 },
    { metric: "Ireland", state:"", stateCount:1, expected:78578678, total:8020743, diff:null, pteq:2352215411,nahu:2552153234},
    { metric: "Italy", state:"", stateCount:1, expected:876762313, total:8020743, diff:null,   euws:2352215411,greq:2552153234,treq:355471821,useq:355471821 },
    { metric: "Jamaica", state:"", stateCount:1, expected:1, total:8020743, diff:null, pteq:2352215411, nzeq:5326451254,nahu:2552153234,useq:355471821 },
    { metric: "Japan", state:"", stateCount:1, expected:8020743, total:8020743, diff:null,   euws:2352215411, iteq:5326451254,greq:2552153234,treq:355471821,useq:355471821 },
    { metric: "Morocco", state:"", stateCount:1, expected:8020743, total:8020743, diff:null,   euws:2352215411, iteq:5326451254,greq:2552153234},
    { metric: "Wales", state:"", stateCount:1, expected:8020743, total:8020743, diff:null, greq:2552153234,treq:355471821,useq:355471821 }],
    frozenValues:[
    { metric : "TOTAL", state:"", expected: 33385224541, total: 335454127442, diff:null,
        euws:2352215411, iteq:5326451254,greq:2552153234,treq:355471821,
        pteq:2352215411, nzeq:5326451254,nahu:2552153234,useq:355471821,
        cneq:2352215411, cnty:5326451254,jpws:2552153234 }]
}
export const HEADERCONFIG = {
    currencies:[{header:'USD',value:'USD'},{header:'EUR',value:'EUR'}],
    divisions:[/*{header:'Division 1',value:'division1'},{header:'Division 2',value:'division2'}*/],
    exposureViews:[{header:'TIV',value:'tiv'},{header:'Avg TIV',value:'AvgTiv'},{header:'#Risk',value:'risk'},{header:'%',value:'percentage'}],
    financialUnits:[],
    portfolios : [{header:'Portfolio 1',value:'portfolio1'},{header:'Portfolio 2',value:'portfolio2'}],
    view:''
}