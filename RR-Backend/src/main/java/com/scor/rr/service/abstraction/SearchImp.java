package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.KeyValue;
import com.scor.rr.domain.entities.Search.ShortCut;
import com.scor.rr.repository.ShortCutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchImp {

    Map<String, Map<String, ShortCut>> shortCutsMap;

    Map<String, List<String>> columns;

    @Autowired
    ShortCutRepository shortCutRepository;

    @PostConstruct
    void init() {
        this.shortCutsMap = new HashMap<String, Map<String, ShortCut>>();
        Map<String, ShortCut> facShortCuts = new HashMap<String, ShortCut>();
        shortCutRepository.findAllByType("FAC").forEach(shortCut -> {
            facShortCuts.put(shortCut.getMappingTable(), shortCut);
        });
        this.shortCutsMap.put("FAC", facShortCuts);

        Map<String, ShortCut> ttyShortCuts = new HashMap<String, ShortCut>();

        shortCutRepository.findAllByType("TTY").forEach(shortCut -> {
            ttyShortCuts.put(shortCut.getMappingTable(), shortCut);
        });
        this.shortCutsMap.put("TTY", ttyShortCuts);
    }

    String generateWhere(List<KeyValue> filters, Map<String, String> shortCuts, String keyword) {
        return filters.stream().map(filter -> " lower(c." + filter.getKey() + ") like '%" + filter.getValue() + "%' ").collect(Collectors.joining(","));
    }

    String generateSelect(String type) {
        String source;

        switch (type) {
            case "TTY":
                source = "ContractSearch";
                break;
            case "FAC":
                source = "FacContractSearch";
                break;
                default:
                    source = "ContractSearch";
        }

        return "SELECT * FROM dbo." + source + " c ";
    }


}
