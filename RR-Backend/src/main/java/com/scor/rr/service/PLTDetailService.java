package com.scor.rr.service;

import com.scor.rr.domain.TargetBuild.PLTDetails.PLTDetailSummary;
import com.scor.rr.domain.TargetBuild.PLTManagerView;
import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.repository.TargetBuild.PLTDetailSummaryRepository;
import com.scor.rr.repository.TargetBuild.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class PLTDetailService {

    @Autowired
    PLTDetailSummaryRepository pltDetailSummaryRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    PltBrowserService pltBrowserService;


    public PLTDetailSummary getPLTDetailSummary(Integer pltHeaderId) {
        Optional<Workspace> workspaceOpt = workspaceRepository.findWorkspaceByPltHeaderId(pltHeaderId);
        Workspace ws;
        if(workspaceOpt.isPresent()) {
            ws = workspaceOpt.get();

            PLTManagerView pltManagerView = new PLTManagerView();

            pltManagerView.setPltId(pltHeaderId);

            Optional<PLTDetailSummary> pltDetailSummaryOpt = pltDetailSummaryRepository.findById(pltHeaderId);
            PLTDetailSummary pltDetailSummary;

            if(pltDetailSummaryOpt.isPresent()) {
                pltDetailSummary =  pltDetailSummaryOpt.get();
                pltDetailSummary.setTags(pltBrowserService.appendTagsToPLTs(new HashSet<PLTManagerView>(){{
                    add(pltManagerView);
                }}, ws).getTags());

                return pltDetailSummary;
            }

            return null;

        }
        return null;
    }

}
