import { Column } from '../types/column.type'

export interface TableInterface {

  data: any[];
  columns: Column[];

  onColumnResize(event);
  onManageColumns(oldColumns: Column[], newColumns: Column[]);
  onFilter(column: Column, filter: string);
  onSort(column: Column, direction);
  onRowSelect(rowId: number);

}