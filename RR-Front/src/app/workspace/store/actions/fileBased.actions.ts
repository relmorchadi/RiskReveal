export class LoadFileBasedFilesAction {
  static readonly type = '[File Based] File Based Import Files Search';
  constructor(public payload: any) {}
}

export class LoadFileBasedFoldersAction {
  static readonly type = '[File Based] File Based Import Folders Search';
  constructor(public payload: any) {}
}

export class LoadFileContentAction {
  static readonly type = '[File Based] Load File Content';
  constructor(public payload: any) {}
}

export class RemoveFileSelectionAction {
  static readonly type = '[File Based] Remove File Selection';
  constructor(public payload: any) {}
}
