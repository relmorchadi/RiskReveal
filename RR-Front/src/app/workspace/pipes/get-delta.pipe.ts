import { Pipe, PipeTransform } from '@angular/core';
import {GetMetricPipe} from "./get-metric.pipe";

@Pipe({
  name: 'getDelta',
  pure: true
})
export class GetDeltaPipe implements PipeTransform {

  constructor(public getEpMetric: GetMetricPipe) {}

  transform(epMetrics: any, threadId, pureId, metric, curveType): any {

    const threadMteric = this.getEpMetric.transform(epMetrics, curveType, threadId, metric);
    const pureMteric = this.getEpMetric.transform(epMetrics, curveType, pureId, metric);

    return ( threadMteric - pureMteric ) / (pureMteric || 1) * 100;
  }

}
