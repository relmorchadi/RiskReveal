import {HighlightDirective} from '../highlight.directive';
import {KeysPipe} from './keys.pipe';
import {ShowLastPipe} from './show-last.pipe';
import {InputSearchPipe} from './input-search.pipe';
import {ToArrayPipe} from './to-array.pipe';
import {LogPipe} from './log.pipe';
import {BoldPipe} from './bold.pipe';
import {SystemTagFilterPipe} from './system-tag-filter.pipe';
import {ReFormatPIDPipe} from './re-format-pid.pipe';


export const PIPES = [ReFormatPIDPipe,HighlightDirective, KeysPipe, ShowLastPipe, InputSearchPipe, ToArrayPipe, LogPipe, BoldPipe, SystemTagFilterPipe]

export * from './input-search.pipe';
export * from './keys.pipe';
export * from './show-last.pipe';

