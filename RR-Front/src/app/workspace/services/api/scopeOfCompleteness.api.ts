import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl, importUrl} from "../../../shared/api";

@Injectable({
    providedIn: 'root'
})
export class ScopeOfCompletenessApi {

    private readonly api = `${backendUrl()}ScopeAndCompleteness/`;

    constructor(private  http: HttpClient) {
    }

    getData(uwyear, workspaceId, projectId) {
        return this.http.get(`${this.api}AccumulationPackage/getScopeOnly`, { params: {uwyear, workspaceId, projectId}});
    }

    getDataPending(AccumulationPackageId, uwyear, workspaceId, ProjectId) {
        return this.http.get(`${this.api}AccumulationPackage/getAccumulationPackage`, { params: {AccumulationPackageId, uwyear, workspaceId, ProjectId}});
    }

    getDataPricing(UWYear, WorkspaceName, projectId) {
        return this.http.get(`${this.api}PricedScopeAndCompleteness/getPricedScope`, { params: {UWYear, WorkspaceName, projectId}});
    }

    getDataAccumulation(ProjectID) {
        return this.http.get(`${this.api}AccumulationPackage/getDropDownInformation`, { params: {ProjectID}});
    }

    getListOfPLTs(accumulationPackageId, projectId) {
        return this.http.get(`${this.api}AttachPLT/getPLTsForPopUp`, { params: {accumulationPackageId, projectId}});
    }

    attachePLTCreate(data) {
        return this.http.post(`${this.api}AttachPLT/attachPLTs`, data, {responseType: 'text' as 'json'});
    }

    attachPLTDelete(pltIds) {
        return this.http.delete(`${this.api}AttachPLT/attachPLTs`, { params: {pltIds}, responseType: 'text' as 'json'});
    }

    fileReader(fileName) {
        return this.http.post(`${this.api}FWFileReader/create`, {},{ params: {fileName}, responseType: 'text' as 'json'});
    }

    overrideDone(data) {
        return this.http.post(`${this.api}OverrideSection/override`, data)
    }

    overrideDelete(overriddenSections) {
        return this.http.post(`${this.api}OverrideSection/deleteOverride`,  overriddenSections, {responseType: 'text' as 'json'});
    }
}
