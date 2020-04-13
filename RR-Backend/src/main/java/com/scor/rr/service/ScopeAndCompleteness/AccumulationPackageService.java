package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.domain.Response.ScopeAndCompleteness.*;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackage;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageAttachedPLT;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageOverrideSection;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.ScopeAndCompleteness.AccumulationPackageNotFoundException;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageAttachedPLTRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageOverrideSectionRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class AccumulationPackageService {

    @Autowired
    private AccumulationPackageRepository accumulationPackageRepository;
    @Autowired
    private AccumulationPackageAttachedPLTService accumulationPackageAttachedPLTService;
    @Autowired
    private AccumulationPackageOverrideSectionService accumulationPackageOverrideSectionService;


    public List<ScopeAndCompletenessResponse> getScopeOnly(String workspaceName, int uwYear) {

        List<ScopeAndCompletenessResponse> response = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setFieldMatchingEnabled(true);

        List<ExpectedScopeDBResponse> expectedScope = accumulationPackageRepository.getExpectedScopeOnly(workspaceName, uwYear)
                .stream()
                .map(exScope -> mapper.map(exScope, ExpectedScopeDBResponse.class))
                .collect(Collectors.toList());


        if (!expectedScope.isEmpty()) {
            Map<Integer, Map<String, List<ExpectedScopeDBResponse>>> groupedMinimimumGrainMap = getGroupedMinimumMap(expectedScope);
            Map<Integer, Map<String, List<ExpectedScopeDBResponse>>> groupedAccumulationMap = getGroupedAccumulationMap(expectedScope);
            groupedMinimimumGrainMap.forEach((k, v) -> {

                Map<String, List<ExpectedScopeDBResponse>> v2 = groupedAccumulationMap.get(k);
                ScopeAndCompletenessResponse itemResponse = new ScopeAndCompletenessResponse();
                itemResponse.setId(workspaceName + "/ " + k);

                List<RegionPerils> regionPerils = new ArrayList<>();
                List<TargetRaps> targetRaps = new ArrayList<>();
                v.forEach((key, value) -> {

                    RegionPerils regionPeril = new RegionPerils();
                    regionPeril.setId(key);


                    List<InsideObject> insideList = new ArrayList<>();
                    for (ExpectedScopeDBResponse row : value) {
                        InsideObject object = new InsideObject();
                        object.setId(row.getAccumulationRapCode());
                        object.setDescription(row.getAccumulationRapDesc());
                        insideList.add(object);
                    }
                    regionPeril.setTargetRaps(insideList);
                    regionPerils.add(regionPeril);
                });

                v2.forEach((key, value) -> {

                            TargetRaps targetRap = new TargetRaps();
                            targetRap.setId(key);


                            List<InsideObject> insideList = new ArrayList<>();
                            for (ExpectedScopeDBResponse row : value) {
                                InsideObject object = new InsideObject();
                                object.setId(row.getMinimumGrainRegionPerilCode());
                                //object.setDescription(row.getAccumulationRapDesc());
                                insideList.add(object);
                            }
                            targetRap.setRegionPerils(insideList);
                            targetRaps.add(targetRap);
                        }


                );
                itemResponse.setRegionPerils(regionPerils);
                itemResponse.setTargetRaps(targetRaps);
                response.add(itemResponse);

            });
        }
        return response;
    }

    public Map<Integer, List<ExpectedScopeDBResponse>> getContractSections(List<ExpectedScopeDBResponse> expectedScope) {

        Map<Integer, List<ExpectedScopeDBResponse>> containerMap = new TreeMap<>();
        for (ExpectedScopeDBResponse row : expectedScope) {
            List<ExpectedScopeDBResponse> workList = containerMap.get(row.getContractSectionID());
            if (workList == null) {
                List<ExpectedScopeDBResponse> newList = new ArrayList<>();
                newList.add(row);
                containerMap.put(row.getContractSectionID(), newList);
            } else {
                workList.add(row);
                containerMap.put(row.getContractSectionID(), workList);
            }
        }

        return containerMap;
    }

    public Map<Integer, Map<String, List<ExpectedScopeDBResponse>>> getGroupedMinimumMap(List<ExpectedScopeDBResponse> expectedScope) {
        Map<Integer, List<ExpectedScopeDBResponse>> containerMap = getContractSections(expectedScope);
        Map<Integer, Map<String, List<ExpectedScopeDBResponse>>> returnMap = new TreeMap<>();
        containerMap.forEach((k, v) -> {
            Map<String, List<ExpectedScopeDBResponse>> rrmap = groupByMinimumGrain(v);
            returnMap.put(k, rrmap);
        });
        return returnMap;
    }

    public Map<Integer, Map<String, List<ExpectedScopeDBResponse>>> getGroupedAccumulationMap(List<ExpectedScopeDBResponse> expectedScope) {
        Map<Integer, List<ExpectedScopeDBResponse>> containerMap = getContractSections(expectedScope);
        Map<Integer, Map<String, List<ExpectedScopeDBResponse>>> returnMap = new TreeMap<>();
        containerMap.forEach((k, v) -> {
            Map<String, List<ExpectedScopeDBResponse>> rrmap = groupByAccumulationRap(v);
            returnMap.put(k, rrmap);
        });
        return returnMap;
    }

    public Map<String, List<ExpectedScopeDBResponse>> groupByMinimumGrain(List<ExpectedScopeDBResponse> requeteResponse) {
        Map<String, List<ExpectedScopeDBResponse>> returnMap = new TreeMap<>();
        for (ExpectedScopeDBResponse row : requeteResponse) {
            List<ExpectedScopeDBResponse> workList = returnMap.get(row.getMinimumGrainRegionPerilCode());
            if (workList == null) {
                List<ExpectedScopeDBResponse> newList = new ArrayList<>();
                newList.add(row);
                returnMap.put(row.getMinimumGrainRegionPerilCode(), newList);
            } else {
                workList.add(row);
                returnMap.put(row.getMinimumGrainRegionPerilCode(), workList);
            }
        }
        return returnMap;
    }

    public Map<String, List<ExpectedScopeDBResponse>> groupByAccumulationRap(List<ExpectedScopeDBResponse> requeteResponse) {
        Map<String, List<ExpectedScopeDBResponse>> returnMap = new TreeMap<>();
        for (ExpectedScopeDBResponse row : requeteResponse) {
            List<ExpectedScopeDBResponse> workList = returnMap.get(row.getAccumulationRapCode());
            if (workList == null) {
                List<ExpectedScopeDBResponse> newList = new ArrayList<>();
                newList.add(row);
                returnMap.put(row.getAccumulationRapCode(), newList);
            } else {
                workList.add(row);
                returnMap.put(row.getAccumulationRapCode(), workList);
            }
        }
        return returnMap;
    }

    public AccumulationPackageResponse getAccumulationPackageDetails(String workspaceName,int UWYear, long accumulationPackageId) throws RRException{
        AccumulationPackage accumulationPackage = accumulationPackageRepository.findByAccumulationPackageId(accumulationPackageId);
        if(accumulationPackage == null ) throw new AccumulationPackageNotFoundException(accumulationPackageId);

        AccumulationPackageResponse response = new AccumulationPackageResponse();
        response.setScopeObject(getScopeOnly(workspaceName,UWYear));
        response.setAttachedPLTs(accumulationPackageAttachedPLTService.getAttachedPLTs(accumulationPackageId));
        response.setOverriddenSections(accumulationPackageOverrideSectionService.getOverriddenSections(accumulationPackageId));

        return response;


    }



}
