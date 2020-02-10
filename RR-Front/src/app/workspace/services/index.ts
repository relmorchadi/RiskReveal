import {PltApi} from './api/plt.api';
import {HelperService} from './helper.service';
import {WsApi} from './api/workspace.api';
import {PreviousNavigationService} from './previous-navigation.service';
import {ExcelService} from '../../shared/services/excel.service';
import {TagsApi} from './api/tags.api';

export const SERVICE = [
  WsApi,
  PltApi,
  TagsApi,
  HelperService,
  PreviousNavigationService,
  ExcelService
];
