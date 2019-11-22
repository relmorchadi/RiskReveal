export class ShortCut {

  public shortCutLabel: string;
  public shortCutAttribute: string;
  public mappingTable: string;

  constructor(shortCutLabel, shortCutAttribute, mappingTable){
    this.shortCutLabel = shortCutLabel;
    this.shortCutAttribute = shortCutAttribute;
    this.mappingTable = mappingTable;
  }

}
