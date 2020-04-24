package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.dto.TargetBuild.FacWorkspaceDTO;
import com.scor.rr.domain.dto.TargetBuild.TreatyWorkspaceDTO;
import com.scor.rr.domain.entities.FacContractCurrency;
import com.scor.rr.domain.entities.FacContractSearchResult;
import com.scor.rr.domain.entities.Project.ProjectCardView;
import com.scor.rr.domain.entities.Search.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.dto.TargetBuild.SavedSearchRequest;
import com.scor.rr.domain.enums.SearchType;
import com.scor.rr.repository.*;
import com.scor.rr.repository.Project.ProjectCardViewRepository;
import com.scor.rr.repository.Search.*;
import com.scor.rr.repository.WorkspacePoPin.FavoriteWorkspaceRepository;
import com.scor.rr.repository.WorkspacePoPin.RecentWorkspaceRepository;
import com.scor.rr.repository.counter.*;
import com.scor.rr.util.QueryHelper;
import com.scor.rr.util.SearchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;


@Service
@Transactional
public class SearchService {

    @Autowired
    private TreatyRepository treatyRepository;
    @Autowired
    private CedantRepository cedantRepository;
    @Autowired
    private CountryPerilRepository countryPerilRepository;
    @Autowired
    private WorkspaceYearsRepository workspaceYearsRepository;

    @Autowired
    private ContractSearchResultRepository contractSearchResultRepository;

    @Autowired
    private CedantCodeCountRepository cedantCodeCountRepository;
    @Autowired
    private CedantNameCountRepository cedantNameCountRepository;
    @Autowired
    private CountryCountRepository countryCountRepository;
    @Autowired
    private TreatyCountRepository treatyCountRepository;
    @Autowired
    private UwyCountRepository uwyCountRepository;

    @Autowired
    private WorkspaceViewRepository workspaceViewRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    WorkspaceIdCountViewRepository workspaceIdCountViewRepository;

    @Autowired
    WorkspaceNameCountViewRepository workspaceNameCountViewRepository;

//    @Autowired
//    ProjectViewRepository projectViewRepository;

    @Autowired
    WorkspaceEntityRepository workspaceEntityRepository;

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    QueryHelper queryHelper;
    @Autowired
    SearchQuery searchQuery;

    @Autowired
    SavedSearchRepository savedSearchRepository;
    @Autowired
    SavedSearchItemRepository savedSearchItemRepository;

    @Autowired
    RecentWorkspaceRepository recentWorkspaceRepository;

    @Autowired
    ProjectCardViewRepository projectCardViewRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    FavoriteWorkspaceRepository favoriteWorkspaceRepository;

    @Autowired
    RecentSearchRepository recentSearchRepository;

    @Autowired
    RecentSearchItemRepository recentSearchItemRepository;

    @Autowired
    CarDivisionsRepository carDivisionsRepository;

    @Autowired
    FacContractCurrencyRepository facContractCurrencyRepository;

    @Autowired
    FacSearchQuery facSearchQuery;

    Map<TreatyTableNames, BiFunction<String, Pageable, Page>> countMapper = new HashMap<>();
    Map<FacTableNames, BiFunction<String, Pageable, Page>> facSearchCountMapper = new HashMap();

    //Fac Search Count Repos

    @Autowired
    FacClientCountRepository facClientCountRepository;

    @Autowired
    FacUWYearCounRepository facUWYearCounRepository;

    @Autowired
    FacContractCodeCounRepository facContractCodeCounRepository;

    @Autowired
    FacContractNameCountRepository facContractNameCountRepository;

    @Autowired
    FacUwAnalysisCountRepository facUwAnalysisCountRepository;

    @Autowired
    FacCARequestIdCountRepository facCARequestIdCountRepository;

    @Autowired
    FacCARStatusCountRepository facCARStatusCountRepository;

    @Autowired
    FacAssignedToCountRepository facAssignedToCountRepository;

    @Autowired
    FacPltCountRepository facPltCountRepository;

    @Autowired
    FacProjectIdCountRepository facProjectIdCountRepository;

    @Autowired
    FacProjectNameCountRepository facProjectNameCountRepository;

    @Autowired
    WorkspaceService workspaceService;

