export class HeaderStateModel {
  workspaceHeader: {
    favorite: {data: any, pageable: any},
    assigned: {data: any, pageable: any},
    recent: {data: any, pageable: any},
    pinned: {data: any, pageable: any},
    statusCount: any
  };
  jobManagerPopIn: {
    active: {
      keyword: string,
      items: any[],
      filter: { date, type }
    },
    completed: {
      keyword: string,
      items: any[],
      filter: { date, type }
    }
  };
  notificationPopIn: {
    all: {
      keyword: string,
      items: any,
      filter: {}
    },
    errors: {
      keyword: string,
      items: any,
      filter: {}
    },
    warnings: {
      keyword: string,
      items: any,
      filter: {}
    },
    informational: {
      keyword: string,
      items: any,
      filter: {}
    }
  };
}
