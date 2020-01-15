import { Pipe, PipeTransform } from '@angular/core';
import {GetMetricPipe} from "./get-metric.pipe";

@Pipe({
  name: 'getDelta',
  pure: true
})
export class GetDeltaPipe implements PipeTransform {

  constructor(public getEpMetric: GetMetricPipe) {}

  transform(epMetrics: any, threadId, pureId, metric, curveType, isDeltaByAmount): any {

    const threadMetric = this.getEpMetric.transform(epMetrics, curveType, threadId, metric);
    const pureMetric = this.getEpMetric.transform(epMetrics, curveType, pureId, metric);

    const diff = threadMetric - pureMetric;

    return !isDeltaByAmount ?  ( diff / (pureMetric || 1) * 100 ) : diff;
  }

}
