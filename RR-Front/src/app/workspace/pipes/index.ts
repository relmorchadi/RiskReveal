import {FilterByBadgePipe} from './filter-by-badge.pipe';
import {FilterByUserTagPipe} from './filter-by-user-tag.pipe';
import {FilterBySystemTagPipe} from './filter-by-system-tag.pipe';
import {FilterByPathPipe} from './filter-by-path.pipe';
import {ForNumberPipe} from './for-number.pipe';
import {ParseIdPipe} from './parse-id.pipe';
import {StatusFilterPipe} from './status-filter.pipe';

export const PIPES = [
  FilterByBadgePipe,
  FilterByUserTagPipe,
  FilterBySystemTagPipe,
  FilterByPathPipe,
  ForNumberPipe,
  ParseIdPipe,
  StatusFilterPipe,
];

export * from './filter-by-badge.pipe';
export * from './filter-by-path.pipe';
export * from './filter-by-system-tag.pipe';
export * from './filter-by-user-tag.pipe';
export * from './for-number.pipe';
export * from './parse-id.pipe';
export * from './status-filter.pipe';

