import {BehaviorSubject, Observable} from 'rxjs';
import { Column } from '../types/column.type'
import {FetchViewContextDataRequest} from "../types/fetchviewcontextdatarequest.type";

export interface TableHandlerInterface {

  _data: any[];
  data$: BehaviorSubject<any[]>;

  _selectedIds: any[];
  selectedIds$: BehaviorSubject<any[]>;

  _sortSelectedAction: string;
  sortSelectedAction$: BehaviorSubject<string>;

  _sortSelectedFirst: boolean;
  sortSelectedFirst$: BehaviorSubject<boolean>;

  _selectAll: boolean;
  selectAll$: BehaviorSubject<boolean>;

  loading$: BehaviorSubject<boolean>;

  totalRecords: number;
  totalRecords$: BehaviorSubject<number>;

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
  onFilter(index: number, filter: string);
  onResetFilter();
  onSort(index: number);
  onResetSort();
  onRowSelect(i: number);
  onCheckAll();
  onContainerResize(newWidth: number);
  onVirtualScroll(event);

  init(config: any): void;
  initApi(url: string): void;
  initTable(params: any): void;
  reloadTable(request: FetchViewContextDataRequest): Observable<any>;

}
