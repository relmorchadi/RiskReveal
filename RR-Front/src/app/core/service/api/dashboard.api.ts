import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl} from '../../../shared/api';
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class DashboardApi {
    protected URL = `${backendUrl()}DashBoard/`;
    protected WidgetURL = this.URL + 'Widget/';
    protected TableURL = `${backendUrl()}DashboardWidgetColumns/`;

    constructor(private http: HttpClient) {
    }

    getDashboards(): Observable<any> {
        return this.http.get(`${this.URL}getDashboards`);
    }

    creatDashboards(data): Observable<any> {
        return this.http.post(`${this.URL}create`, data);
    }

    deleteDashboards(userDashboardId): Observable<any> {
        return this.http.delete(`${this.URL}delete`, {params: {userDashboardId}, responseType: 'text' as 'json'});
    }

    updateDashboard(userDashboard, updatedDashboard) {
       return this.http.put(`${this.URL}update`, updatedDashboard, {params: {userDashboard}, responseType: 'text' as 'json'});
    }

    getReferenceWidget() {
        return this.http.get(`${this.WidgetURL}getRefData`);
    }

    createWidget(data) {
        return this.http.post(`${this.WidgetURL}create`, data);
    }

    deleteWidget(dashboardWidgetId) {
        return this.http.delete(`${this.WidgetURL}delete`, {params: {dashboardWidgetId}, responseType: 'text' as 'json'});
    }

    deleteAllWidgetByRef(dashboardId, refId) {
        return this.http.delete(`${this.WidgetURL}deleteByRef`, {params: {dashboardId, refId}, responseType: 'text' as 'json'});
    }

    duplicateWidget(dashboardWidgetId) {
        return this.http.post(`${this.WidgetURL}duplicate`,{} ,{params: {dashboardWidgetId}});
    }

    updateWidget(widget) {
        return this.http.put(`${this.WidgetURL}update`, widget, {responseType: 'text' as 'json'});
    }

    manageColumnsWidget(tableCols) {
        return this.http.put(`${this.TableURL}updateVisibilityAndOrder`, tableCols, {responseType: 'text' as 'json'});
    }

    updateColsWidth(columnId, width) {
        return this.http.put(`${this.TableURL}updateWidth`, {}, {params: {columnId, width},
            responseType: 'text' as 'json'});
    }

    updateSortCols(columnId, sort, sortType) {
        return this.http.put(`${this.TableURL}updateSort`, {}, {params: {columnId, sort, sortType},
            responseType: 'text' as 'json'});
    }

    updateFilterCols(columnId, filter) {
        return this.http.put(`${this.TableURL}updateFilter`, {}, {params: {columnId, filter},
            responseType: 'text' as 'json'});
    }

    getFacDashboardResources(filters): Observable<any>  {
        return this.http.post(`${backendUrl()}dashboard/getData`, filters);
    }

    getWidgetCarStatusCount(): Observable<any> {
        return this.http.get(`${backendUrl()}dashboard/`);
    }

    getWidgetAssignedCountByUwAnalysis(): Observable<any> {
        return this.http.get(`${backendUrl()}dashboard/`)
    }

    getWidgetAssignedPercentageByUwAnalysis(): Observable<any> {
        return this.http.get(`${backendUrl()}dashboard/`)
    }

}
