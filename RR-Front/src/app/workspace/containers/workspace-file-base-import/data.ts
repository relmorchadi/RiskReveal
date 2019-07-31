const directoryTree = {
  data:
    [
      {
        label: 'Documents',
        data: 'Documents Folder',
        expandedIcon: 'fa fa-folder-open',
        collapsedIcon: 'fa fa-folder',
        children: [{
          label: 'Work',
          data: 'Work Folder',
          expandedIcon: 'fa fa-folder-open',
          collapsedIcon: 'fa fa-folder',
          children: [{label: 'Expenses.doc', icon: 'fa fa-file-word-o', data: 'Expenses Document'},
            {label: 'Resume.doc', icon: 'fa fa-file-word-o', data: 'Resume Document'}]
        },
          {
            label: 'Home',
            data: 'Home Folder',
            expandedIcon: 'fa fa-folder-open',
            collapsedIcon: 'fa fa-folder',
            children: [{label: 'Invoices.txt', icon: 'fa fa-file-word-o', data: 'Invoices for this month'}]
          }]
      },
      {
        label: 'Pictures',
        data: 'Pictures Folder',
        expandedIcon: 'fa fa-folder-open',
        collapsedIcon: 'fa fa-folder',
        children: [
          {label: 'barcelona.jpg', icon: 'fa fa-file-image-o', data: 'Barcelona Photo'},
          {label: 'logo.jpg', icon: 'fa fa-file-image-o', data: 'PrimeFaces Logo'},
          {label: 'primeui.png', icon: 'fa fa-file-image-o', data: 'PrimeUI Logo'}]
      },
      {
        label: 'Movies',
        data: 'Movies Folder',
        expandedIcon: 'fa fa-folder-open',
        collapsedIcon: 'fa fa-folder',
        children: [{
          label: 'Al Pacino',
          data: 'Pacino Movies',
          children: [{label: 'Scarface', icon: 'fa fa-file-video-o', data: 'Scarface Movie'},
            {label: 'Serpico', icon: 'fa fa-file-video-o', data: 'Serpico Movie'}]
        },
          {
            label: 'Robert De Niro',
            data: 'De Niro Movies',
            children: [{label: 'Goodfellas', icon: 'fa fa-file-video-o', data: 'Goodfellas Movie'},
              {label: 'Untouchables', icon: 'fa fa-file-video-o', data: 'Untouchables Movie'}]
          }]
      }
    ]
};

const PltDataTables = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'scanned', header: '', width: '25px', type: 'scan', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'delete', header: '', width: '25px', type: 'action', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'user', header: 'User', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'fileType', header: 'File Type', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'fileName', header: 'File Name', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'filePath', header: 'File Path', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'modelSystemInfo', header: 'Model System Information', width: '150px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'resultName', header: 'Result Name', width: '100px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'resultDataName', header: 'Result Database Name', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'sourceRef', header: 'Source Reference', width: '100px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'currency', header: 'Currency', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'fp', header: 'FP', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'peril', header: 'Peril', width: '60px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'region', header: 'Region', width: '60px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'oep100', header: 'OEP 100', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'oep250', header: 'OEP 250', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'aal', header: 'AAL', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'fileDate', header: 'File Date', width: '70px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'runDate', header: 'Run Date', width: '70px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'years', header: 'Years', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
];

export const DataTables = {
  directoryTree,
  PltDataTables
};
