package com.rr.riskreveal.service;

import com.rr.riskreveal.domain.*;
import com.rr.riskreveal.domain.dto.NewWorkspaceFilter;
import com.rr.riskreveal.domain.dto.WorkspaceDetailsDTO;
import com.rr.riskreveal.repository.*;
import com.rr.riskreveal.repository.counter.*;
import com.rr.riskreveal.repository.specification.WorkspaceViewSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.BiFunction;

import static com.rr.riskreveal.repository.specification.ContractSearchResultSpecification.filterByWorkspaceIdAndUwy;
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

    @PersistenceContext
    EntityManager entityManager;


    Map<TableNames, BiFunction<String, Pageable, Page>> countMapper = new HashMap<>();


    @PostConstruct
    private void feedCountMapper() {
        countMapper.put(TableNames.CEDANT_CODE, cedantCodeCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.CEDANT_NAME, cedantNameCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.COUNTRY, countryCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
//        countMapper.put(TableNames.TREATY, treatyCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.YEAR, uwyCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
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


    public Page<WorkspaceProjection> getWorkspaces(NewWorkspaceFilter filter, int offset, int size) {
//    public Page<ContractSearchResult> getWorkspaces(WorkspaceFilter filter, int size) {
//        if (filter.isEmpty())
//            return contractSearchResultRepository.findAll(PageRequest.of(0, size));
//        else if (filter.isGlobalSearch())
//            return contractSearchResultRepository.findAll(filterGlobal(filter.getGlobalKeyword()), PageRequest.of(0, size));
//        else
//            return contractSearchResultRepository.findAll(filter(filter), PageRequest.of(0, size));
//        return workspaceViewRepository.findAll(WorkspaceViewSpecification.filter(filter), PageRequest.of(0, size));

//        return contractSearchResultRepository.customQuery(PageRequest.of(0, size));
        return new PageImpl<WorkspaceProjection>(
                contractSearchResultRepository.getContracts(filter, offset,size),
                PageRequest.of(offset/size, size),
                contractSearchResultRepository.countContracts(filter)
        );

//        return contractSearchResultRepository.findAll(selectAndGroupFields(),PageRequest.of(0,size));

//        return entityManager.createNativeQuery("EXEC filterContracts '10', 'ax', '2015', NULL, NULL, NULL")
//                .setParameter("workspaceId", null)
//                .setParameter("workspaceName", null)
//                .setParameter("year", null)
//                .setParameter("cedantCode", null)
//                .setParameter("cedantName", null)
//                .setParameter("countryName", null)
//                .getResultList();
//        Page<WorkspaceProjection> workspaceProjectionPage= new PageImpl<WorkspaceProjection>(
//                ctrP.getContent().stream().map(ctr -> new SpelAwareProxyProjectionFactory().createProjection(WorkspaceProjection.class, ctr)).collect(toList()),
//                ctrP.getPageable(),
//                ctrP.getTotalElements());
//        return workspaceProjectionPage;

    }

    public Page<WorkspaceProjection> globalSearchWorkspaces(String keyword, int size) {
        return contractSearchResultRepository.globalSearch("%"+keyword+"%", PageRequest.of(0, size));
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
        return Optional.of(new WorkspaceDetailsDTO(items, years));
    }

}