    @PostConstruct
    private void feedCountMapper() {
        //TREATY

        countMapper.put(TreatyTableNames.CLIENT_CODE, cedantCodeCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TreatyTableNames.CLIENT_NAME, cedantNameCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TreatyTableNames.COUNTRY_NAME, countryCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TreatyTableNames.UW_YEAR, uwyCountRepository::findByLabelIgnoreCaseLikeOrderByLabelDesc);
        countMapper.put(TreatyTableNames.CONTRACT_CODE, workspaceIdCountViewRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TreatyTableNames.CONTRACT_NAME, workspaceNameCountViewRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);

        //FAC

        facSearchCountMapper.put(FacTableNames.CLIENT_CODE, facClientCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.UW_YEAR, facUWYearCounRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.CONTRACT_CODE, facContractCodeCounRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.CONTRACT_NAME, facContractNameCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.UW_ANALYSIS, facUwAnalysisCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.CAR_ID, facCARequestIdCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.CAR_STATUS, facCARStatusCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.USR, facAssignedToCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.PLT, facPltCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.PROJECT_ID, facProjectIdCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        facSearchCountMapper.put(FacTableNames.PROJECT_NAME, facProjectNameCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
    }

    public Page<TreatyView> getTreaty(String keyword, int size) {
        return treatyRepository.findByLabelIgnoreCaseLikeOrderByLabel("%" + keyword + "%", PageRequest.of(0, size));
    }

    public Page<CedantView> getCedants(String keyword, int size) {
        return cedantRepository.findByLabelIgnoreCaseLikeOrderByLabel("%" + keyword + "%", PageRequest.of(0, size));
    }


    public Page<CountryView> getCountryPeril(String keyword, int size) {
        return countryPerilRepository.findByLabelIgnoreCaseLikeOrderByLabel("%" + keyword + "%", PageRequest.of(0, size));
    }

    public Page<WorkspaceYears> getWorkspaceYear(String keyword, int size) {
        return workspaceYearsRepository.findByLabelLikeOrderByLabel("%" + keyword + "%", PageRequest.of(0, size));
    }

    public Object getWorkspaceDetails(String workspaceId, String uwy, String wsType) {

        if("TTY".equals(wsType)) return loadTreatyWorkspace(workspaceId, uwy, false, null);
        if("FAC".equals(wsType)) return loadFacWorkspace(workspaceId, uwy, false, null);

        if("".equals(wsType)) {
            List<ContractSearchResult> contracts = contractSearchResultRepository.findByTreatyidAndUwYear(workspaceId, uwy);
            if(!CollectionUtils.isEmpty(contracts)) {
                return this.loadTreatyWorkspace(workspaceId, uwy, true, contracts);
            }

            Optional<WorkspaceEntity> facContract = workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceId, Integer.valueOf(uwy));

            if(facContract.isPresent()) {
                return this.loadFacWorkspace(workspaceId, uwy, true, facContract);
            }
        }

        return null;
    }

    TreatyWorkspaceDTO loadTreatyWorkspace(String workspaceId, String uwy, boolean alreadyChecked, List<ContractSearchResult> ct) {
        List<ContractSearchResult> contracts = !alreadyChecked ? contractSearchResultRepository.findByTreatyidAndUwYear(workspaceId, uwy) : ct;
        List<Integer> years = contractSearchResultRepository.findDistinctYearsByWorkSpaceId(workspaceId);
        Optional<WorkspaceEntity> wsOpt = workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceId, Integer.valueOf(uwy));
        List<ProjectCardView> projects = wsOpt
                .map(workspace -> projectCardViewRepository.findAllByWorkspaceId(workspace.getWorkspaceId()))
                .orElse(new ArrayList<>());
        if (!CollectionUtils.isEmpty(contracts)) {
            UserRrEntity user = ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            this.recentWorkspaceRepository.toggleRecentWorkspace(workspaceId, Integer.valueOf(uwy), user.getUserId());
            return buildTtyWS(
                    contracts,
                    years,
                    projects,
                    workspaceId,
                    uwy
            );
        } else {
            throw new RuntimeException("No corresponding workspace for the Workspace ID / UWY : " + workspaceId + " / " + uwy);
        }
    }

