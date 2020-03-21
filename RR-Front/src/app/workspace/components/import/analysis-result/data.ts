export default {
  FAC: {
    scrollableCols: [
      // {field: '', header: '', width: '80px', type: '', filtered: false, visible: false, highlight: false, edit: false},
      {
        field: 'divisions',
        header: 'Divisions',
        width: '100px',
        type: 'division',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: false
      },
      {
        field: 'rpCode',
        header: 'Region Peril',
        width: '80px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'analysisCurrency',
        header: 'Source Currency',
        width: '90px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      }, /*
      {
        field: 'targetCurrency',
        header: 'Target Currency',
        width: '80px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: true
      }, */
      {
        field: 'financialPerspective',
        header: 'ELT FIN PERSP',
        width: '80px',
        type: 'multiple',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: false
      },
      {
        field: 'occurrenceBasis',
        header: 'Occurrence Basis',
        width: '100px',
        type: 'Ob',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: false
      },/**
      {
        field: 'targetRap',
        header: 'Target RAP',
        width: '180px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: true
      }, */
      {
        field: 'targetRaps',
        header: 'PEQT',
        width: '80px',
        type: 'Peqt',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false,
        canOverride: false
      },
      {
        field: 'unitMultiplier',
        header: 'Unit Multiplier',
        width: '80px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'proportion',
        header: 'Proportion',
        width: '70px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'rdmName',
        header: 'RDM',
        width: '210px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'importId',
        header: 'Import ID',
        width: '80px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'dateImport',
        header: 'Date Import',
        width: '70px',
        type: 'date',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'user',
        header: 'User',
        width: '70px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'publishAcc',
        header: 'Publish For Accumulation',
        width: '30px',
        type: 'check',
        icon: 'icon-focus-add',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'publishPri',
        header: 'Publish For Pricing',
        width: '30px',
        type: 'check',
        icon: 'icon-note',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      /*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
    ],
    fixedCols: [
      {
        field: 'selected',
        header: '',
        width: '25px',
        type: 'selection',
        sorting: '',
        filtered: false,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'scan',
        header: '',
        width: '25px',
        type: 'scan',
        sorting: '',
        filtered: false,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'status',
        header: 'Status',
        width: '40px',
        type: 'progress',
        sorting: '',
        filtered: false,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'rlId',
        header: 'ID',
        width: '50px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'analysisName',
        header: 'Name',
        width: '170px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'analysisDescription',
        header: 'Description',
        width: '250px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
    ]
  },
  Treaty: {
    scrollableCols: [
      {field: '', header: '', width: '80px', type: '', filtered: false, visible: false, highlight: false, edit: false},
      {
        field: 'rpCode',
        header: 'Region Peril',
        width: '80px',
        type: 'Rp',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: false
      },
      {
        field: 'analysisCurrency',
        header: 'Source Currency',
        width: '90px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'targetCurrency',
        header: 'Target Currency',
        width: '80px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: true
      },
      {
        field: 'financialPerspective',
        header: 'ELT FIN PERSP',
        width: '80px',
        type: 'multiple',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: false
      },
      {
        field: 'occurrenceBasis',
        header: 'Occurrence Basis',
        width: '100px',
        type: 'Ob',
        sorting: '',
        filtered: true,
        highlight: true,
        visible: true,
        edit: false
      },
      // {
      //   field: 'targetRap',
      //   header: 'Target RAP',
      //   width: '180px',
      //   type: 'text',
      //   sorting: '',
      //   filtered: true,
      //   highlight: false,
      //   visible: true,
      //   edit: true
      // },
      {
        field: 'targetRaps',
        header: 'PEQT',
        width: '80px',
        type: 'Peqt',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false,
        canOverride: true
      },
      {
        field: 'unitMultiplier',
        header: 'Unit Multiplier',
        width: '80px',
        type: 'number',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'proportion',
        header: 'Proportion',
        width: '70px',
        type: 'percentage',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'rdmName',
        header: 'RDM',
        width: '210px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'importId',
        header: 'Import ID',
        width: '80px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'dateImport',
        header: 'Date Import',
        width: '70px',
        type: 'date',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'user',
        header: 'User',
        width: '70px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'publishAcc',
        header: 'Publish For Accumulation',
        width: '30px',
        type: 'check',
        icon: 'icon-focus-add',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'publishPri',
        header: 'Publish For Pricing',
        width: '30px',
        type: 'check',
        icon: 'icon-note',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      /*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
    ],
    fixedCols: [
      {
        field: 'selected',
        header: '',
        width: '25px',
        type: 'selection',
        sorting: '',
        filtered: false,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'scan',
        header: '',
        width: '25px',
        type: 'scan',
        sorting: '',
        filtered: false,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'status',
        header: 'Status',
        width: '40px',
        type: 'progress',
        sorting: '',
        filtered: false,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'rlId',
        header: 'ID',
        width: '40px',
        type: 'text',
        sorting: '',
        filtered: false,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'analysisName',
        header: 'Name',
        width: '170px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
      {
        field: 'analysisDescription',
        header: 'Description',
        width: '200px',
        type: 'text',
        sorting: '',
        filtered: true,
        highlight: false,
        visible: true,
        edit: false
      },
    ]
  }
}
