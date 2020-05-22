import {HighlightDirective} from './highlight.directive';
import {ScrollerDirective} from "./scroller.directive";
import {ScrollIndicatorDirective} from "./scroll-indicator.directive";
import {SyncScrollDirective} from "./sync-scroll.directive";
import {TableRowsDirective} from "./table-rows.directive";
import {RrNumberDirective} from "./rr-number.directive";
import {SearchInputDirective} from "./search-input.directive";

export const DIRECTIVES = [SearchInputDirective, HighlightDirective, ScrollerDirective, ScrollIndicatorDirective, SyncScrollDirective, TableRowsDirective, RrNumberDirective];

export * from './highlight.directive';
export * from './scroller.directive';
