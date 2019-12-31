import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'statusFilter'
})
export class StatusFilterPipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {
    let res = [];
    let status = args[0];
    let isFilter = false;
    for (let elm in status){
      if (status[elm]){
        isFilter = true;
      }
    }
    if (!isFilter) return value;
    value.forEach((thread) => {
      for (let elm in status){
        if(status[elm]){
          switch (elm) {
            case 'inProgress':
              if (thread.status == 'in Progress')
                res.push(thread)
              break;
            case 'failed':
              if (thread.status == 'failed')
                res.push(thread);
              break;
            case 'locked':
              if (thread.status == 'locked')
                res.push(thread)
              break;
            case 'new':
              if (thread.status == 'new')
                res.push(thread);
              break;
            case 'requiresRegeneration':
              if (thread.status == 'requires regeneration')
                res.push(thread)
              break;
            case 'valid':
              if (thread.status == 'Valid')
                res.push(thread);
              break;
          }
        }
      }
    })
    return res;
  }
}
