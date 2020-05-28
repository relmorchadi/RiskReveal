import {FilterByBadgePipe} from './filter-by-badge.pipe';
import {FilterByUserTagPipe} from './filter-by-user-tag.pipe';
import {FilterBySystemTagPipe} from './filter-by-system-tag.pipe';
import {ForNumberPipe} from './for-number.pipe';
import {ParseIdPipe} from './parse-id.pipe';
import {StatusFilterPipe} from './status-filter.pipe';
import {RmsDatasourceTypePipe} from './rms-datasource.pipe';
import {AnalysisByRdmPipe} from "./analysis-by-rdm.pipe";
import {GetMetricPipe} from "./get-metric.pipe";
import {FrozenColsLengthPipe} from "./frozen-cols-length.pipe";
import {GetAdjustmentPipe} from "./get-adjustment.pipe";
import {GetFactorPipe} from "./get-factor.pipe";
import {GetDescriptionPipe} from "./get-description.pipe";
import {ConcatListPipe} from "./concat-list.pipe";
import {GetDeltaPipe} from "./get-delta.pipe";
import {FilterRPPipe} from "./filter-rp.pipe";
import {FilterGroupedPltsPipe} from "./filter-grouped-plts.pipe";
import {SortGroupedPltsPipe} from "./sort-grouped-plts.pipe";
import {IsEventAdjustmentPipe} from "./is-event-adjustment.pipe";
import {GetEventAdjustmentParamsPipe} from "./get-event-adjustment-params.pipe";
import {FrozenColumnsFilterPipe} from "./frozen-columns-filter.pipe";
import {CloneDataColumnManageFilterPipe} from "./clone-data-column-manage-filter.pipe";
import {CalibrationSortAndFilterPipe} from "./calibration-sort-and-filter.pipe";
import {GetVisibleColumnsPipes} from "./get-visible-columns.pipes";

export const PIPES = [
  GetVisibleColumnsPipes,
  CalibrationSortAndFilterPipe,
  CloneDataColumnManageFilterPipe,
  FilterByBadgePipe,
  FrozenColumnsFilterPipe,
  FilterByUserTagPipe,
  FilterBySystemTagPipe,
  ForNumberPipe,
  ParseIdPipe,
  StatusFilterPipe,
  AnalysisByRdmPipe,
  RmsDatasourceTypePipe,
  GetMetricPipe,
  FrozenColsLengthPipe,
  GetAdjustmentPipe,
  GetFactorPipe,
  GetDescriptionPipe,
  ConcatListPipe,
  GetDeltaPipe,
  FilterRPPipe,
  FilterGroupedPltsPipe,
  SortGroupedPltsPipe,
  IsEventAdjustmentPipe,
  GetEventAdjustmentParamsPipe

];

export * from './filter-by-badge.pipe';
export * from '../../shared/pipes/filter-by-path.pipe';
export * from './filter-by-system-tag.pipe';
export * from './filter-by-user-tag.pipe';
export * from './for-number.pipe';
export * from './parse-id.pipe';
export * from './status-filter.pipe';
export * from './rms-datasource.pipe';
export * from './analysis-by-rdm.pipe';
