import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EntryComponent} from './entry.component';
import {MainComponent} from '../core/containers/main/main.component';
import {SubmissionPageComponent} from '../submission-page/containers/submission-page/submission-page.component';

const routes: Routes = [
  {path: 'CreateNewFile', component: SubmissionPageComponent},
  {path: '', component: MainComponent, children: [
    {data: {title: 'RR- Workspace'}, path: 'workspace', loadChildren: '../workspace/workspace.module#WorkspaceModule'},
    {data: {title: 'RR- Dashboard'}, path: 'dashboard', loadChildren: '../dashboard/dashboard.module#DashboardModule'},
    {data: {title: 'RR- PLT Comparer'}, path: 'plt-comparer', loadChildren: '../plt-comparer/plt-comparer.module#PltComparerModule'},
    {data: {title: 'RR- Search'}, path: 'search', loadChildren: '../search/search.module#SearchModule'},
    {data: {title: 'RR- JOB Manager'}, path: 'jobManager', loadChildren: '../job-manager/job-manager.module#JobManagerModule'},
    {data: {title: 'RR- Notification Manager'}, path: 'notificationManager', loadChildren: '../notification-manager/notification-manager.module#NotificationManagerModule'},
    {data: {title: 'RR- USER Preference'}, path: 'userPreference', loadChildren: '../user-preference/user-preference.module#UserPreferenceModule'},
    {path: '**', redirectTo: 'dashboard'}
  ]}
];

@NgModule({
  declarations: [EntryComponent],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, EntryComponent]
})
export class GatewayModule { }
