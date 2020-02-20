import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EntryComponent} from './entry.component';
import {MainComponent} from '../core/containers/main/main.component';
import {AuthGuard} from "./guards/auth.gards";
import { JwtModule } from "@auth0/angular-jwt";
import { HttpClientModule } from "@angular/common/http";
import {UnauthorizedComponent} from "../core/containers";

const routes: Routes = [
  {path: 'unauthorized', data: {title: 'Error- 401'}, component: UnauthorizedComponent},
  {path: '', component: MainComponent, children: [
    {data: {title: 'RR- Workspace'}, path: 'workspace', loadChildren: '../workspace/workspace.module#WorkspaceModule'},
    {data: {title: 'RR- Dashboard'}, path: 'dashboard', loadChildren: '../dashboard/dashboard.module#DashboardModule'},
    //{data: {title: 'RR- PLT Comparer'}, path: 'plt-comparer', loadChildren: '../plt-comparer/plt-comparer.module#PltComparerModule'},
    {data: {title: 'RR- Search'}, path: 'search', loadChildren: '../search/search.module#SearchModule'},
    //{data: {title: 'RR- JOB Manager'}, path: 'jobManager', loadChildren: '../job-manager/job-manager.module#JobManagerModule'},
    //{data: {title: 'RR- Notification Manager'}, path: 'notificationManager', loadChildren: '../notification-manager/notification-manager.module#NotificationManagerModule'},
    {data: {title: 'RR- USER Preference'}, path: 'userPreference', loadChildren: '../user-preference/user-preference.module#UserPreferenceModule'},
    {path: '**', redirectTo: 'dashboard'}
  ]}
];

export function tokenGetter() {
  return localStorage.getItem("token");
}

@NgModule({
  declarations: [EntryComponent],
  imports: [
    RouterModule.forRoot(routes),
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
      }
    })],
  exports: [RouterModule, EntryComponent]
})
export class GatewayModule { }
