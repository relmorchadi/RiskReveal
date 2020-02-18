import { Column } from '../types/column.type'
import {Observable} from "rxjs";

export interface TableInterface {

  loading$: Observable<boolean>;

  data: any[];
  columns: Column[];

  selectedIds: number [] | string [];

  onColumnResize(event);
  onManageColumns(oldColumns: Column[], newColumns: Column[]);
  onFilter(column: Column, filter: string);
  onSort(column: Column, direction);
  onRowSelect(i: number);
  onVirtualScroll(event);

}