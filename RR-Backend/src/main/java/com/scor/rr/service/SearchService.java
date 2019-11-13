package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.domain.TargetBuild.Project;
import com.scor.rr.domain.TargetBuild.Search.*;
import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.dto.TargetBuild.SavedSearchRequest;
import com.scor.rr.domain.enums.SearchType;
import com.scor.rr.domain.views.VwFacTreaty;
import com.scor.rr.repository.*;
import com.scor.rr.repository.TargetBuild.WorkspacePoPin.RecentWorkspaceRepository;
import com.scor.rr.repository.TargetBuild.WorkspaceRepository;
import com.scor.rr.repository.counter.*;
import com.scor.rr.repository.specification.VwFacTreatySpecification;
import com.scor.rr.util.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
import static java.util.stream.Collectors.reducing;


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
    WorkspaceRepository workspaceRepository;

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    QueryHelper queryHelper;
    @Autowired
    VwFacTreatyRepository vwFacTreatyRepository;
    @Autowired
    VwFacTreatySpecification vwFacTreatySpecification;

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

    Map<TableNames, BiFunction<String, Pageable, Page>> countMapper = new HashMap<>();


    @PostConstruct
    private void feedCountMapper() {
        countMapper.put(TableNames.CEDANT_CODE, cedantCodeCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.CEDANT_NAME, cedantNameCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.COUNTRY, countryCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
//        countMapper.put(TableNames.TREATY, treatyCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.UW_YEAR, uwyCountRepository::findByLabelIgnoreCaseLikeOrderByLabelDesc);
        countMapper.put(TableNames.WORKSPACE_ID, workspaceIdCountViewRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.WORKSPACE_NAME, workspaceNameCountViewRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
//        countMapper.put(TableNames.PROGRAM, programRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
    }

    public Page<Treaty> getTreaty(String keyword, int size) {
        return treatyRepository.findByLabelIgnoreCaseLikeOrderByLabel("%" + keyword + "%", PageRequest.of(0, size));
    }

    public Page<Cedant> getCedants(String keyword, int size) {
        return cedantRepository.findByLabelIgnoreCaseLikeOrderByLabel("%" + keyword + "%", PageRequest.of(0, size));
    }


    public Page<Country> getCountryPeril(String keyword, int size) {
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
                .map(callback -> callback.apply( keyword , PageRequest.of(0, size)))
                .orElseThrow(() -> new RuntimeException("Table parameter not found")), table);
    }

    public Workspace getWorkspace(String workspaceId, String uwy) {
        return workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceId, Integer.valueOf(uwy))
                .orElseGet(() ->
                        contractSearchResultRepository.findByWorkspaceIdAndUwYear(workspaceId, Integer.valueOf(uwy))
                                .map(targetContract -> new Workspace(targetContract))
                                .orElseThrow(() -> new RuntimeException("No available Workspace with ID : " + workspaceId + "-" + Integer.valueOf(uwy)))
                );
    }

    public WorkspaceDetailsDTO getWorkspaceDetails(String workspaceId, String uwy) {
        List<ContractSearchResult> contracts = contractSearchResultRepository.findByTreatyidAndUwYear(workspaceId, uwy);
        List<Integer> years = contractSearchResultRepository.findDistinctYearsByWorkSpaceId(workspaceId);
        List<Project> projects = workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceId, Integer.valueOf(uwy)).map(Workspace::getProjects)
                .orElse(new ArrayList<>());
        return ofNullable(contracts.get(0))
                .map(firstWs -> {
                    Optional<Workspace> wsOpt = Optional.of(this.getWorkspace(workspaceId, uwy));

                    Workspace ws = wsOpt.get();

                    this.recentWorkspaceRepository.setRecentWorkspace(workspaceId, Integer.valueOf(uwy), 1);

                    return new WorkspaceDetailsDTO(firstWs, contracts, years, projects);
                })
                .orElseThrow(() -> new RuntimeException("No corresponding workspace for the Workspace ID / UWY : " + workspaceId + " / " + uwy));

    }

    public Page<VwFacTreaty> getAllFacTreaties(VwFacTreatyFilter filter, Pageable pageable) {
        return vwFacTreatyRepository.findAll(vwFacTreatySpecification.getFilter(filter), pageable);
    }

    public Page<?> expertModeSearch(ExpertModeFilterRequest request) {

        if(request.getFromSavedSearch() != null) {
            if(!request.getFilter().isEmpty()) {
                Long treatySearchId = request.getFilter().get(0).getTreatySearchId();

                if(treatySearchId != null) {
                    Optional<TreatySearch> treatySearchOpt = this.treatySearchRepository.findById(treatySearchId);

                    if(treatySearchOpt.isPresent()) {
                        TreatySearch treatySearch = treatySearchOpt.get();

                        treatySearch.setCount(treatySearch.getCount() + 1);

                        this.treatySearchRepository.saveAndFlush(treatySearch);
                    }
                }
            }
        }

        String resultsQueryString = queryHelper.generateSqlQuery(request.getFilter(),request.getSort(), request.getKeyword(), request.getOffset(), request.getSize());
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

        if( !items.isEmpty() ) {

            if(userId != null) {

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
        FacSearch facSearch= this.facSearchRepository.save(new FacSearch());
        List<FacSearchItem> facSearchItems= items.stream().map(item -> new FacSearchItem(item, facSearch.getId())).collect(toList());
        facSearch.setItems(this.facSearchItemRepository.saveAll(facSearchItems));
        return facSearch;
    }


    public List<?> getSavedSearches(SearchType searchType, Integer userId) {

        if( userId != null ) {
            if(searchType.equals(SearchType.FAC))
                return facSearchRepository.findAllByUserId(userId);
            else if (searchType.equals(SearchType.TREATY))
                return treatySearchRepository.findAllByUserId(userId);
            else {
                throw new RuntimeException("Unsupported Search Type" + searchType);
            }
        } else throw new RuntimeException("No userID was Provided");

    }

    public List<?> getMostUsedSavedSearch(SearchType searchType, Integer userId) {
        if( userId != null ) {
            if(searchType.equals(SearchType.FAC))
                return facSearchRepository.findTop5ByUserIdOrderByCountDesc(userId);
            else if (searchType.equals(SearchType.TREATY))
                return treatySearchRepository.findTop5ByUserIdOrderByCountDesc(userId);
            else {
                throw new RuntimeException("Unsupported Search Type" + searchType);
            }
        } else throw new RuntimeException("No userID was Provided");
    }

    public void deleteSavedSearch(SearchType searchType, Long id) {
        if(searchType.equals(SearchType.FAC)){
            this.deleteFacSearch(id);
        }
        else if (searchType.equals(SearchType.TREATY)){
            this.deleteTreatySearch(id);
        }
        else {
            throw new RuntimeException("Unsupported Search Type" + searchType);
        }
    }

    private void deleteFacSearch(Long id) {
        if(!facSearchRepository.existsById(id))
            throw new RuntimeException("No available Fac Saved Search with ID " + id);

        facSearchItemRepository.deleteByFacSearchId(id);
        facSearchRepository.deleteById(id);
    }

    private void deleteTreatySearch(Long id) {
        if(!treatySearchRepository.existsById(id))
            throw new RuntimeException("No available Treaty Saved Search with ID " + id);
        treatySearchItemRepository.deleteByTreatySearchId(id);
        treatySearchRepository.deleteById(id);
    }


}
