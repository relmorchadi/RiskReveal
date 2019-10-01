export class WorkspaceModel {
  content: {
    [key: string]: any
  };
  currentTab: {
    index: number,
    wsIdentifier: string
  };
  facWs: {data: any[], sequence: any};
  favorite: any[];
  pinned: any[];
  routing: string;
  loading: boolean;
  // paginationParams:any[];
}
