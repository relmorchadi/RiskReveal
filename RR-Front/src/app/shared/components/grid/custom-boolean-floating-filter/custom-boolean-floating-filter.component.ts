import { Component, OnInit } from '@angular/core';
import {IFloatingFilterParams, IFloatingFilter, IAfterGuiAttachedParams, FilterChangedEvent} from '@ag-grid-community/core';
import {AgFrameworkComponent} from "ag-grid-angular";

interface CustomBooleanFloatingFilterParams extends IFloatingFilterParams {
  value: number;
}


@Component({
  selector: 'app-custom-boolean-filter',
  templateUrl: './custom-boolean-floating-filter.component.html',
  styleUrls: ['./custom-boolean-floating-filter.component.scss']
})
export class CustomBooleanFloatingFilter implements IFloatingFilter, AgFrameworkComponent<CustomBooleanFloatingFilterParams> {
  private params: CustomBooleanFloatingFilterParams;
  public currentValue: boolean;

  afterGuiAttached(params?: IAfterGuiAttachedParams): void {
  }

  agInit(params: CustomBooleanFloatingFilterParams): void {
    this.params = params;
    this.currentValue = false;
  }

  onParentModelChanged(parentModel: any, filterChangedEvent?: FilterChangedEvent): void {
    if (!parentModel) {
      this.currentValue = false;
    } else {
      // note that the filter could be anything here, but our purposes we're assuming a greater than filter only,
      // so just read off the value and use that
      this.currentValue = parentModel.filter;
    }
  }

  onFilterChange() {
    let valueToUse = this.currentValue ? this.currentValue : null;
    this.params.parentFilterInstance(function(instance: any) {
      console.log("[Instance]",instance )
      instance.onFloatingFilterChanged(
          valueToUse
      );
    });
  }

}
