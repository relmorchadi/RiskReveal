export class ShortCut {

  public shortCutLabel: string;
  public shortCutAttribute: string;
  public mappingTable: string;
  public type: string;

  constructor(shortCutLabel, shortCutAttribute, mappingTable, type){
    this.shortCutLabel = shortCutLabel;
    this.shortCutAttribute = shortCutAttribute;
    this.mappingTable = mappingTable;
    this.type = type;
  }

}
