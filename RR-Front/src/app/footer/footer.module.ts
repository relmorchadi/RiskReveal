import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponentComponent } from './footer-component/footer-component.component';

@NgModule({
  declarations: [FooterComponentComponent],
  exports: [
    FooterComponentComponent
  ],
  imports: [
    CommonModule
  ]
})
export class FooterModule { }
