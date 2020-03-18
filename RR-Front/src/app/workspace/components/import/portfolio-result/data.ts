export default {
  Treaty:{
    scrollableCols:[
      // {field: 'exposedLocation', header: 'Exposed Location', width: '90px', type: 'indicator', sorting: ', filtered: true, highlight: false, visible: true},
      {field: 'agCurrency', header: 'Source Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'targetCurrency', header: 'Target Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: true, visible: true, edit: true},
      {field: 'unitMultiplier', header: 'Unit Multiplier', width: '50px', type: 'number', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'proportion', header: 'Proportion', width: '50px', type: 'percentage', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'edmName', header: 'EDM', width: '250px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'importLocationLevel', header: 'Location Level Scan', width: '50px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true},
      /*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
    ],
    fixedCols:  [
      {field: 'selected', header: '', width: '25px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'scan', header: '', width: '25px', type: 'scan', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'status', header: 'Status', width: '40px', type: 'progress', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'rlId', header: 'ID', width: '40px', type: 'text', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'number', header: 'Number', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
      {field: 'name', header: 'Name', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
    ]
  },
  FAC:{
    scrollableCols:[
      // {field: 'exposedLocation', header: 'Exposed Location', width: '90px', type: 'indicator', sorting: ', filtered: true, highlight: false, visible: true},
      {field: 'divisions', header: 'Divisions', width: '100px', type: 'division', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'agCurrency', header: 'Source Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'targetCurrency', header: 'Target Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: true, visible: true, edit: true},
      {field: 'edmName', header: 'EDM', width: '250px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'unitMultiplier', header: 'Unit Multiplier', width: '50px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'proportion', header: 'Proportion', width: '50px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
      {field: 'importLocationLevel', header: 'Location Level Scan', width: '50px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true},
      /*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
    ],
    fixedCols:  [
      {field: 'selected', header: '', width: '25px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'scan', header: '', width: '25px', type: 'scan', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'status', header: 'Status', width: '40px', type: 'progress', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'rlId', header: 'ID', width: '40px', type: 'text', sorting: '', filtered: false, highlight: false, visible: true},
      {field: 'number', header: 'Number', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
      {field: 'name', header: 'Name', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
    ]
  }
}
