import {HighlightDirective} from '../highlight.directive';
import {KeysPipe} from './keys.pipe';
import {ShowLastPipe} from './show-last.pipe';
import {InputSearchPipe} from './input-search.pipe';
import {ToArrayPipe} from './to-array.pipe';
import {LogPipe} from './log.pipe';
import {BoldPipe} from './bold.pipe';
import {SystemTagFilterPipe} from './system-tag-filter.pipe';
import {TableSortAndFilterPipe} from "./table-sort-and-filter.pipe";
import {TextLengthPipe} from "./text-length.pipe";
import {ReFormatPIDPipe} from './re-format-pid.pipe';
import {PickKeysPipe} from './pull-keys.pipe';
import {CalibratePipe} from "./calibrate.pipe";
import {FilterByStatusPipe} from "./filter-by-status.pipe";


export const PIPES = [
  CalibratePipe,
  PickKeysPipe,
  ReFormatPIDPipe,
  HighlightDirective,
  TableSortAndFilterPipe,
  TextLengthPipe,
  KeysPipe,
  ShowLastPipe,
  InputSearchPipe,
  ToArrayPipe,
  LogPipe,
  BoldPipe,
  SystemTagFilterPipe,
  FilterByStatusPipe
]

export * from './input-search.pipe';
export * from './keys.pipe';
export * from './show-last.pipe';
export * from "./filter-by-status.pipe";

