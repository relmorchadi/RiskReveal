export class WorkspaceModel {
  content: {
    [key: string]: any
  };
  currentTab: {
    index: number,
    wsIdentifier: string
  };
  facWs: {data: any[], sequence: any};
  savedData: {riskLink: {
    edmrdmSelection: any
  }};
  routing: string;
  loading: boolean;
  // paginationParams:any[];
}
