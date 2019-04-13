package com.rr.riskreveal.service;

import com.rr.riskreveal.domain.*;
import com.rr.riskreveal.domain.dto.WorkspaceFilter;
import com.rr.riskreveal.repository.*;
import com.rr.riskreveal.repository.counter.CedantCountRepository;
import com.rr.riskreveal.repository.counter.CountryCountRepository;
import com.rr.riskreveal.repository.counter.TreatyCountRepository;
import com.rr.riskreveal.repository.counter.UwyCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static com.rr.riskreveal.repository.specification.ContractSearchResultSpecification.filter;
import static com.rr.riskreveal.repository.specification.ContractSearchResultSpecification.filterGlobal;
import static java.util.Optional.ofNullable;


@Service
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
    private CedantCountRepository cedantCountRepository;
    @Autowired
    private CountryCountRepository countryCountRepository;
    @Autowired
    private TreatyCountRepository treatyCountRepository;
    @Autowired
    private UwyCountRepository uwyCountRepository;


    Map<TableNames, BiFunction<String, Pageable, Page>> countMapper = new HashMap<>();


    @PostConstruct
    private void feedCountMapper() {
        countMapper.put(TableNames.CEDANT, cedantCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put( TableNames.COUNTRY, countryCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.TREATY, treatyCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
        countMapper.put(TableNames.YEAR, uwyCountRepository::findByLabelIgnoreCaseLikeOrderByCountOccurDesc);
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

    public Page<ContractSearchResult> getWorkspaces(WorkspaceFilter filter, int size) {
        if (filter.isEmpty())
            return contractSearchResultRepository.findAll(PageRequest.of(0, size));
        else if (filter.isGlobalSearch())
            return contractSearchResultRepository.findAll(filterGlobal(filter.getGlobalKeyword()), PageRequest.of(0, size));
        else
            return contractSearchResultRepository.findAll(filter(filter), PageRequest.of(0, size));
    }

    public Page<?> countInWorkspace(TableNames table, String keyword, int size) {
        return ofNullable(countMapper.get(table))
                .map(callback -> callback.apply("%" + keyword + "%", PageRequest.of(0, size)))
                .orElseThrow(() -> new RuntimeException("Table parameter not found"));
    }

}