    FacWorkspaceDTO loadFacWorkspace(String workspaceId, String uwy, boolean alreadyChecked, Optional<WorkspaceEntity> ws) {
        Optional<WorkspaceEntity> wsOpt = !alreadyChecked ? workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceId, Integer.valueOf(uwy)) : ws;
        List<ProjectCardView> projects = wsOpt
                .map(workspace -> projectCardViewRepository.findAllByWorkspaceId(workspace.getWorkspaceId()))
                .orElse(new ArrayList<>());
        if (wsOpt.isPresent()) {
            Optional<FacContractCurrency> currency = this.facContractCurrencyRepository.findById(wsOpt.get().getWorkspaceId());
            UserRrEntity user = ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            this.recentWorkspaceRepository.toggleRecentWorkspace(workspaceId, Integer.valueOf(uwy), user.getUserId());
            return buildFacWS(
                    wsOpt.get(),
                    projects,
                    workspaceId,
                    uwy,
                    currency.isPresent() ? currency.get().getCurrency() : null
            );
        } else {
            throw new RuntimeException("No corresponding workspace for the Workspace ID / UWY : " + workspaceId + " / " + uwy);
        }
    }


    private TreatyWorkspaceDTO buildTtyWS(
            List<ContractSearchResult> contracts,
            List<Integer> years,
            List<ProjectCardView> projects,
            String workspaceId,
            String uwy) {
        TreatyWorkspaceDTO detailsDTO;
        ContractSearchResult firstWs = contracts.get(0);
        detailsDTO = new TreatyWorkspaceDTO(firstWs, "TTY");
        detailsDTO.setId(workspaceId);
        detailsDTO.setProjects(projects);
        detailsDTO.setTreatySections(contracts);
        detailsDTO.setYear(Integer.parseInt(uwy));
        detailsDTO.setYears(years);
        detailsDTO.setIsPinned(true);
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        detailsDTO.getCount(this.workspaceEntityRepository.getWorkspaceHeaderStatistics(workspaceId, Integer.parseInt(uwy), user.getUserId()));

        return detailsDTO;
    }

    private FacWorkspaceDTO buildFacWS(WorkspaceEntity ws,
                                       List<ProjectCardView> projects,
                                       String workspaceId,
                                       String uwy,
                                       String currency) {
        FacWorkspaceDTO detailsDTO= new FacWorkspaceDTO(ws);
        detailsDTO.setProjects(projects.stream().map(projectCardView -> {
            projectCardView.setDivisions(carDivisionsRepository.findAllDivisions(projectCardView.getCarRequestId()));
            return projectCardView;
        }).collect(Collectors.toList()));
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        detailsDTO.getCount(this.workspaceEntityRepository.getWorkspaceHeaderStatistics(workspaceId, Integer.parseInt(uwy), user.getUserId()));

        detailsDTO.setCurrency(currency);
        detailsDTO.setYears(Arrays.asList(Integer.valueOf(uwy)));


        return detailsDTO;
    }

    public Page<?> expertModeSearch(ExpertModeFilterRequest request) {
         if(request.getType().equals(SearchType.TREATY)) {
            return this.treatyContractSearch(request);
        } else if(request.getType().equals(SearchType.FAC)) {
            return this.facContractSearch(request);
        } else {
            return null;
        }

    }

    Page<?> facContractSearch(ExpertModeFilterRequest request) {
        this.increaseSavedSearchCount(request);
        this.saveRecentExpertModeSearch(request);

        String resultsQueryString = facSearchQuery.generateSqlQuery(request.getFilter(), request.getSort(), request.getKeyword(), request.getOffset(), request.getSize());
        String countQueryString = facSearchQuery.generateCountQuery(request.getFilter(), request.getKeyword());
        Query resultsQuery = entityManager.createNativeQuery(resultsQueryString);
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        List<Object[]> resultList = resultsQuery.getResultList();
        Object total = countQuery.getSingleResult();
        List<FacContractSearchResult> result = mapFacContract(resultList);
        return new PageImpl<>(result, PageRequest.of(request.getOffset() / request.getSize(), request.getSize()), (Integer) total);
    }

    public SearchCountResult facSearchCount(FacTableNames table, String keyword, int size) {
        return new SearchCountResult<FacTableNames>(ofNullable(facSearchCountMapper.get(table))
                .map(callback -> callback.apply(keyword, PageRequest.of(0, size)))
                .orElseThrow(() -> new RuntimeException("Table parameter not found")), table);
    }

    Page<?> treatyContractSearch(ExpertModeFilterRequest request) {
        this.increaseSavedSearchCount(request);
        this.saveRecentExpertModeSearch(request);

        String resultsQueryString = queryHelper.generateSqlQuery(request.getFilter(), request.getSort(), request.getKeyword(), request.getOffset(), request.getSize());
        String countQueryString = queryHelper.generateCountQuery(request.getFilter(), request.getKeyword());
        Query resultsQuery = entityManager.createNativeQuery(resultsQueryString);
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        List<Object[]> resultList = resultsQuery.getResultList();
        Object total = countQuery.getSingleResult();
        List<ContractSearchResult> contractSearchResult = map(resultList);
        return new PageImpl<>(contractSearchResult, PageRequest.of(request.getOffset() / request.getSize(), request.getSize()), (Integer) total);
    }

    public SearchCountResult treatySearchCount(TreatyTableNames table, String keyword, int size) {
        return new SearchCountResult<TreatyTableNames>(ofNullable(countMapper.get(table))
                .map(callback -> callback.apply(keyword, PageRequest.of(0, size)))
                .orElseThrow(() -> new RuntimeException("Table parameter not found")), table);
    }

    private List<ContractSearchResult> map(List<Object[]> resultList) {
        return resultList.stream().map((r) ->
                new ContractSearchResult((String) r[0], (String) r[1], (String) r[2], (String) r[3], (String) r[4], (Integer) r[5])
        ).collect(Collectors.toList());
    }

    private List<FacContractSearchResult> mapFacContract(List<Object[]> resultList) {
        return resultList.stream().map(
                (r) ->
                new FacContractSearchResult((String) r[0], (Integer) r[1], (String) r[2], (String) r[3], (String) r[4], (String) r[5], (String) r[6], (BigInteger) r[7])
        ).collect(Collectors.toList());
    }

    public SavedSearch saveSearch(SavedSearchRequest request) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        SavedSearch savedSearch = new SavedSearch();
        savedSearch.setLabel(request.getLabel());
        savedSearch.setUserId(user.getUserId());
        savedSearch.setType(request.getSearchType().toString());
        this.savedSearchRepository.saveAndFlush(savedSearch);
        List<SavedSearchItem> savedSearchItems = request.getItems().stream().map(item -> new SavedSearchItem(item, savedSearch.getSavedSearchId())).collect(toList());
        savedSearch.setItems(this.savedSearchItemRepository.saveAll(savedSearchItems));
        return savedSearch;
    }


    public List<?> getSavedSearches(SearchType searchType) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return this.savedSearchRepository.findAllByUserIdAndTypeOrderBySavedDateDesc(user.getUserId(), searchType.toString());
    }

    public List<?> getMostUsedSavedSearch(SearchType searchType) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return this.savedSearchRepository.findTop5ByUserIdAndTypeOrderByCountDescSavedDateDesc(user.getUserId(), searchType.toString());
    }

    public List<RecentSearch> getRecentSearch(SearchType type) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return recentSearchRepository.findTop5ByUserIdAndTypeOrderBySearchDateDesc(user.getUserId(), type.toString());
    }

    public void deleteSavedSearch(Long id) {
        this.savedSearchRepository.deleteById(id);
    }

    private void increaseSavedSearchCount(ExpertModeFilterRequest request) {
        if(request.getFromSavedSearch() != null && request.getFromSavedSearch()) {
            Long savedSearchId = request.getFilter().get(0).getSearchId();

            if (savedSearchId != null) {
                Optional<SavedSearch> treatySearchOpt = this.savedSearchRepository.findById(savedSearchId);

                if (treatySearchOpt.isPresent()) {
                    SavedSearch savedSearch = treatySearchOpt.get();

                    savedSearch.setCount(savedSearch.getCount() + 1);

                    this.savedSearchRepository.saveAndFlush(savedSearch);
                }
            }
        }
    }

    private void saveRecentExpertModeSearch(ExpertModeFilterRequest request) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        String keyword;
        keyword = Optional.of(request.getKeyword()).orElse("").replace("%", "").trim();

        if(!keyword.equals("") || request.getFilter().size() > 0) {
            List<RecentSearch> recentSearches = recentSearchRepository.findByUserIdAndTypeOrderBySearchDateDesc(user.getUserId(), request.getType().toString());
            int recentSearchesLength = recentSearches.size();

            if( recentSearchesLength == 7 ) {
                RecentSearch SearchItem = recentSearches.get(recentSearchesLength - 1);
                recentSearchRepository.delete(SearchItem);
            }
            RecentSearch newSearch = new RecentSearch();
            newSearch.setUserId(user.getUserId());
            recentSearchRepository.save(newSearch);

            List<RecentSearchItem> items= new ArrayList<>();

            if(!keyword.equals("")) {
                RecentSearchItem newSearchItem = new RecentSearchItem();
                newSearchItem.setKeyword("global search");
                newSearchItem.setOperator("LIKE");
                newSearchItem.setValue(keyword);
                newSearchItem.setRecentSearchId(newSearch.getRecentSearchId());
                items.add(newSearchItem);
            }

            request.getFilter()
                    .forEach( expertModeFilter -> {
                        if(!expertModeFilter.getValue().equals("")) {
                            RecentSearchItem newSearchItem = new RecentSearchItem();
                            newSearchItem.setKeyword(expertModeFilter.getField());
                            newSearchItem.setOperator(expertModeFilter.getOperator().value);
                            newSearchItem.setValue(expertModeFilter.getValue().replace("%", "").trim());
                            newSearchItem.setRecentSearchId(newSearch.getRecentSearchId());
                            items.add(newSearchItem);
                        }
                    });

            recentSearchItemRepository.saveAll(items);
        }
    }

}
