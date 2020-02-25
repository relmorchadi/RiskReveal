export interface Column {
  viewContextId: number;
  viewContextColumnId: number;
  userCode: string;
  displayName: string;
  columnOrder: number;
  isVisible: boolean;
  isResizable: boolean;
  minWidth: number;
  maxWidth: number;
  width: number;
  sortOrder: number;
  sortType: string;
  columnName: string;
  dataColumnType: string;
  dataDisplayType: string;
  filterCriteria: string;
}