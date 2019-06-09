import {Action, createSelector, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import * as _ from 'lodash';
import {WorkspaceModel} from "../../model";
import * as fromWS from '../actions'
import {WsApi} from '../../services/workspace.api';
import {catchError, mergeMap} from 'rxjs/operators';
import {Navigate} from '@ngxs/router-plugin';

const initiaState: WorkspaceModel = {
  content : {},
  currentTab: {
    index: 0,
    wsIdentifier: null,
  },
  routing: '',
  loading: false,
};

@State<WorkspaceModel>({
  name: 'workspace',
  defaults: initiaState
})
export class WorkspaceState implements NgxsOnInit {
  ctx = null;

  constructor(private wsApi: WsApi) {
  }

  ngxsOnInit(ctx?: StateContext<WorkspaceState>): void | any {
    this.ctx = ctx;
  }

  static getAttrFromWorspaceState(path: string, defaultValue: any){
    return createSelector([WorkspaceState], (state: WorkspaceModel) => {
      return _.get(state, path, defaultValue)
    })
  }

  //Selectors

  @Selector()
  static getWorkspaces(state: WorkspaceModel){
    return state.content;
  }

  @Selector()
  static getCurrentTab(state: WorkspaceModel){
    return state.currentTab;
  }

  @Selector()
  static getLoading(state: WorkspaceModel){
    return state.loading;
  }


  //Actions

  @Action(fromWS.loadWS)
  loadWs(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadWS){
    const {
      wsId,
      uwYear
    } = payload;

    ctx.patchState({
      loading: true
    });

    return this.wsApi.searchWorkspace(wsId,uwYear)
      .pipe(
        mergeMap(ws => ctx.dispatch(new fromWS.loadWsSuccess({
          wsId,
          uwYear,
          ws
        }))),
        catchError( e => ctx.dispatch(new fromWS.loadWsFail()))
      )
  }

  @Action(fromWS.loadWsSuccess)
  loadWsSuccess(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.loadWsSuccess){
    const {
      wsId,
      uwYear,
      ws
    } = payload;

    const {
      content
    } = ctx.getState()

    const wsIdentifier = wsId+'-'+uwYear;

    ctx.patchState({
      content: _.merge({},content, { [wsId+ '-'+ uwYear]: {wsId,uwYear,...ws} } ),
      loading: false
    })

    return ctx.dispatch(new fromWS.setCurrentTab({
      index: _.size(content),
      wsIdentifier
    }))
  }

  @Action(fromWS.openWS)
  openWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.openWS){
    const {
      wsId,
      uwYear
    } = payload;

    const {
      content
    } = ctx.getState()

    const wsIdentifier = wsId+'-'+uwYear;

    if(content[wsIdentifier]){
      return ctx.dispatch(new fromWS.setCurrentTab({
        index: _.findIndex(_.toArray(content), ws => ws.wsId == wsId && ws.uwYear == uwYear),
        wsIdentifier
      }))
    }else{
      return ctx.dispatch(new fromWS.loadWS({
        wsId,
        uwYear
      }))
    }
  }

  @Action(fromWS.setCurrentTab)
  setCurrentTab(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.setCurrentTab){
    const {
      index,
      wsIdentifier
    } = payload;

    const {
      routing
    } = ctx.getState();

    ctx.patchState({
      currentTab: {
        index,
        wsIdentifier
      }
    })

    ctx.dispatch(new Navigate([`workspace/${_.replace(wsIdentifier, '-','/')}${routing ? '/'+routing : ''}`]))

  }

  @Action(fromWS.closeWS)
  closeWorkspace(ctx: StateContext<WorkspaceModel>, {payload}: fromWS.closeWS){
    const {
      wsIdentifier
    } = payload

    const {
      currentTab,
      content
    } = ctx.getState()

    if(wsIdentifier == currentTab.wsIdentifier) {
      if(currentTab.index  === _.size(content) - 1) {
        ctx.dispatch(new fromWS.setCurrentTab({
          index: currentTab.index - 1,
          wsIdentifier: _.keys(content)[currentTab.index - 1]
        }))
      }else {
        ctx.dispatch(new fromWS.setCurrentTab({
          index: currentTab.index,
          wsIdentifier: _.keys(content)[currentTab.index + 1]
        }))
      }
    }else {
      let i = _.findIndex(_.toArray(content), ws => ws.wsId+'-'+ws.uwYear == wsIdentifier)
      if(currentTab.index > i) {
        ctx.dispatch(new fromWS.setCurrentTab({
          index: currentTab.index - 1,
          wsIdentifier: currentTab.wsIdentifier
        }))
      }
    }

    ctx.patchState({
      content: _.keyBy(_.filter(content, ws => ws.wsId+'-'+ws.uwYear != wsIdentifier), (el) => el.wsId+'-'+el.uwYear)
    })

  }

}
