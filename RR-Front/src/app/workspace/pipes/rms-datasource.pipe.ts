import {Pipe, PipeTransform} from '@angular/core';

import * as _ from 'lodash';

@Pipe({
    name: 'rmsDatasource'
})
export class RmsDatasourceTypePipe implements PipeTransform {

    transform(datasources: Object, type?: string): any {
        let array: any[] = this.sortItems(_.toArray(datasources));
        if (type)
            return array.filter(ds => ds.type == type);
        else
            return array;
    }

    private sortItems(array) {
        if (array)
            return array.sort((a, b) => {
                const nameA = a.name.toUpperCase();
                const nameB = b.name.toUpperCase();

                let comparison = 0;
                if (nameA > nameB) {
                    comparison = 1;
                } else if (nameA < nameB) {
                    comparison = -1;
                }
                return comparison;
            })
        else return [];
    }

}
