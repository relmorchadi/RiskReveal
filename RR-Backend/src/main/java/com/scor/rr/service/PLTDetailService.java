package com.scor.rr.service;

import com.scor.rr.domain.entities.PLTDetails.PLTDetailSummary;
import com.scor.rr.domain.entities.PLTManager.PLTManagerView;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.repository.PLTDetailSummaryRepository;
import com.scor.rr.repository.WorkspaceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class PLTDetailService {

    @Autowired
    PLTDetailSummaryRepository pltDetailSummaryRepository;

    @Autowired
    WorkspaceEntityRepository workspaceEntityRepository;

    @Autowired
    PltBrowserService pltBrowserService;


    public PLTDetailSummary getPLTDetailSummary(Long pltHeaderId) {
        Optional<WorkspaceEntity> workspaceOpt = workspaceEntityRepository.findWorkspaceByPltHeaderId(pltHeaderId);
        WorkspaceEntity ws;
        if(workspaceOpt.isPresent()) {
            ws = workspaceOpt.get();

            PLTManagerView pltManagerView = new PLTManagerView();

            pltManagerView.setPltId(pltHeaderId);

            Optional<PLTDetailSummary> pltDetailSummaryOpt = pltDetailSummaryRepository.findById(pltHeaderId);
            PLTDetailSummary pltDetailSummary;

            if(pltDetailSummaryOpt.isPresent()) {
                pltDetailSummary =  pltDetailSummaryOpt.get();
//                pltDetailSummary.setTags(pltBrowserService.appendTagsToPLTs(new HashSet<PLTManagerView>(){{
//                    add(pltManagerView);
//                }}, ws).getTags());

                return pltDetailSummary;
            }

            return null;

        }
        return null;
    }

}
