import {HighlightDirective} from '../highlight.directive';
import {KeysPipe} from './keys.pipe';
import {ShowLastPipe} from './show-last.pipe';
import {InputSearchPipe} from './input-search.pipe';
import {ToArrayPipe} from './to-array.pipe';
import {LogPipe} from './log.pipe';
import {BoldPipe} from './bold.pipe';
import {SystemTagFilterPipe} from './system-tag-filter.pipe';


export const PIPES = [HighlightDirective, KeysPipe, ShowLastPipe, InputSearchPipe, ToArrayPipe, LogPipe, BoldPipe, SystemTagFilterPipe]
