export interface FetchViewContextDataRequest {
  workspaceContextCode: string;
  workspaceUwYear: number;
  entity: number;
  pageNumber: number;
  pageSize: number;
  selectionList: string;
  sortSelectedFirst: boolean;
  sortSelectedAction: string;
}