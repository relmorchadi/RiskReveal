/*
 * Date : 11/5/2020.
 * Author : Reda El Morchadi
 */

import {Observable, Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WorkspaceFilter} from '../model/workspace-filter';
import * as _ from 'lodash'
import {backendUrl, importUrl} from "../../shared/api";


@Injectable({
    providedIn: 'root'
})
export class JobManagerService {


    private readonly api = importUrl() + 'import/';

    constructor(private _http: HttpClient) {
    }

    getAllJobs() {
        return this._http.get(`${this.api}user-jobs`);
    }
    pauseJob(jobId){
        return this._http.get(`${this.api}pause-job?jobId=`+jobId,{responseType: 'text'});
    }
    deleteJob(jobId){
        return this._http.get(`${this.api}cancel-job-or-task?id=`+jobId+'&type=job',{responseType: 'text'});
    }
    resumeJob(jobId) {
        return this._http.get(`${this.api}resume-job?jobId=`+jobId, {responseType: 'json'});
    }


}

