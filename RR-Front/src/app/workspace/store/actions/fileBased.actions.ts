export class LoadFileBasedFilesAction {
  static readonly type = '[File Based] File Based Import Files Search';
  constructor(public payload: any) {}
}

export class LoadFileBasedFoldersAction {
  static readonly type = '[File Based] File Based Import Folders Search';
  constructor(public payload: string) {}
}

export class LoadFileContentAction {
  static readonly type = '[File Based] Load File Content';
  constructor(public payload: any) {}
}

export class RemoveFileFromImportAction {
  static readonly type = '[File Based] Remove File From Import';
  constructor(public payload: any) {}
}

export class ToggleFilesAction {
  static readonly type = '[File Based] Toggle File Selection';
  constructor(public payload: any) {}
}

export class TogglePltsAction {
  static readonly type = '[File Based] Toggle Plts Selection';
  constructor(public payload: any) {}
}

export class AddFileForImportAction {
  static readonly type = '[File Based] Added File For Import';
  constructor(public payload: any) {}
}

export class LaunchFileBasedImportAction {
    static readonly type = '[File Based] Launched File Based Import';
    constructor() {}
}