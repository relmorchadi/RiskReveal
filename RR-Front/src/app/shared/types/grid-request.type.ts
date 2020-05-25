export interface GridRequest<T> {
  startRow :number;
  endRow: number;
  size: number;

  // // row group columns
  // List<ColumnVO> rowGroupCols;
  //
  // // value columns
  // List<ColumnVO> valueCols;
  //
  // // pivot columns
  // List<ColumnVO> pivotCols;
  //
  // // true if pivot mode is one, otherwise false
  // boolean pivotMode;
  //
  // // what groups the user is viewing
  // List<String> groupKeys;

  // if filtering, what the filter model is
  filterModel: T;

  // // if sorting, what the sort model is
  // List<SortModel> sortModel;
}