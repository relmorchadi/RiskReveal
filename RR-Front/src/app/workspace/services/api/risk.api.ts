import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl, importUrl} from "../../../shared/api";

@Injectable({
  providedIn: 'root'
})
export class RiskApi {
  protected URL = `${importUrl()}risk-link/`;
  protected IMPORT_URL= importUrl();

  constructor(private http: HttpClient) {
  }

  loadImportRefData(carId?){
    let params= carId ? {carId} : {};
    return this.http.get(`${this.IMPORT_URL}import/refs`, {params});
  }

  scanDatasources(dataSources:any[], projectId, instanceId,instanceName){
    return this.http.post(`${this.IMPORT_URL}import/config/basic-scan`, dataSources, {params: {projectId, instanceId, instanceName}});
  }

  rescanDataSource(dataSource:any, projectId, instanceId,instanceName){
    return this.http.post(`${this.IMPORT_URL}import/config/single-basic-scan`, dataSource, {params: {projectId, instanceId, instanceName}});
  }

  loadDataSourceContent(instanceId, projectId, rmsId , type){
    return this.http.get(`${this.IMPORT_URL}import/config/get-riskLink-analysis-portfolios`, {params: {instanceId, projectId, rmsId , type}});
  }

  searchRiskLinkData(instanceId, keyword, offset, size): Observable<any> {
    return this.http.get(`${this.IMPORT_URL}rms/listAvailableDataSources`, {params: {instanceId, keyword, offset, size}});
  }

  runDetailedScan(instanceId, projectId, rlAnalysisList, rlPortfolioList){
    return this.http.post(`${this.IMPORT_URL}import/config/detailed-scan`, {instanceId, projectId, rlAnalysisList, rlPortfolioList});
  }

  triggerImport(instanceId, projectId,userId, analysisConfig, portfolioConfig){
    return this.http.post(`${this.IMPORT_URL}import/trigger-import`, {instanceId, projectId,userId, analysisConfig, portfolioConfig});
  }

  loadSourceEpCurveHeaders(rlAnalysisId: any):Observable<any> {
    return this.http.get(`${this.IMPORT_URL}import/config/get-source-ep-headers`, {params: {rlAnalysisId} });
  }

  loadTargetRap(rlAnalysisId: any):Observable<any> {
    return this.http.get(`${this.IMPORT_URL}import/config/get-target-raps-for-analysis`, {params: {rlAnalysisId} });
  }

  loadAnalysisRegionPerils(rlAnalysisIds: any):Observable<any> {
    return this.http.get(`${this.IMPORT_URL}import/config/get-region-peril-for-multi-analysis`, {params: {rlAnalysisIds}})
  }

  getDefaultDataSources(instanceId, projectId, userId){
    return this.http.get(`${this.IMPORT_URL}import/config/get-default-data-sources`, {params: {instanceId, projectId, userId}})
  }

  saveDefaultDataSources(instanceId, projectId,dataSources, userId){
    return this.http.post(`${this.IMPORT_URL}import/config/save-default-data-sources`, {instanceId, projectId,dataSources, userId})
  }

  searchRiskLinkAnalysis(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}analysis?size=20`, {params: {rdmName: paramName}});
  }

  searchRiskLinkPortfolio(paramId, paramName): Observable<any> {
    return this.http.get(`${this.URL}portfolio?size=20`, {params: {edmId: paramId, edmName: paramName}});
  }

}
