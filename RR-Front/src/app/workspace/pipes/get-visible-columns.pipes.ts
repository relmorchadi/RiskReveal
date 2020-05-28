import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: 'getVisibleColumns'
})
export class GetVisibleColumnsPipes implements PipeTransform {

    transform(cols: Array<any>, visibility: boolean): any {
        console.log(cols);
        console.log(cols ? cols.filter(c => c.visible == visibility) : cols);
        return cols ? cols.filter(c => c.visible == visibility) : cols;
    }

}
