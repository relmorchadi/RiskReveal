package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.domain.ContractSearchResult;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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
    FacSearchRepository facSearchRepository;
    @Autowired
    FacSearchItemRepository facSearchItemRepository;
    @Autowired
    TreatySearchRepository treatySearchRepository;
    @Autowired
    TreatySearchItemRepository treatySearchItemRepository;

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

    Map<TableNames, BiFunction<String, Pageable, Page>> countMapper = new HashMap<>();


    @PostConstruct
    private void feedCountMapper() {
        countMapper.put(TableNames.CEDANT_CODE, cedantCodeCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.CEDANT_NAME, cedantNameCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.COUNTRY_NAME, countryCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
//        countMapper.put(TableNames.TREATY, treatyCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.UW_YEAR, uwyCountRepository::findByLabelIgnoreCaseLikeOrderByLabelDesc);
        countMapper.put(TableNames.WORKSPACE_ID, workspaceIdCountViewRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.WORKSPACE_NAME, workspaceNameCountViewRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
//        countMapper.put(TableNames.PROGRAM, programRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
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

    public Page<?> globalSearchWorkspaces(NewWorkspaceFilter filter, int offset, int size) {
        String resultsQueryString = queryHelper.generateSqlQuery(filter, offset, size);
        String countQueryString = queryHelper.generateCountQuery(filter);
        Query resultsQuery = entityManager.createNativeQuery(resultsQueryString);
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        List<Object[]> resultList = resultsQuery.getResultList();
        Object total = countQuery.getSingleResult();
        List<ContractSearchResult> contractSearchResult = map(resultList);
        return new PageImpl<>(contractSearchResult, PageRequest.of(offset / size, size), (Integer) total);
    }

    public SearchCountResult countInWorkspace(TableNames table, String keyword, int size) {
        return new SearchCountResult(ofNullable(countMapper.get(table))
                .map(callback -> callback.apply(keyword, PageRequest.of(0, size)))
                .orElseThrow(() -> new RuntimeException("Table parameter not found")), table);
    }

    public WorkspaceDetailsDTO getWorkspaceDetails(String workspaceId, String uwy) {
        List<ContractSearchResult> contracts = contractSearchResultRepository.findByTreatyidAndUwYear(workspaceId, uwy);
        List<Integer> years = contractSearchResultRepository.findDistinctYearsByWorkSpaceId(workspaceId);
        Optional<WorkspaceEntity> wsOpt = workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceId, Integer.valueOf(uwy));
        List<ProjectCardView> projects = wsOpt
                .map(workspace -> projectCardViewRepository.findAllByWorkspaceId(workspace.getWorkspaceId()))
                .orElse(new ArrayList<>());
        if (!CollectionUtils.isEmpty(contracts)) {
            this.recentWorkspaceRepository.toggleRecentWorkspace(workspaceId, Integer.valueOf(uwy), 1);
            Long marketChannel = null;
            if(wsOpt.isPresent()) marketChannel = wsOpt.get().getWorkspaceMarketChannel();
            return buildWorkspaceDetails(
                    contracts,
                    years,
                    projects,
                    workspaceId,
                    uwy,
                    marketChannel
            );
        } else {
            throw new RuntimeException("No corresponding workspace for the Workspace ID / UWY : " + workspaceId + " / " + uwy);
        }
    }


    private WorkspaceDetailsDTO buildWorkspaceDetails(List<ContractSearchResult> contracts, List<Integer> years, List<ProjectCardView> projects, String workspaceId, String uwy, Long marketChannel) {
        ContractSearchResult firstWs = contracts.get(0);
        WorkspaceDetailsDTO detailsDTO = new WorkspaceDetailsDTO(firstWs, marketChannel);
        detailsDTO.setProjects(projects);
        detailsDTO.setTreatySections(contracts);
        detailsDTO.setYears(years);
        detailsDTO.setIsPinned(true);
        detailsDTO.setExpectedRegionPerils(getCount(workspaceEntityRepository.getDistinctRegionPerilsInScopeCount(firstWs.getWorkSpaceId(), firstWs.getUwYear())));
        detailsDTO.setExpectedExposureSummaries(getCount(new ArrayList<>(Arrays.asList())));
        detailsDTO.setQualifiedPLTs(getCount(workspaceEntityRepository.getDistinctRegionPerilsByQualifyingPLTsCount(firstWs.getWorkSpaceId(), firstWs.getUwYear())));
        detailsDTO.setExpectedPublishedForPricing(getCount(workspaceEntityRepository.getDistinctRPByPublishedToPricingQualifiedPLTsCount(firstWs.getWorkSpaceId(), firstWs.getUwYear())));
        detailsDTO.setExpectedPriced(getCount(workspaceEntityRepository.getDistinctRPByPricedQualifiedPLTsCount(firstWs.getWorkSpaceId(), firstWs.getUwYear())));
        detailsDTO.setExpectedAccumulation(getCount(workspaceEntityRepository.getDistinctRPByAccumulatedQualifiedPLTsCount(firstWs.getWorkSpaceId(), firstWs.getUwYear())));
        detailsDTO.setIsFavorite(this.favoriteWorkspaceRepository.existsByWorkspaceContextCodeAndWorkspaceUwYearAndUserId(firstWs.getWorkSpaceId(), firstWs.getUwYear(), 1));
        return detailsDTO;
    }

    private Integer getCount(List<Map<String, Object>> arr) {
        if( arr.size() > 0 ) {
            Map<String, Object> tmp = arr.get(0);
            System.out.println(tmp);
            System.out.println(tmp.get("count"));
            return (Integer) tmp.get("count");

        } else return 0;
    }

    public Page<?> expertModeSearch(ExpertModeFilterRequest request) {

        if (request.getFromSavedSearch() != null) {
            if (!request.getFilter().isEmpty()) {
                Long treatySearchId = request.getFilter().get(0).getTreatySearchId();

                if (treatySearchId != null) {
                    Optional<TreatySearch> treatySearchOpt = this.treatySearchRepository.findById(treatySearchId);

                    if (treatySearchOpt.isPresent()) {
                        TreatySearch treatySearch = treatySearchOpt.get();

                        treatySearch.setCount(treatySearch.getCount() + 1);

                        this.treatySearchRepository.saveAndFlush(treatySearch);
                    }
                }
            }
        }
        String keyword = request.getKeyword().replace("%", "").trim();

        if(!keyword.equals("") || request.getFilter().size() > 0) {
            List<RecentSearch> recentSearches = recentSearchRepository.findByUserIdOrderBySearchDateDesc(1);
            int recentSearchesLength = recentSearches.size();

            if( recentSearchesLength == 7 ) {
                RecentSearch SearchItem = recentSearches.get(recentSearchesLength - 1);
                recentSearchRepository.delete(SearchItem);
            }

            RecentSearch newSearch = new RecentSearch();
            newSearch.setUserId(1);
            recentSearchRepository.save(newSearch);

            List<RecentSearchItem> items= new ArrayList<>();

            if(!keyword.equals("")) {
                RecentSearchItem newSearchItem = new RecentSearchItem();
                newSearchItem.setKey("global search");
                newSearchItem.setOperator("LIKE");
                newSearchItem.setValue(keyword);
                newSearchItem.setRecentSearchId(newSearch.getId());
                items.add(newSearchItem);
            }

            request.getFilter()
                    .forEach( expertModeFilter -> {
                        if(!expertModeFilter.getValue().equals("")) {
                            RecentSearchItem newSearchItem = new RecentSearchItem();
                            newSearchItem.setKey(expertModeFilter.getField());
                            newSearchItem.setOperator(expertModeFilter.getOperator().value);
                            newSearchItem.setValue(expertModeFilter.getValue().replace("%", "").trim());
                            newSearchItem.setRecentSearchId(newSearch.getId());
                            items.add(newSearchItem);
                        }
                    });

            recentSearchItemRepository.saveAll(items);
        }

        String resultsQueryString = queryHelper.generateSqlQuery(request.getFilter(), request.getSort(), request.getKeyword(), request.getOffset(), request.getSize());
        String countQueryString = queryHelper.generateCountQuery(request.getFilter(), request.getKeyword());
        Query resultsQuery = entityManager.createNativeQuery(resultsQueryString);
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        List<Object[]> resultList = resultsQuery.getResultList();
        Object total = countQuery.getSingleResult();
        List<ContractSearchResult> contractSearchResult = map(resultList);
        return new PageImpl<>(contractSearchResult, PageRequest.of(request.getOffset() / request.getSize(), request.getSize()), (Integer) total);
    }

    private List<ContractSearchResult> map(List<Object[]> resultList) {
        return resultList.stream().map((r) ->
                new ContractSearchResult((String) r[0], (String) r[1], (String) r[2], (String) r[3], (String) r[4], (Integer) r[5])
        ).collect(Collectors.toList());
    }

    public Object saveSearch(SavedSearchRequest request) {
        /** @TODO: Make it user Specific **/

        if (request.getSearchType().equals(SearchType.FAC)) {
            return this.saveFacSearch(request.getItems());
        } else if (request.getSearchType().equals(SearchType.TREATY)) {
            return this.saveTreatySearch(request.getItems(), request.getUserId(), request.getLabel());
        } else {
            throw new RuntimeException("Unsupported Search Type" + request.getSearchType());
        }
    }

    private TreatySearch saveTreatySearch(List<SearchItem> items, Integer userId, String label) {
        if (!items.isEmpty()) {
            if (userId != null) {
                TreatySearch treatySearch = new TreatySearch();
                treatySearch.setLabel(label);
                treatySearch.setUserId(userId);
                this.treatySearchRepository.saveAndFlush(treatySearch);
                List<TreatySearchItem> treatySearchItems = items.stream().map(item -> new TreatySearchItem(item, treatySearch.getId())).collect(toList());
                treatySearch.setItems(this.treatySearchItemRepository.saveAll(treatySearchItems));
                return treatySearch;
            } else throw new RuntimeException("No userID was Provided");
        }
        return null;
    }

    private FacSearch saveFacSearch(List<SearchItem> items) {
        //TODO
        FacSearch facSearch = this.facSearchRepository.save(new FacSearch());
        List<FacSearchItem> facSearchItems = items.stream().map(item -> new FacSearchItem(item, facSearch.getId())).collect(toList());
        facSearch.setItems(this.facSearchItemRepository.saveAll(facSearchItems));
        return facSearch;
    }


    public List<?> getSavedSearches(SearchType searchType, Integer userId) {
        if (userId != null) {
            if (searchType.equals(SearchType.FAC))
                return facSearchRepository.findAllByUserId(userId);
            else if (searchType.equals(SearchType.TREATY))
                return treatySearchRepository.findAllByUserId(userId);
            else {
                throw new RuntimeException("Unsupported Search Type" + searchType);
            }
        } else throw new RuntimeException("No userID was Provided");

    }

    public List<?> getMostUsedSavedSearch(SearchType searchType, Integer userId) {
        if (userId != null) {
            if (searchType.equals(SearchType.FAC))
                return facSearchRepository.findTop5ByUserIdOrderByCountDesc(userId);
            else if (searchType.equals(SearchType.TREATY))
                return treatySearchRepository.findTop5ByUserIdOrderByCountDesc(userId);
            else {
                throw new RuntimeException("Unsupported Search Type" + searchType);
            }
        } else throw new RuntimeException("No userID was Provided");
    }

    public List<RecentSearch> getRecentSearch(Integer userId) {
        return recentSearchRepository.findTop5ByUserIdOrderBySearchDateDesc(userId);
    }

    public void deleteSavedSearch(SearchType searchType, Long id) {
        if (searchType.equals(SearchType.FAC)) {
            this.deleteFacSearch(id);
        } else if (searchType.equals(SearchType.TREATY)) {
            this.deleteTreatySearch(id);
        } else {
            throw new RuntimeException("Unsupported Search Type" + searchType);
        }
    }

    private void deleteFacSearch(Long id) {
        if (!facSearchRepository.existsById(id))
            throw new RuntimeException("No available Fac Saved Search with ID " + id);

        facSearchItemRepository.deleteByFacSearchId(id);
        facSearchRepository.deleteById(id);
    }

    private void deleteTreatySearch(Long id) {
        if (!treatySearchRepository.existsById(id))
            throw new RuntimeException("No available TreatyView Saved Search with ID " + id);
        treatySearchItemRepository.deleteByTreatySearchId(id);
        treatySearchRepository.deleteById(id);
    }


}
