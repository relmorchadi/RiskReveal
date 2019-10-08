export interface Input {
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
}
