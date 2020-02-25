export interface FetchViewContextDataRequest {
  [key :string]: any;
  entity: number;
  pageNumber: number;
  pageSize: number;
  selectionList: string;
  sortSelectedFirst: boolean;
  sortSelectedAction: string;
}