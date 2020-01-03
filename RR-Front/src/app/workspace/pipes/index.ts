import {FilterByBadgePipe} from './filter-by-badge.pipe';
import {FilterByUserTagPipe} from './filter-by-user-tag.pipe';
import {FilterBySystemTagPipe} from './filter-by-system-tag.pipe';
import {FilterByPathPipe} from './filter-by-path.pipe';
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
import {FinancialUnitPipe} from "./financial-unit.pipe";
import {GetDeltaPipe} from "./get-delta.pipe";

export const PIPES = [
  FilterByBadgePipe,
  FilterByUserTagPipe,
  FilterBySystemTagPipe,
  FilterByPathPipe,
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
  FinancialUnitPipe,
  GetDeltaPipe
];

export * from './filter-by-badge.pipe';
export * from './filter-by-path.pipe';
export * from './filter-by-system-tag.pipe';
export * from './filter-by-user-tag.pipe';
export * from './for-number.pipe';
export * from './parse-id.pipe';
export * from './status-filter.pipe';
export * from './rms-datasource.pipe';
export * from './analysis-by-rdm.pipe';
