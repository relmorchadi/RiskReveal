import { Column } from '../types/column.type'
import {Observable} from "rxjs";

export interface TableInterface {

  loading$: Observable<boolean>;

  data: any[];
  columns: Column[];

  selectedIds: any;
  selectedItem: any;
  selectAll: boolean;
  indeterminate: boolean;
  sortSelectedAction: string;

  totalRecords: number;
  totalColumnWidth: number;


  onColumnResize(event);
  onManageColumns(oldColumns: Column[], newColumns: Column[]);
  onFilter(column: Column, filter: string);
  onSort(column: Column, direction);
  onRowSelect(id: number, index: number, $event: MouseEvent);
  onVirtualScroll(event);

}