export interface Input {
  tagContextMenu: any;
  _tagModalVisible: boolean;
  _modalSelect: [];
  tagForMenu: any,
  _editingTag: boolean;
  wsId: string;
  uwYear: string;
  projects: any[];
  showDeleted: boolean;
  filterData: any;
  filters: {
    systemTag: any,
    userTag: any[]
  };
  addTagModalIndex: number;
  fromPlts: boolean;
  deletedPltsLength: number;
  userTags: any[];
  selectedListOfPlts: any[];
  systemTagsCount: any;
  wsHeaderSelected: boolean;
  pathTab: boolean;
  selectedItemForMenu: any;
  previouslyUsed: any[];
  usedInWs: any[];
  userFavorite: any[];
  subsetsAssigned: any[];
  allAssigned: any[];
}
