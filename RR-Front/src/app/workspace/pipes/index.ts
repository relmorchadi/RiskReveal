import {FilterByBadgePipe} from "./filter-by-badge.pipe";
import {FilterByUserTagPipe} from "./filter-by-user-tag.pipe";
import {FilterBySystemTagPipe} from "./filter-by-system-tag.pipe";
import {FilterByPathPipe} from "./filter-by-path.pipe";
import {TableSortAndFilterPipe} from '../../core/pipes/table-sort-and-filter.pipe';
import {TextLengthPipe} from '../../core/pipes/text-length.pipe';
import {BoldPipe} from './bold.pipe';

export const PIPES= [
  FilterByBadgePipe,
  FilterByUserTagPipe,
  FilterBySystemTagPipe,
  FilterByPathPipe,
  TableSortAndFilterPipe,
  TextLengthPipe,
  BoldPipe
];

export * from '../../core/pipes/text-length.pipe'
export * from '../../core/pipes/table-sort-and-filter.pipe';
export * from './filter-by-badge.pipe';
export * from './filter-by-path.pipe';
export * from './filter-by-system-tag.pipe';
export * from './filter-by-user-tag.pipe';
export * from './for-number.pipe';
