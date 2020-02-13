import {BehaviorSubject, Observable} from 'rxjs';
import { Column } from '../types/column.type'
import {FetchViewContextDataRequest} from "../types/fetchviewcontextdatarequest.type";

export interface TableHandlerInterface {

  _data: any[];
  data$: BehaviorSubject<any[]>;

  _columns: Column[];
  columns$: BehaviorSubject<Column[]>;

  _visibleColumns: Column[];
  visibleColumns$: BehaviorSubject<Column[]>;

  _availableColumns: Column[];
  availableColumns$: BehaviorSubject<Column[]>;

  _totalColumnWidth: number;
  totalColumnWidth$: BehaviorSubject<number>;

  onColumnResize(event);
  onManageColumns(columns: Column[]);
  onFilter(column: Column, filter: string);
  onSort(column: Column, direction);
  onRowSelect(rowId: number);
  onContainerResize(newWidth: number);

  init(config: any): void;
  initApi(url: string): void;
  initTable(request: FetchViewContextDataRequest): void;

}
