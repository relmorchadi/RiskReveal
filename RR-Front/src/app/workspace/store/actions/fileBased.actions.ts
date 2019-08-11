export class LoadFileBasedFilesAction {
  static readonly type = '[File Based] File Based Import Files Search';

  constructor(public payload: any) {
  }
}

export class LoadFileBasedFoldersAction {
  static readonly type = '[File Based] File Based Import Folders Search';

  constructor() {
  }
}

export class LoadFileContentAction {
  static readonly type = '[File Based] Load File Content';

  constructor(public payload: any) {
  }
}

export class RemoveFileSelectionAction {
  static readonly type = '[File Based] Remove File Selection';

  constructor(public payload: any) {
  }
}

export class ToggleFilesAction {
  static readonly type = '[File Based] Toggle File Selection';

  constructor(public payload: any) {
  }
}

export class AddFileForImportAction {
  static readonly type = '[File Based] Added File For Import';

  constructor(public payload: any) {
  }
}
