import {PltApi} from './api/plt.api';
import {HelperService} from './helper.service';
import {WsApi} from './api/workspace.api';
import {TagsApi} from './api/tags.api';
import {ScopeOfCompletenessApi} from './api/scopeOfCompleteness.api'
import {PreviousNavigationService} from './previous-navigation.service';
import {ExcelService} from '../../shared/services/excel.service';
import {ColumnsFormatterService} from "../../shared/services/columnsFormatter.service";
import {ConfirmationService} from "primeng/api";

export const SERVICE = [
    ConfirmationService,
    WsApi,
    PltApi,
    TagsApi,
    ScopeOfCompletenessApi,
    HelperService,
    PreviousNavigationService
];
