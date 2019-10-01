export interface Input {
  _tagModalVisible: boolean;
  wsId: string;
  uwYear: number;
  projects: any[];
  showDeleted: boolean;
  filterData: any;
  filters: {
    systemTag: any,
    userTag: any[]
  };
  deletedPltsLength: number;
  userTags: any[];
  selectedListOfPlts: any[];
  systemTagsCount: any;
  wsHeaderSelected: boolean;
  pathTab: boolean;
  assignedTags: any[];
  assignedTagsCache: any[];
  toAssign: any[];
  toRemove: any[];
  usedInWs: any[];
  allTags: any[];
  suggested: any[];
  selectedTags: any;
  operation: string;
}
