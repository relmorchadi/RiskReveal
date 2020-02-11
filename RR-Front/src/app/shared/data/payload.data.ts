import { Observable, of } from "rxjs";

export const Columns = of([
  {
    displayName: 'ID',
    columnOrder: 0,
    isVisible: true,
    isResizable: false,
    minWidth: 50,
    maxWidth: 70,
    defaultWidth: 50,
    sortOrder: 0,
    sortType: null,
    columnName: 'id',
    dataColumnType: 'bigint',
    displayType: 'id',
    filterCriteria: null
  },
  {
    displayName: 'User Tags',
    columnOrder: 1,
    isVisible: false,
    isResizable: true,
    minWidth: 120,
    maxWidth: 200,
    defaultWidth: 120,
    sortOrder: 0,
    sortType: null,
    columnName: 'tags',
    dataColumnType: 'ids',
    displayType: 'list',
    filterCriteria: null
  },
  {
    displayName: 'PLT Name',
    columnOrder: 1,
    isVisible: true,
    isResizable: true,
    minWidth: 120,
    maxWidth: 200,
    defaultWidth: 120,
    sortOrder: 0,
    sortType: null,
    columnName: 'pltName',
    dataColumnType: 'varchar(255)',
    displayType: 'text',
    filterCriteria: null
  },
  {
    displayName: 'Region Peril',
    columnOrder: 1,
    isVisible: true,
    isResizable: true,
    minWidth: 120,
    maxWidth: 200,
    defaultWidth: 120,
    sortOrder: 0,
    sortType: null,
    columnName: 'regionPeril',
    dataColumnType: 'varchar(255)',
    displayType: 'text',
    filterCriteria: null
  },
  {
    displayName: 'AEP500',
    columnOrder: 1,
    isVisible: true,
    isResizable: true,
    minWidth: 120,
    maxWidth: 200,
    defaultWidth: 120,
    sortOrder: 0,
    sortType: null,
    columnName: 'aep500',
    dataColumnType: 'varchar(255)',
    displayType: 'number',
    filterCriteria: null
  }
]);

export const Data = of([
  { id: 1,  pltName: 'EUWS_GR_LMF1', regionPeril: 'EUEQ', aep500: 897444987 },
  { id: 2,  pltName: 'EUWS_GR_LMF1', regionPeril: 'EUEQ', aep500: 11657601 },
  { id: 3,  pltName: 'EUWS_GR_LMF1', regionPeril: 'EUEQ', aep500: 55468 },
  { id: 4,  pltName: 'EUWS_GR_LMF1', regionPeril: 'EUEQ', aep500: 8789.265 }
]);
