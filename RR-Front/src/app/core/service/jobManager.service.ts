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

    exemple =
        {
            id: 0,
            selected: false,
            progress: 75,
            cedantName: 'SCOR SE',
            cedantCode: ' 22231',
            description: 'SBS-SCOR ASIA PACIF...',
            duration: '5 min remaining',
            jobId: '001',
            linkTo: 'RiskLink',
            date: 'today',
            workSpaceId: '00F0006',
            uwYear: 2018,
            workspaceName: 'SBS-SCOR ASIA PACIF.-BEIJING',
            jobOwner: 'Rim Benabbes',
            jobType: 'Import',
            context: {
                data: 'ALABAMA INS.UA - 2019',
                year: 2018,
                program: 'Cat Program, 1st/4th 1year, 2nd/3rd year ½'
            },
            append: false,
            isPaused: false,
            pending: false,
            priority: 'low',
            startTime: '2019-01-03 T 09:57:10',
            elapsedTime: '2019-01-03 T 09:57:10',
            completionTime: '2019-01-03 T 09:57:10',
            submittedTime: '2019-01-03 T 09:57:10',
            status: {
                completed: 0,
                total: 3
            },
            content: [
                {
                    progress: 90,
                    projectId: 'P-000004970',
                    contentId: 'For ARC',
                    contentName: 'For ARC',
                    createdAt: 1542882354617,
                    duration: '1 min remaining',
                    createdBy: 'Ghada CHOUK'
                },
                {
                    progress: 60,
                    projectId: 'P-000004971',
                    contentId: 'For ARC',
                    contentName: 'Clone',
                    createdAt: 1542882615080,
                    duration: '3 min remaining',
                    createdBy: 'Ghada CHOUK'
                },
                {
                    progress: 50,
                    projectId: 'P-000004971',
                    contentId: 'For ARC',
                    contentName: 'Clone',
                    createdAt: 1542882354617,
                    duration: '5 min remaining',
                    createdBy: 'Ghada CHOUK'
                }
            ]
        }


    private readonly api = importUrl() + 'import/';

    constructor(private _http: HttpClient) {
    }

    getAllJobs() {
        return this._http.get(`${this.api}user-jobs`);
    }
    pauseJob(){

    }
    cancelJob(){

    }


}

