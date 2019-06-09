import {FilterByBadgePipe} from "./filter-by-badge.pipe";
import {FilterByUserTagPipe} from "./filter-by-user-tag.pipe";
import {FilterBySystemTagPipe} from "./filter-by-system-tag.pipe";
import {FilterByPathPipe} from "./filter-by-path.pipe";
import {TableSortAndFilterPipe} from '../../core/pipes/table-sort-and-filter.pipe';
import {TextLengthPipe} from '../../core/pipes/text-length.pipe';
import {BoldPipe} from './bold.pipe';
import {ForNumberPipe} from "./for-number.pipe";
import {StatusFilterPipe} from "./status-filter.pipe";

export const PIPES= [
  FilterByBadgePipe,
  FilterByUserTagPipe,
  FilterBySystemTagPipe,
  FilterByPathPipe,
  TableSortAndFilterPipe,
  TextLengthPipe,
  BoldPipe,
  ForNumberPipe,
  StatusFilterPipe,

];

export * from '../../shared/pipes/keys.pipe'
export * from '../../core/pipes/text-length.pipe'
export * from '../../core/pipes/table-sort-and-filter.pipe';
export * from './filter-by-badge.pipe';
export * from './filter-by-path.pipe';
export * from './filter-by-system-tag.pipe';
export * from './filter-by-user-tag.pipe';
export * from './for-number.pipe';
export * from './status-filter.pipe';

