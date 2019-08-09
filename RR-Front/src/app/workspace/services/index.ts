import {PltApi} from './plt.api';
import {HelperService} from "./helper.service";
import {WsApi} from './workspace.api';
import {PreviousNavigationService} from './previous-navigation.service';
import {WsProjectService} from "./ws-project.service";
import {ExcelService} from '../../shared/services/excel.service';

export const SERVICE =[
  WsApi,
  PltApi,
  HelperService,
  PreviousNavigationService,
  WsProjectService,
  ExcelService
]
