import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseContainer} from '../../../shared/base';
import {Store} from '@ngxs/store';

@Component({
  selector: 'app-workspace-activity',
  templateUrl: './workspace-activity.component.html',
  styleUrls: ['./workspace-activity.component.scss']
})
export class WorkspaceActivityComponent extends BaseContainer implements OnInit {

  hyperLinks: string[]= ['Projects', 'Contract', 'Activity'];
  hyperLinksRoutes: any= {
    'Projects': '',
    'Contract': '/Contract',
    'Activity': '/Activity'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  contentActive = {
    today: [{
        color: '#06B8FF',
        icon: 'icon-chat_24px',
        text: 'Project P-6458 has been assigned to you by Huw Parry',
        projectInfo: [],
        expanded: false,
        project: 'P-6458',
        timeStamp: 'Wed Jul 04 16:19:39 UTC 2019'
      },
      {
        color: '#C38FFF',
        icon: 'icon-focus-add',
        text: 'Accumulation Package AP-3857 has been successfully published to ARC',
        projectInfo: [],
        expanded: false,
        project: 'AP-3857',
        timeStamp: 'Wed Jul 04 16:19:39 UTC 2019'
      },
      {
        color: '#7BBE31',
        icon: 'icon-check_24px',
        text: 'Calculation on Inuring Package IP-2645 is now complete',
        projectInfo: [],
        expanded: false,
        project: 'IP-2645',
        timeStamp: 'Wed Jul 04 16:19:39 UTC 2019'
      }
    ],
    yesterday: [
      {
        color: '#6200EE',
        icon: 'icon-file-alt',
        text: '4 PLTs imported by Huw Parry',
        projectInfo: [
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1']
        ],
        expanded: false,
        timeStamp: 'Wed Jul 04 16:19:39 UTC 2019'
      },
      {
        color: '#0700CF',
        icon: 'icon-insert_chart_outlined_24px',
        text: 'Project P-8687: 4 PLTs have been successfully published to pricing',
        project: 'P-8687',
        projectInfo: [
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1']
        ],
        expanded: false,
        timeStamp: 'Wed Jul 04 16:19:39 UTC 2019'
      }
    ],
    date: [
      {
        color: '#6B0FEF',
        icon: 'icon-file-alt',
        text: '2 PLTs unpublished by Huw Parry ',
        project: '',
        projectInfo: [
          ['937080', 'NATC-USM_RL_lmf.T1'],
          ['937080', 'NATC-USM_RL_lmf.T1']
        ],
        expanded: false,
        timeStamp: 'Wed Jul 04 16:19:39 UTC 2019'
      },
      {
        color: '#03DAC4',
        icon: 'icon-window-section',
        text: 'Workspace 02PY376 created by Huw Parry',
        projectInfo: [],
        expanded: false,
        project: '02PY376',
        timeStamp: 'Wed Jul 04 16:19:39 UTC 2019'
      }
    ]
  };

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
      this.hyperLinksConfig= {
        wsId,
        uwYear: year
      }
    })
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
