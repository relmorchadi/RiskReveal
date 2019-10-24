import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'statusFilter'
})
export class StatusFilterPipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {

    let res = [];
    const inProgress = args[0];
    const checked = args[1];
    const locked = args[2];
    for (const thread of value) {
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
    }
    return [{name: '', thread: res}];
  }
}
