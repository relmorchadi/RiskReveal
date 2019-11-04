import {ShortCut} from "./shortcut.model";

export class Badge {

  public shortCut: ShortCut;
  public keyword: string;
  public expression: string;

  constructor(expression,shortCut) {
    this.expression = expression;
    this.shortCut = shortCut;

    this.updateKeyWord();
  }

  public updateKeyWord() {
    this.keyword = this.expression ?
      this.expression.substring( (this.shortCut.shortCutLabel + ":").length ) :
      ""
  }

}
