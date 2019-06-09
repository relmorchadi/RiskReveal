
export class WorkspaceModel {
  content : {
    [key: string] : any
  };
  currentTab: {
    index: number,
    wsIdentifier: string
  };
  routing: string;
  loading: boolean;
}
