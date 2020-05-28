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
import {FinancialUnitPipe} from "../../workspace/pipes/financial-unit.pipe";
import {ExchangeRatePipe} from "./exchange-rate.pipe";
import {RRDatePipe} from "./rr-date.pipe";
import {RrNumberPipe} from "./rr-number.pipe";
import {HasStatusPipe} from "./has-status.pipe";
import {TableFilterPipe} from "./table-filter.pipe";
import {GetIdentifiersPipe} from "./get-identifiers.pipe";
import {FilterByPathPipe} from "./filter-by-path.pipe";
import {IncludesPipe} from "./includes.pipe";
import {BoldSpanPipe} from "./bold-span.pipe";

export const PIPES = [
    BoldSpanPipe,
    RRDatePipe,
    CalibratePipe,
    PickKeysPipe,
    ReFormatPIDPipe,
    HighlightDirective,
    TableSortAndFilterPipe,
    TextLengthPipe,
    TrimFormatPipe,
    FilterByPathPipe,
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
    StartCasePipe,
    FinancialUnitPipe,
    ExchangeRatePipe,
    RrNumberPipe,
    HasStatusPipe,
    TableFilterPipe,
    GetIdentifiersPipe,
    IncludesPipe
];

export * from './trim-secondary-format.pipe';
export * from './rr-date.pipe';
export * from './trim-format.pipe';
export * from './input-search.pipe';
export * from './keys.pipe';
export * from './show-last.pipe';
export * from './filter-by-status.pipe';

