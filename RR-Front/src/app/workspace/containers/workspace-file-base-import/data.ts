const directoryTree = {
  data:
    [
      {
        label: 'RAMADANI Feta (U000886)',
        data: 'Movies Folder',
        expandedIcon: 'fa fa-folder-open',
        collapsedIcon: 'fa fa-folder',
        children: [{
          label: 'Test_Folder',
          data: 'Pacino Movies',
          children: [{label: '1',
            children: [
              {label: '1', icon: 'fa fa-folder', data: 'Scarface Movie'},
              {label: '2', icon: 'fa fa-folder', data: 'Serpico Movie'},
              {label: '3', icon: 'fa fa-folder', data: 'Serpico Movie'},
            ],
            expandedIcon: 'fa fa-folder-open', collapsedIcon: 'fa fa-folder',
            data: 'Scarface Movie'},
            {label: '2', icon: 'fa fa-folder', data: 'Serpico Movie'},
            {label: '3', icon: 'fa fa-folder', data: 'Serpico Movie'},
            ]
        }]
      },
      {
        label: 'Parry Huw (U000886)',
        data: 'Documents Folder',
        expandedIcon: 'fa fa-folder-open',
        collapsedIcon: 'fa fa-folder',
        children: [{
          label: 'EdgeCaseTestPLTs',
          icon: 'fa fa-folder',
          data: 'Work Folder'
          },
          {
            label: 'Inuring EdgeCase Tests',
            data: 'Home Folder',
            icon: 'fa fa-folder'
          },
          {
            label: 'SampleCatrader',
            data: 'Home Folder',
            expandedIcon: 'fa fa-folder-open',
            collapsedIcon: 'fa fa-folder',
            children: [{label: 'Amended Model Name', icon: 'fa fa-folder', data: 'Invoices for this month'}]
          },
          {
            label: 'SampleElements',
            data: 'Home Folder',
            expandedIcon: 'fa fa-folder-open',
            collapsedIcon: 'fa fa-folder',
            children: [{label: 'Amended Files', icon: 'fa fa-folder', data: 'Invoices for this month'}]
          }
        ]
      },
      {
        label: 'CHOY CHI (U001141)',
        data: 'Pictures Folder',
        expandedIcon: 'fa fa-folder-open',
        collapsedIcon: 'fa fa-folder',
        children: [
          {label: 'Elements_v10.0.0.0_PRD', icon: 'fa fa-folder', data: 'Barcelona Photo'},
          {label: 'RMS_HDA_v17.0_TST', icon: 'fa fa-folder', data: 'PrimeFaces Logo'},
          {label: 'THFL', icon: 'fa fa-folder', data: 'PrimeUI Logo'},
          {label: 'UAT Test Files', icon: 'fa fa-folder', data: 'PrimeUI Logo'}]
      },
      {
        label: 'BORDOY Roger (U001372)',
        data: 'Movies Folder',
        expandedIcon: 'fa fa-folder-open',
        collapsedIcon: 'fa fa-folder',
        children: [{
            label: 'Czech',
            data: 'Pacino Movies',
            icon: 'fa fa-folder'
          },
          {
            label: 'For Testing',
            data: 'De Niro Movies',
            expandedIcon: 'fa fa-folder-open',
            collapsedIcon: 'fa fa-folder',
            children: [
              {label: 'Cyprus', icon: 'fa fa-folder', data: 'Barcelona Photo'},
              {label: 'Czech', icon: 'fa fa-folder', data: 'Barcelona Photo'},
              {label: 'Hungary', icon: 'fa fa-folder', data: 'Barcelona Photo'},
              {label: 'Poland', icon: 'fa fa-folder', data: 'Barcelona Photo'},
              {label: 'Slovakia', icon: 'fa fa-folder', data: 'Barcelona Photo'},
              ]
          }]
      },
      {
        label: 'NGUYEN Viet Thanh Trung (U004602)',
        data: 'Movies Folder',
        icon: 'fa fa-folder'
      },
      {
        label: 'MACK Soon Ling (U005384)',
        data: 'Movies Folder',
        icon: 'fa fa-folder'
      },
      {
        label: 'DANG Hong Phuc (U006930)',
        data: 'Movies Folder',
        icon: 'fa fa-folder'
      },
      {
        label: 'Reveal Risk (USYS)',
        data: 'Movies Folder',
        icon: 'fa fa-folder'
      },
      {
        label: 'Unknown User (Y422)',
        data: 'Movies Folder',
        icon: 'fa fa-folder'
      }
    ]
};

