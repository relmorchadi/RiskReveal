export interface Input {
  wsId: string;
  uwYear: number;
  projects: any[];
  selectedProject?: any;
  showDeleted: boolean;
  deletedPltsLength?: number;
  filterData: any;
  filters: {
    systemTag: any,
    userTag: any[]
  };
  userTags: any[];
  selectedListOfPlts: any[];
  systemTagsCount: any;
  wsHeaderSelected: boolean;
  pathTab: boolean,
  isTagsTab: boolean
}
