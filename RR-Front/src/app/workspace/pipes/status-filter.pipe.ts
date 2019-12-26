import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'statusFilter'
})
export class StatusFilterPipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {
    console.log(value)
    console.log(args)
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
    /*const inProgress = args[0];
    const checked = args[1];
    const locked = args[2];*/
   /* for (const thread of value) {
      for (const row of thread.thread) {
        if (inProgress) {
          if (row.status === 'in progress') {
            res.push(row);
          }
        }
        if (checked) {
          if (row.status === 'checked') {
            res.push(row);
          }
        }
        if (locked) {
          if (row.status === 'locked') {
            res.push(row);
          }
        }
      }
    }*/
    return [{name: '', thread: res}];
  }
}
