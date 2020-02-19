package com.scor.rr.service;

import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.entities.PLTManagerView;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.entities.Tag;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.TargetBuild.PLTHeaderDeleteRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewHelperResponse;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.domain.entities.ViewContextColumns;
import com.scor.rr.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class PltBrowserService {

    @Autowired
    UserTagRepository userTagRepository;
    @Autowired
    WorkspaceEntityRepository workspaceEntityRepository;
    @Autowired
    PltHeaderRepository pltHeaderRepository;
    @Autowired
    UserRrRepository userRrRepository;

    @Autowired
    PLTManagerViewRepository pltManagerViewRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PLTHeaderTagRepository pltHeaderTagRepository;
    @Autowired
    ViewContextColumnsRepository viewContextColumnsRepository;
    @Autowired
    EntityManager entityManager;

    PLTManagerViewHelperResponse appendTagsToPLTs(Set<PLTManagerView> plts, WorkspaceEntity ws) {
        HashMap<Long, Tag> pltHeaderTagCount = new HashMap<>();
        plts.forEach( pltView -> {
                    pltView.setTags(
                            pltHeaderTagRepository.findByPltHeaderId(pltView.getPltId())
                                    .stream()
                                    .map( pltHeaderTag -> {
                                        Optional<Tag> tmpTagOpt = tagRepository.findById(pltHeaderTag.getTagId());
                                        Tag tmpTag = new Tag();
                                        if(tmpTagOpt.isPresent()) {
                                            tmpTag = tmpTagOpt.get();

                                            if(pltHeaderTagCount.containsKey(tmpTag.getTagId())) {
                                                tmpTag.setCount(pltHeaderTagCount.get(tmpTag.getTagId()).getCount());

                                            } else {
                                                tmpTag.setCount(pltHeaderTagRepository.findByWorkspaceId(ws.getWorkspaceId()).size());

                                            }
                                        }
                                        pltHeaderTagCount.put(tmpTag.getTagId(), tmpTag);
                                        return tmpTag;
                                    }).collect(Collectors.toSet())
                    );
                });
        return new PLTManagerViewHelperResponse(plts, new HashSet<>(pltHeaderTagCount.values()));
    }

    public PLTManagerViewResponse getPLTHeaderView(PLTManagerViewRequest request) {

        PLTManagerViewResponse response= new PLTManagerViewResponse();

        response.setPlts(this.pltManagerViewRepository.getPLTManagerData(
                request.getWorkspaceContextCode(),
                request.getWorkspaceUwYear(),
                request.getEntity(),
                "A798",
                request.getPageNumber(),
                request.getPageSize(),
                request.getSelectionList(),
                request.getSortSelectedFirst(),
                request.getSortSelectedAction()
        ));

        response.setTotalCount(this.useGetPLTManagerDataCountProc(request));


        return response;

    }

    public List<Map<String, Object>> getColumns() {
        try {
            return this.pltManagerViewRepository.getColumns("A798", 2L);
        } catch (Exception ex) {
            log.error("Couldn't Get PLT Manager Columns with message: {}", ex.getMessage());
        }
        return null;
    }

    public void updateColumnWidth(UpdateColumnWidthRequest request) {
        Optional<ViewContextColumns> opt = this.viewContextColumnsRepository.findById(request.getViewContextColumnId());
        Integer newWidth;
        if(opt.isPresent()) {
            ViewContextColumns column = opt.get();

            if(column.getMaxWidth() < request.getWidth()) {
                newWidth = column.getMaxWidth();
            } else if(column.getMinWidth() > request.getWidth()) {
                newWidth = column.getMinWidth();
            } else {
                newWidth = request.getWidth();
            }

            this.viewContextColumnsRepository.updateColumnWidth("A798", request.getViewContextColumnId(), newWidth);
        }
    }

    Integer useGetPLTManagerDataCountProc(PLTManagerViewRequest request) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("dbonew.usp_PLTManagerGetThreadEndPLTsCount")
                .registerStoredProcedureParameter(
                        "WorkspaceContextCode",
                        String.class,
                        ParameterMode.IN
                )
                .registerStoredProcedureParameter(
                        "WorkspaceUwYear",
                        Integer.class,
                        ParameterMode.IN
                )
                .registerStoredProcedureParameter(
                        "Entity",
                        Integer.class,
                        ParameterMode.IN
                )
                .registerStoredProcedureParameter(
                        "UserCode",
                        String.class,
                        ParameterMode.IN
                )
                .registerStoredProcedureParameter(
                        "TotalRecCount",
                        Integer.class,
                        ParameterMode.OUT
                )
                .registerStoredProcedureParameter(
                        "FilteredRecCount",
                        Integer.class,
                        ParameterMode.OUT
                )
                .setParameter("WorkspaceContextCode", request.getWorkspaceContextCode())
                .setParameter("WorkspaceUwYear", request.getWorkspaceUwYear())
                .setParameter("Entity", request.getEntity())
                .setParameter("UserCode", "A798");

        try {
            query.execute();
            return (Integer) query.getOutputParameterValue("FilteredRecCount");

        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }

    public void updateColumnSort(UpdateColumnSortRequest request) {
        this.viewContextColumnsRepository.updateColumnSort("A798", request.getViewContextId(), request.getViewContextColumnId());
    }

    public void updateColumnFilterCriteria(UpdateColumnFilterCriteriaRequest request) {
        this.viewContextColumnsRepository.updateColumnFilterCriteria("A798", request.getViewContextId(), request.getViewContextColumnId(), request.getFilterCriteria());
    }

    public void updateColumnOrderAndVisibility(UpdateColumnOrderAndVisibilityRequest request) {
        this.viewContextColumnsRepository.updateColumnOrderAndVisibility("A798", request.getViewContextId(), request.getColumnsList());
    }

    public List<Map<String, Object>> getIDs(PLTManagerIDsRequest request) {
        try {
            return this.pltManagerViewRepository.getIDs(request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), 1, "A798");
        } catch (Exception e) {
            log.error("Couldn't Get PLT Manager IDs with message: {}", e.getMessage());
        }

        return new ArrayList<>();
    }

    /*public Boolean deletePLTheader(PLTHeaderDeleteRequest request) {
        request.getPltHeaderIds().forEach( pltHeaderId -> {
            Optional<PltHeaderEntity> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
            PltHeaderEntity pltHeaderEntity;

            if(pltHeaderOpt.isPresent()) {
                pltHeaderEntity = pltHeaderOpt.get();

                if(pltHeaderEntity.getDeletedBy() == null && pltHeaderEntity.getDeletedOn() == null && pltHeaderEntity.getDeletedDue() == null) {

                    pltHeaderEntity.setDeletedBy(request.getDeletedBy());
                    pltHeaderEntity.setDeletedDue(request.getDeletedDue());
                    pltHeaderEntity.setDeletedOn(request.getDeletedOn());

                    pltHeaderRepository.save(pltHeaderEntity);
                }

            }
        });
        return true;
    }

    public Boolean restorePLTHeader(List<Long> pltHeaderIds) {
        pltHeaderIds.forEach((pltHeaderId) -> {
            Optional<PltHeaderEntity> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
            PltHeaderEntity pltHeaderEntity;

            if(pltHeaderOpt.isPresent()) {
                pltHeaderEntity = pltHeaderOpt.get();

                if(pltHeaderEntity.getDeletedBy() != null && pltHeaderEntity.getDeletedOn() != null && pltHeaderEntity.getDeletedDue() != null) {

                    pltHeaderEntity.setDeletedBy(null);
                    pltHeaderEntity.setDeletedDue(null);
                    pltHeaderEntity.setDeletedOn(null);

                    pltHeaderRepository.save(pltHeaderEntity);
                }

            }
        });
        return true;
    }*/
}
