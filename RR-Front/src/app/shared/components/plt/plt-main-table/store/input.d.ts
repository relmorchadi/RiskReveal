export interface Input {
  dataKey: string;
  contextMenuItems: any;
  filterInput: any;
  pltColumns: any[];
  listOfDeletedPltsCache: any[];
  listOfDeletedPltsData: any[];
  listOfPltsCache: any[];
  listOfPltsData: any[];
  selectedListOfPlts: any[];
  selectedListOfDeletedPlts: any[];
  selectAll: boolean;
  selectAllDeletedPlts: boolean;
  someItemsAreSelected: boolean;
  someDeletedItemsAreSelected: boolean;
  openedPlt: string;
  showDeleted: boolean;
  filterData: any;
  filters: any;
  sortData: any;
  status: any;
  scrollHeight: string | number;
  showApplicable?: boolean;
}
