
export class WorkspaceModel {
  content : {
    [key: string] : any
  };
  currentTab: {
    index: number,
    wsIdentifier: string
  };
  favorite: any[];
  pinned: any[];
  routing: string;
  loading: boolean;
  // paginationParams:any[];
}
