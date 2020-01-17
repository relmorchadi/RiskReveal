import {HighlightDirective} from '../highlight.directive';
import {KeysPipe} from './keys.pipe';
import {ShowLastPipe} from './show-last.pipe';
import {InputSearchPipe} from './input-search.pipe';
import {ToArrayPipe} from './to-array.pipe';
import {LogPipe} from './log.pipe';
import {BoldPipe} from './bold.pipe';
import {SystemTagFilterPipe} from './system-tag-filter.pipe';
import {TableSortAndFilterPipe} from './table-sort-and-filter.pipe';
import {TextLengthPipe} from './text-length.pipe';
import {TrimFormatPipe} from './trim-format.pipe';
import {ReFormatPIDPipe} from './re-format-pid.pipe';
import {PickKeysPipe} from './pull-keys.pipe';
import {CalibratePipe} from "./calibrate.pipe";
import {FilterByStatusPipe} from "./filter-by-status.pipe";
import {FalselyFilterPipe} from "./falsely-filter.pipe";
import {TrimSecondaryFormatPipe} from './trim-secondary-format.pipe';
import {StartCasePipe} from "./start-case.pipe";

export const PIPES = [
  CalibratePipe,
  PickKeysPipe,
  ReFormatPIDPipe,
  HighlightDirective,
  TableSortAndFilterPipe,
  TextLengthPipe,
  TrimFormatPipe,
  TrimSecondaryFormatPipe,
  KeysPipe,
  ShowLastPipe,
  InputSearchPipe,
  ToArrayPipe,
  LogPipe,
  BoldPipe,
  SystemTagFilterPipe,
  FilterByStatusPipe,
  FalselyFilterPipe,
  StartCasePipe
];

export * from './trim-secondary-format.pipe';
export * from './trim-format.pipe';
export * from './input-search.pipe';
export * from './keys.pipe';
export * from './show-last.pipe';
export * from './filter-by-status.pipe';

