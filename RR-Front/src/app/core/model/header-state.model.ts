

export class HeaderStateModel{
  workspacePopIn: {
    recent: {
      keyword: string,
      items: {selected, wsId, uwYear, workspaceName, programName, cedantName}[],
      pagination: number
    },
    favorite: {
      keyword: string,
      items: any[],
      pagination: number
    },
    assigned: {
      keyword: string,
      items: any[],
      pagination: number
    },
    pinned: {
      keyword: string,
      items: any[],
      pagination: number
    }
  };
  jobManagerPopIn: {
    active: {
      keyword: string,
      items: any[],
      filter: {date, type}
    },
    completed: {
      keyword: string,
      items: any[],
      filter: {date, type}
    }
  };
  notificationPopIn: {
    all:{
      keyword:string,
      items: any,
      filter: {}
    },
    errors:{
      keyword:string,
      items: any,
      filter: {}
    },
    warnings:{
      keyword:string,
      items: any,
      filter: {}
    },
    informational:{
      keyword:string,
      items: any,
      filter: {}
    }
  }
}
