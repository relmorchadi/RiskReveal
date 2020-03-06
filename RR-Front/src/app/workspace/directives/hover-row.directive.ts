/*
 * Date : 28/2/2020.
 * Author : Reda El Morchadi
 */

import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
    selector: '[hoverRow]'
})
export class HoverRow {

    constructor(private el: ElementRef) { }

    @Input('hoverRow') highlightColor: string;

    @HostListener('mouseenter') onMouseEnter() {
        this.highlight(this.highlightColor || 'red');
    }

    @HostListener('mouseleave') onMouseLeave() {
        this.highlight(null);
    }

    private highlight(color: string) {
        this.el.nativeElement.style.backgroundColor = color;
    }
}