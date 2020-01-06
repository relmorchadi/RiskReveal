import {Pipe, PipeTransform} from '@angular/core';
declare const _;

@Pipe({
  name: 'statusFilter'
})
export class StatusFilterPipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {
    let res = [];
    let status = args[0];
    let isFilter = false;
    _.forEach(status, (v, k)=>{
      if (v){
        isFilter = true;
      }
    });
    if (!isFilter) return res;
    _.forEach(value, (thread)=>{
      _.forEach(status, (v, k)=>{
        if (v){
          if (thread.status.replace(' ','').toLowerCase() ==
            k.replace(' ','').toLowerCase()) res = [...res, thread];

        }
      })
    })
    return res;
  }
}
