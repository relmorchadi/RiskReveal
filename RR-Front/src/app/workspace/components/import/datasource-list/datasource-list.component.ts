import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';

@Component({
    selector: 'datasource-list',
    templateUrl: './datasource-list.component.html',
    styleUrls: ['./datasource-list.component.scss']
})
export class DatasourceListComponent implements OnInit, OnDestroy {
    @Input('data')
    data: any;

    @Output('select')
    selectEmitter: EventEmitter<any> = new EventEmitter<any>();

    @Output('lazyLoad')
    lazyLoadEmitter: EventEmitter<any> = new EventEmitter();

    @Input('params')
    params: {
        last: boolean,
        totalElements: number,
    };

    constructor() {
    }

    ngOnInit() {
    }

    lazyLoadDataSources($event: any) {
        this.lazyLoadEmitter.emit({$event});
    }

    toggleItems(item, $event: MouseEvent, link: string) {
        this.selectEmitter.emit({
            item, $event, link
        })
    }

    ngOnDestroy(): void {
    }
}
