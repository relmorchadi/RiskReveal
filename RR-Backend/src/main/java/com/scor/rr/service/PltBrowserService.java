package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.entities.PLTManager.GroupedPLTs;
import com.scor.rr.domain.entities.PLTManager.PLTManagerView;
import com.scor.rr.domain.entities.PLTManager.Tag;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewHelperResponse;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.domain.entities.ViewContextColumns;
import com.scor.rr.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class PltBrowserService {

//    @Autowired
//    UserTagRepository userTagRepository;
//    @Autowired
//    WorkspaceEntityRepository workspaceEntityRepository;
//    @Autowired
//    PltHeaderRepository pltHeaderRepository;
//    @Autowired
//    UserRrRepository userRrRepository;
//
//    @Autowired
//    PLTManagerViewRepositoryold pltManagerViewRepository;
//    @Autowired
//    TagRepository tagRepository;
//    @Autowired
//    PLTHeaderTagRepository pltHeaderTagRepository;
//    @Autowired
//    ViewContextColumnsRepository viewContextColumnsRepository;
//    @Autowired
//    EntityManager entityManager;
//
//    PLTManagerViewHelperResponse appendTagsToPLTs(Set<PLTManagerView> plts, WorkspaceEntity ws) {
//        HashMap<Long, Tag> pltHeaderTagCount = new HashMap<>();
//        plts.forEach( pltView -> {
//                    pltView.setTags(
//                            pltHeaderTagRepository.findByPltHeaderId(pltView.getPltId())
//                                    .stream()
//                                    .map( pltHeaderTag -> {
//                                        Optional<Tag> tmpTagOpt = tagRepository.findById(pltHeaderTag.getTagId());
//                                        Tag tmpTag = new Tag();
//                                        if(tmpTagOpt.isPresent()) {
//                                            tmpTag = tmpTagOpt.get();
//
//                                            if(pltHeaderTagCount.containsKey(tmpTag.getTagId())) {
//                                                tmpTag.setCount(pltHeaderTagCount.get(tmpTag.getTagId()).getCount());
//
//                                            } else {
//                                                tmpTag.setCount(pltHeaderTagRepository.findByWorkspaceId(ws.getWorkspaceId()).size());
//
//                                            }
//                                        }
//                                        pltHeaderTagCount.put(tmpTag.getTagId(), tmpTag);
//                                        return tmpTag;
//                                    }).collect(Collectors.toSet())
//                    );
//                });
//        return new PLTManagerViewHelperResponse(plts, new HashSet<>(pltHeaderTagCount.values()));
//    }
//
//    public PLTManagerViewResponse getPLTHeaderView(PLTManagerViewRequest request) {
//            UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//            PLTManagerViewResponse response= new PLTManagerViewResponse();
//
//            response.setPlts(this.pltManagerViewRepository.getPLTManagerData(
//                    request.getWorkspaceContextCode(),
//                    request.getWorkspaceUwYear(),
//                    request.getEntity(),
//                    user.getUserCode(),
//                    request.getPageNumber(),
//                    request.getPageSize(),
//                    request.getSelectionList(),
//                    request.getSortSelectedFirst(),
//                    request.getSortSelectedAction()
//            ));
//
//            response.setTotalCount(this.useGetPLTManagerDataCountProc(request));
//
//
//            return response;
//
//    }
//
//    public List<GroupedPLTs> getPLTManagerGroupedPLTs(PLTManagerViewRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setFieldMatchingEnabled(true);
//
//        List<GroupedPLTs> plts = this.pltManagerViewRepository.getPLTManagerGroupedPLTs(
//                request.getWorkspaceContextCode(),
//                request.getWorkspaceUwYear(),
//                request.getEntity(),
//                user.getUserCode(),
//                request.getPageNumber(),
//                request.getPageSize(),
//                request.getSelectionList(),
//                request.getSortSelectedFirst(),
//                request.getSortSelectedAction()
//        ).stream()
//                .map(plt -> mapper.map(plt, GroupedPLTs.class))
//                .collect(Collectors.toList());
//
//        Map<Long, GroupedPLTs> pures = new HashMap<>();
//
//        plts.stream()
//                .filter(plt -> plt.getThreadId() == null)
//                .forEach(pure -> pures.put(pure.getPltId(), pure));
//
//        Map<Long, List<GroupedPLTs>> threadsByPureId =  plts
//                .stream()
//                .filter(plt -> plt.getThreadId() != null)
//                .collect(Collectors.groupingBy(plt -> plt.getPureId() == null ? -1 : plt.getPureId()));
//
//        return threadsByPureId
//                .keySet()
//                .stream()
//                .map(pureId -> {
//                    GroupedPLTs pure = pures.get(pureId);
//                    pure.setThreads(threadsByPureId.get(pureId));
//                    return pure;
//                }).collect(Collectors.toList());
//
////        return this.pltManagerViewRepository.getPLTManagerGroupedPLTs(
////                request.getWorkspaceContextCode(),
////                request.getWorkspaceUwYear(),
////                request.getEntity(),
////                user.getUserCode(),
////                request.getPageNumber(),
////                request.getPageSize(),
////                request.getSelectionList(),
////                request.getSortSelectedFirst(),
////                request.getSortSelectedAction()
////        );
//    }
//
//    public List<Map<String, Object>> getColumns() {
//        try {
//            UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//            return this.pltManagerViewRepository.getColumns(user.getUserCode(), 2L);
//        } catch (Exception ex) {
//            log.error("Couldn't Get PLT Manager Columns with message: {}", ex.getMessage());
//        }
//        return null;
//    }
//
//    public void updateColumnWidth(UpdateColumnWidthRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        Optional<ViewContextColumns> opt = this.viewContextColumnsRepository.findById(request.getViewContextColumnId());
//        Integer newWidth;
//        if(opt.isPresent()) {
//            ViewContextColumns column = opt.get();
//
//            if(column.getMaxWidth() < request.getWidth()) {
//                newWidth = column.getMaxWidth();
//            } else if(column.getMinWidth() > request.getWidth()) {
//                newWidth = column.getMinWidth();
//            } else {
//                newWidth = request.getWidth();
//            }
//
//            this.viewContextColumnsRepository.updateColumnWidth(user.getUserCode(), request.getViewContextColumnId(), newWidth);
//        }
//    }
//
//    Integer useGetPLTManagerDataCountProc(PLTManagerViewRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        StoredProcedureQuery query = entityManager
//                .createStoredProcedureQuery("dbo.usp_PLTManagerGetThreadEndPLTsCount")
//                .registerStoredProcedureParameter(
//                        "WorkspaceContextCode",
//                        String.class,
//                        ParameterMode.IN
//                )
//                .registerStoredProcedureParameter(
//                        "WorkspaceUwYear",
//                        Integer.class,
//                        ParameterMode.IN
//                )
//                .registerStoredProcedureParameter(
//                        "Entity",
//                        Integer.class,
//                        ParameterMode.IN
//                )
//                .registerStoredProcedureParameter(
//                        "UserCode",
//                        String.class,
//                        ParameterMode.IN
//                )
//                .registerStoredProcedureParameter(
//                        "TotalRecCount",
//                        Integer.class,
//                        ParameterMode.OUT
//                )
//                .registerStoredProcedureParameter(
//                        "FilteredRecCount",
//                        Integer.class,
//                        ParameterMode.OUT
//                )
//                .setParameter("WorkspaceContextCode", request.getWorkspaceContextCode())
//                .setParameter("WorkspaceUwYear", request.getWorkspaceUwYear())
//                .setParameter("Entity", request.getEntity())
//                .setParameter("UserCode", user.getUserCode());
//
//        try {
//            query.execute();
//            return (Integer) query.getOutputParameterValue("FilteredRecCount");
//
//        } finally {
//            query.unwrap(ProcedureOutputs.class).release();
//        }
//    }
//
//    public void updateColumnSort(UpdateColumnSortRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        this.viewContextColumnsRepository.updateColumnSort(user.getUserCode(), request.getViewContextId(), request.getViewContextColumnId());
//    }
//
//    public void resetColumnSort(ResetColumnSortRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        this.viewContextColumnsRepository.resetColumnSort(user.getUserCode(), request.getViewContextId());
//    }
//
//    public void updateColumnFilterCriteria(UpdateColumnFilterCriteriaRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        this.viewContextColumnsRepository.updateColumnFilterCriteria(user.getUserCode(), request.getViewContextId(), request.getViewContextColumnId(), request.getFilterCriteria());
//    }
//
//    public void filterByProjectId(UpdateColumnFilterCriteriaRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        this.viewContextColumnsRepository.filterByProjectId(user.getUserCode(), 2L, request.getProjectId().toString());
//    }
//
//    public void resetColumnFilterCriteria(ResetColumnFilterCriteriaRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        this.viewContextColumnsRepository.resetColumnFilterCriteria(user.getUserCode(), request.getViewContextId());
//    }
//
//    public void updateColumnOrderAndVisibility(UpdateColumnOrderAndVisibilityRequest request) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        this.viewContextColumnsRepository.updateColumnOrderAndVisibility(user.getUserCode(), request.getViewContextId(), request.getColumnsList());
//    }
//
//    public List<Map<String, Object>> getIDs(PLTManagerIDsRequest request) {
//        try {
//            UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//            return this.pltManagerViewRepository.getIDs(request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), 1, user.getUserCode());
//        } catch (Exception e) {
//            log.error("Couldn't Get PLT Manager IDs with message: {}", e.getMessage());
//        }
//
//        return new ArrayList<>();
//    }
//
//    /*public Boolean deletePLTheader(PLTHeaderDeleteRequest request) {
//        request.getPltHeaderIds().forEach( pltHeaderId -> {
//            Optional<PltHeaderEntity> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
//            PltHeaderEntity pltHeaderEntity;
//
//            if(pltHeaderOpt.isPresent()) {
//                pltHeaderEntity = pltHeaderOpt.get();
//
//                if(pltHeaderEntity.getDeletedBy() == null && pltHeaderEntity.getDeletedOn() == null && pltHeaderEntity.getDeletedDue() == null) {
//
//                    pltHeaderEntity.setDeletedBy(request.getDeletedBy());
//                    pltHeaderEntity.setDeletedDue(request.getDeletedDue());
//                    pltHeaderEntity.setDeletedOn(request.getDeletedOn());
//
//                    pltHeaderRepository.save(pltHeaderEntity);
//                }
//
//            }
//        });
//        return true;
//    }
//
//    public Boolean restorePLTHeader(List<Long> pltHeaderIds) {
//        pltHeaderIds.forEach((pltHeaderId) -> {
//            Optional<PltHeaderEntity> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
//            PltHeaderEntity pltHeaderEntity;
//
//            if(pltHeaderOpt.isPresent()) {
//                pltHeaderEntity = pltHeaderOpt.get();
//
//                if(pltHeaderEntity.getDeletedBy() != null && pltHeaderEntity.getDeletedOn() != null && pltHeaderEntity.getDeletedDue() != null) {
//
//                    pltHeaderEntity.setDeletedBy(null);
//                    pltHeaderEntity.setDeletedDue(null);
//                    pltHeaderEntity.setDeletedOn(null);
//
//                    pltHeaderRepository.save(pltHeaderEntity);
//                }
//
//            }
//        });
//        return true;
//    }*/
}
