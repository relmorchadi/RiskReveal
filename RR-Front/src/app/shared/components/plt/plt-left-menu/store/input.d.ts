export interface Input {
  visible: boolean;
  mode: string;
  selectedTab: {
    index: number,
    title: string,
  };
  tabs: { [key: string]: any };
  pltDetail: any;
  basket: any[];
}
