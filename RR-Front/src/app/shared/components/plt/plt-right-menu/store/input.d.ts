export interface Input {
  visible: boolean;
  mode: string;
  pltHeaderId: string;
  selectedTab: {
    index: number,
    title: string,
  };
  tabs: any[];
  basket: any[];
}
