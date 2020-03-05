export class WorkspaceModel {
  content: {
    [key: string]: any
  };
  currentTab: {
    index: number,
    openedTabs: any,
    wsIdentifier: string
  };
  constants: {
    basis: any[],
    adjustmentTypes: any[],
    status: any[]
  };
  savedData: {riskLink: {
    edmrdmSelection: any
  }};
  routing: string;
  loading: boolean;
  // paginationParams:any[];
}
