package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.enums.SearchType;
import com.scor.rr.domain.views.VwFacTreaty;
import com.scor.rr.repository.*;
import com.scor.rr.repository.counter.*;
import com.scor.rr.repository.specification.VwFacTreatySpecification;
import com.scor.rr.util.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
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

    @Autowired
    ProjectViewRepository projectViewRepository;

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

    Map<TableNames, BiFunction<String, Pageable, Page>> countMapper = new HashMap<>();


    @PostConstruct
    private void feedCountMapper() {
        countMapper.put(TableNames.CEDANT_CODE, cedantCodeCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.CEDANT_NAME, cedantNameCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.COUNTRY, countryCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
//        countMapper.put(TableNames.TREATY, treatyCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.YEAR, uwyCountRepository::findByLabelIgnoreCaseLikeOrderByLabelDesc);
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


//    public Page<WorkspaceProjection> getWorkspaces(NewWorkspaceFilter filter, int offset, int size) {
//        return new PageImpl<WorkspaceProjection>(
//                contractSearchResultRepository.getContracts(filter, offset, size),
//                PageRequest.of(offset / size, size),
//                contractSearchResultRepository.countContracts(filter)
//        );
//
//    }

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

    public Page<?> countInWorkspace(TableNames table, String keyword, int size) {
        return ofNullable(countMapper.get(table))
                .map(callback -> callback.apply("%" + keyword + "%", PageRequest.of(0, size)))
                .orElseThrow(() -> new RuntimeException("Table parameter not found"));
    }

    public Optional<WorkspaceDetailsDTO> getWorkspaceDetails(String worspaceId, String uwy) {
        List<ContractSearchResult> items = contractSearchResultRepository.findByTreatyidAndUwYear(worspaceId, uwy);
        if (items == null || items.isEmpty())
            return Optional.empty();
        List<String> years = contractSearchResultRepository.findDistinctByWorkSpaceId(worspaceId).map(item -> item.getUwYear()).filter(Objects::nonNull).map(String::valueOf).distinct().sorted().collect(toList());
        final List<ProjectView> projects = projectViewRepository.findByWorkspaceIdAndUwy(worspaceId, Integer.valueOf(uwy));
        List<ProjectView> filtredProjects = projectViewRepository.findFirstByWorkspaceIdAndUwyAndPostInuredFlagIsTrue(worspaceId, Integer.valueOf(uwy))
                .map(postInuredProject -> Stream.concat(asList(postInuredProject).stream(), projects.stream()).collect(toList()))
                .orElse(projects);
        return Optional.of(new WorkspaceDetailsDTO(items, years, filtredProjects));
    }

    public Page<VwFacTreaty> getAllFacTreaties(VwFacTreatyFilter filter, Pageable pageable) {
        return vwFacTreatyRepository.findAll(vwFacTreatySpecification.getFilter(filter), pageable);
    }

    public Page<?> expertModeSearch(ExpertModeFilterRequest request) {
        String resultsQueryString = queryHelper.generateSqlQuery(request.getFilter(), request.getKeyword(), request.getOffset(), request.getSize());
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

    public Object saveSearch(SearchType searchType, List<SearchItem> items, Integer userId) {
        /** @TODO: Make it user Specific **/

        if (searchType.equals(SearchType.FAC)) {
            return this.saveFacSearch(items);
        } else if (searchType.equals(SearchType.TREATY)) {
            return this.saveTreatySearch(items, userId);
        } else {
            throw new RuntimeException("Unsupported Search Type" + searchType);
        }
    }

    private TreatySearch saveTreatySearch(List<SearchItem> items, Integer userId) {

        if( items.isEmpty() ) {

            if(userId != null) {

                TreatySearch treatySearch = new TreatySearch();
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
