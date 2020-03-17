import {forkJoin, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {backendUrl, importUrl} from "../../../shared/api";
import {map} from "rxjs/operators";
import * as _ from 'lodash'

@Injectable({
    providedIn: 'root'
})
export class RiskApi {
    protected URL = `${importUrl()}risk-link/`;
    protected IMPORT_URL = importUrl();

    constructor(private http: HttpClient) {
    }

    loadImportRefData(carId?) {
        let params = carId ? {carId} : {};
        return this.http.get(`${this.IMPORT_URL}import/refs`, {params});
    }

    scanDatasources(dataSources: any[], projectId) {
        return this.http.post(`${this.IMPORT_URL}import/config/basic-scan`, dataSources, {
            params: {
                projectId
            }
        });
    }

    rescanDataSource(dataSource: any, projectId) {
        return this.http.post(`${this.IMPORT_URL}import/config/single-basic-scan`, dataSource, {
            params: {
                projectId
            }
        });
    }

    loadDataSourceContent(instanceId, projectId, rmsId, type) {
        return this.http.get(`${this.IMPORT_URL}import/config/get-riskLink-analysis-portfolios`, {
            params: {
                instanceId,
                projectId,
                rmsId,
                type
            }
        });
    }

    filterRlAnalysis(paginationParams, instanceId, projectId, rdmId, userId, filter, sort, withPagination = true) {
        return this.http.get(`${this.IMPORT_URL}import/config/filter-riskLink-analysis`, {
            params: {
                ...paginationParams,
                instanceId,
                projectId,
                rdmId,
                userId,
                withPagination, ...filter,
                sort
            }
        });
    }

    filterRlPortfolios(paginationParams, instanceId, projectId, edmId, userId, filter, sort, withPagination = true) {
        return this.http.get(`${this.IMPORT_URL}import/config/filter-riskLink-portfolio`, {
            params: {
                ...paginationParams,
                instanceId,
                projectId,
                edmId,
                userId,
                withPagination, ...filter,
                sort
            }
        });
    }

    searchRiskLinkData(instanceId, keyword, offset, size, type): Observable<any> {
        keyword = this._parseKeyword(keyword);
        return this.http.get(`${this.IMPORT_URL}rms/listAvailableDataSources`, {
            params: {
                instanceId,
                keyword,
                offset,
                size,
                type
            }
        });
    }

    private _parseKeyword(keyword) {
        if (_.isEmpty(keyword))
            return keyword;
        if (_.startsWith(keyword, "\"") && _.endsWith(keyword, "\"")) {
            return _.trim(keyword, "\"");
        } else if (!_.includes(keyword, '*')) {
            return '*' + keyword + '*';
        } else {
            return keyword;
        }
    }

    runDetailedScan(instanceId, projectId, rlAnalysisList, rlPortfolioList) {
        return this.http.post(`${this.IMPORT_URL}import/config/detailed-scan`, {
            instanceId,
            projectId,
            rlAnalysisList,
            rlPortfolioList
        });
    }

    triggerImport(instanceId, projectId, userId, analysisConfig, portfolioConfig) {
        return this.http.post(`${this.IMPORT_URL}import/trigger-import`, {
            instanceId,
            projectId,
            userId,
            analysisConfig,
            portfolioConfig
        });
    }

    loadSourceEpCurveHeaders(rlAnalysisId: any): Observable<any> {
        return this.http.get(`${this.IMPORT_URL}import/config/get-source-ep-headers`, {params: {rlAnalysisId}});
    }

    loadTargetRap(rlAnalysisId: any): Observable<any> {
        return this.http.get(`${this.IMPORT_URL}import/config/get-target-raps-for-analysis`, {params: {rlAnalysisId}});
    }

    loadAnalysisRegionPerils(rlAnalysisIds: any): Observable<any> {
        return this.http.get(`${this.IMPORT_URL}import/config/get-region-peril-for-multi-analysis`, {params: {rlAnalysisIds}})
    }

    getSummaryOrDefaultDataSources(instanceId, projectId, userId='1'): Observable<any> {
        return this.http.get(`${this.IMPORT_URL}import/config/get-global-data-sources`, {
            params: {
                instanceId,
                projectId,
                userId
            }
        })
    }

    getDefaultDataSources(instanceId, projectId: any, userId='1'): Observable<any> {
        return this.http.get(`${this.IMPORT_URL}import/config/get-default-data-sources`,{
            params: {
                instanceId,
                projectId,
                userId
            }
        })
    }

    deleteDataSourcesByProjectId(projectId: any) {
        return this.http.delete(`${this.IMPORT_URL}import/config/delete-datasources-by-project-id`,{
            params: {
                projectId
            }
        })
    }

    getAnalysisPortfoliosByProject(projectId) {
        return forkJoin([
            this.http.get(`${this.IMPORT_URL}import/config/get-imported-analysis-configuration`, {params: {projectId}}),
            this.http.get(`${this.IMPORT_URL}import/config/get-imported-portfolio-configuration`, {params: {projectId}})
        ]);
    }

    saveDefaultDataSources(instanceId, projectId, dataSources, userId) {
        return this.http.post(`${this.IMPORT_URL}import/config/save-default-data-sources`, {
            instanceId,
            projectId,
            dataSources,
            userId
        })
    }

    getAutoAttach(divisionsIds, edmIds, rdmIds, wsId) {
        return this.http.get(`${this.IMPORT_URL}import/config/AutoAttach`, {
            params: {
                divisionsIds, edmIds, rdmIds, wsId
            }
        });
    }

    deleteDataSource(rlModelDataSourceId) {
        return this.http.delete(`${this.IMPORT_URL}import/config/data-source`, {
            params: {
                rlModelDataSourceId
            }
        });
    }

    savePortfoliosConfig(portfolioConfig) {
        return this.http.post(`${this.IMPORT_URL}import/config/save-portfolio-import-selection`, portfolioConfig);
    }

    saveAnalysisConfig(analysisConfig) {
        return this.http.post(`${this.IMPORT_URL}import/config/save-analysis-import-selection`, analysisConfig);
    }

    deletePortfolioSummary(rlPortfolioId: any, projectId: any) {
        return this.http.delete(`${this.IMPORT_URL}import/config/delete-portfolio-summary`, {params: {projectId, rlPortfolioId}});
    }

    deleteAnalysisSummary(rlAnalysisId: any, projectId: any) {
        return this.http.delete(`${this.IMPORT_URL}import/config/delete-analysis-summary`, {params: {projectId, rlAnalysisId}});
    }
}
