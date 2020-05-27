export class ErrorValue {
  errorDescription: string;
  rowIndex: number;
  value: any;

  constructor(d, r, v) {
    this.errorDescription= d;
    this.rowIndex = r;
    this.value = v;
  }
}