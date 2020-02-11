export interface Column {
  displayName: string;
  columnOrder: number;
  isVisible: boolean;
  isResizable: boolean;
  minWidth: number;
  maxWidth: number;
  defaultWidth: number;
  sortOrder: number;
  sortType: string;
  columnName: string;
  dataColumnType: string;
  displayType: string;
  filterCriteria: string;
}