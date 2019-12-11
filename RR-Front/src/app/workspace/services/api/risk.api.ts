import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl, importUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class RiskApi {
  protected URL = `${importUrl()}risk-link/`;
  protected FURL = `${backendUrl()}fac/`;
  protected IMPORT_URL= importUrl();

  constructor(private http: HttpClient) {
  }

  loadImportRefData(){
    return this.http.get(`${this.IMPORT_URL}import/refs`);
  }

  scanDatasources(dataSources:any[], projectId, instanceId,instanceName){
    return this.http.post(`${this.IMPORT_URL}import/config/basic-scan`, dataSources, {params: {projectId, instanceId, instanceName}});
  }

  loadDataSourceContent(instanceId, projectId, rmsId , type){
    return this.http.get(`${this.IMPORT_URL}import/config/get-riskLink-analysis-portfolios`, {params: {instanceId, projectId, rmsId , type}});
  }

  searchRiskLinkData(instanceId): Observable<any> {
    return this.http.get(`${this.IMPORT_URL}rms/listAvailableDataSources`, {params: {instanceId}});
  }

  searchRiskLinkAnalysis(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}analysis?size=20`, {params: {rdmName: paramName}});
  }

  searchRiskLinkPortfolio(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}portfolio?size=20`, {params: {edmId: paramId, edmName: paramName}});
  }

  searchDetailAnalysis(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}detailed-analysis-scan`, {params: {edmId: paramId, edmName: paramName}});
  }

  searchFacData() {
    return this.http.get(`${this.FURL}datasources`);
  }

  searchFacAnalysisBasic(paramId, paramName, paramData) {
    return this.http.get(`${this.FURL}analysis-basic`, {params: {rdmId: paramId, rdmName: paramName, analysisName: paramData}});
  }

  searchFacAnalysisDetail(paramId, paramName) {
    return this.http.get(`${this.FURL}analysis-detail`, {params: {analysisId: paramId, analysisName: paramName}});
  }

  searchFacPortfolio(paramId, paramName, paramData) {
    return this.http.get(`${this.FURL}portfolio`, {params: {edmId: paramId, edmName: paramName, portNum: paramData}});
  }
}