const textFilesData = [
  {id: 0, name: '27_201804_PPS - 201804_PPS_ZAEQ_EventSetId_1190_100', selected: false},
  {id: 1, name: '27_201804_PPS - 201804_PPS_ZAEQ_EventSetId_1190_100', selected: false},
  {id: 2, name: '27_201804_PPS - 201804_PPS_ZAEQ_EventSetId_1190_100', selected: false},
  {id: 3, name: '31_2A_ALGERIA_Fire_SP - Analysis_2A_IF_Fire_SP_EventSetId_1190_100', selected: false},
  {id: 4, name: '351_2018 APAC TWN SOUTHCHINA ENG OS_Shinkong EventSetId_1190_100', selected: false},
  {id: 5, name: '360_SGDOOD YONGSHI TRAINING_Nationwide - TAIWAN EventSetId_1190_100', selected: false},
  {id: 6, name: '360_SGDOOD YONGSHI TRAINING_Nationwide - TAIWAN EventSetId_1190_100', selected: false},
  {id: 7, name: '27_201804_PPS - 201804_PPS_ZAEQ_EventSetId_1190_100', selected: false},
  {id: 8, name: '27_201804_PPS - 201804_PPS_ZAEQ_EventSetId_1190_100', selected: false}
];

const PltDataTables = [
  // {field: 'selected', header: '', width: '25px', type: 'select', sorted: false, filtered: false, highlight: false, visible: true, edit: false},
  {field: 'scanned', header: '', width: '25px', type: 'scan', sorted: false, filtered: false, highlight: false, visible: true, edit: false},
  {field: 'delete', header: '', width: '25px', type: 'action', sorted: false, filtered: false, highlight: false, visible: true, edit: false},
  {field: 'FileName', header: 'File Name', width: '130px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'RegionPeril', header: 'Region Peril', width: '90px', type: 'text', sorted: false, filtered: true, highlight: true, visible: true, edit: false},
  {field: 'Currency', header: 'Currency', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'TargetCurrency', header: 'Target Currency', width: '80px', type: 'text', sorted: false, filtered: true, highlight: true, visible: true, edit: false},
  {field: 'modelSystemInfo', header: 'Model System Information', width: '200px', type: 'info', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'FinPerspective', header: 'FP', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'User', header: 'User', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'FileType', header: 'File Type', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'User', header: 'File Path', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'ResultsName', header: 'Result Name', width: '170px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'ResultsDatabaseName', header: 'Result Database Name', width: '120px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'SourceResultsReference', header: 'Source Reference', width: '100px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'Peril', header: 'Peril', width: '60px', type: 'perilIndicator', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'Region', header: 'Region', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'UnitMultiplier', header: 'Unit Multiplier', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'Proportion', header: 'Proportion', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'OEP100', header: 'OEP 100', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'OEP250', header: 'OEP 250', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'AAL', header: 'AAL', width: '80px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'fileDate', header: 'File Date', width: '70px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'Run_Date', header: 'Run Date', width: '70px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
  {field: 'Years', header: 'Years', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
];

const importedFiles = [
  {field: 'selected', header: '', width: '25px', type: 'select', sorted: false, filtered: false, highlight: false, visible: true},
  {field: 'label', header: 'File Name', width: '265px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'createdAt', header: 'Created At', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
  {field: 'updatedAt', header: 'Updated At', width: '50px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true},
];

export const DataTables = {
  directoryTree,
  textFilesData,
  PltDataTables,
  importedFiles
};
