export class WorkspaceModel {
  content: {
    [key: string]: any
  };
  currentTab: {
    index: number,
    wsIdentifier: string
  };
  facWs: any[];
  favorite: any[];
  pinned: any[];
  routing: string;
  loading: boolean;
  // paginationParams:any[];
}
