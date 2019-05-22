import * as _ from 'lodash'
export class pltMainModel {
  data: any;
  loading: boolean;
  filters: any;
}

export const getPLTData = state => state.data;
export const getAttrfromState = (path,state) => _.get(state, `${path}`)
