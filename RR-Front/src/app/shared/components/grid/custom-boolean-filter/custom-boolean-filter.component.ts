import { Component } from '@angular/core';
import {AgFilterComponent} from "@ag-grid-community/angular";
import {IAfterGuiAttachedParams, IFilterParams, IDoesFilterPassParams, RowNode} from '@ag-grid-community/core';

@Component({
  selector: 'app-custom-boolean-filter',
  templateUrl: './custom-boolean-filter.component.html',
  styleUrls: ['./custom-boolean-filter.component.scss']
})
export class CustomBooleanFilter implements AgFilterComponent {
  private params: IFilterParams;
  private valueGetter: (rowNode: RowNode) => any;
  public currentValue: boolean;

  afterGuiAttached(params?: IAfterGuiAttachedParams): void {
  }

  agInit(params: IFilterParams): void {
    this.params = params;
    this.valueGetter = params.valueGetter;
  }

  doesFilterPass(params: IDoesFilterPassParams): boolean {
    return this.currentValue;
  }

  getFrameworkComponentInstance(): any {
  }

  getModel(): any {
    return this.currentValue ? {
      filterType: "boolean",
      type: "equalsBoolean",
      filter: this.currentValue
    } : null;
  }

  getModelAsString(model: any): string {
    return "";
  }

  isFilterActive(): boolean {
    return this.currentValue && this.currentValue != null;
  }

  onAnyFilterChanged(): void {
  }

  onNewRowsLoaded(): void {
  }

  setModel(model: any): void {
    this.currentValue = model ? model.value : false;
  }

  onFilterChange(newValue) {
    if (this.currentValue !== newValue) {
      this.currentValue = newValue;
      this.params.filterChangedCallback();
    }
  }

  onFloatingFilterChanged(newValue) {
    if (this.currentValue !== newValue) {
      this.currentValue = newValue;
      this.params.filterChangedCallback();
    }
  }

}
