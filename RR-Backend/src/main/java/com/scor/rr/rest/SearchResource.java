package com.scor.rr.rest;

import com.scor.rr.domain.*;
import com.scor.rr.domain.entities.Search.ShortCut;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.dto.TargetBuild.SavedSearchRequest;
import com.scor.rr.domain.enums.SearchType;
import com.scor.rr.repository.WorkspaceEntityRepository;
import com.scor.rr.service.SearchService;
import com.scor.rr.service.ShortCutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("api/search")
public class SearchResource {

    @Autowired
    private SearchService searchService;

    @Autowired
    ShortCutService shortCutService;

    @Autowired
    WorkspaceEntityRepository workspaceEntityRepository;


    @GetMapping("treaty")
    Page<TreatyView> searchTreaty(@RequestParam String keyword, int size) {
        return searchService.getTreaty(keyword, size);
    }

    @GetMapping("cedant")
    Page<CedantView> searchCedants(@RequestParam String keyword, int size) {
        return searchService.getCedants(keyword, size);
    }

    @GetMapping("country")
    Page<CountryView> searchCountries(@RequestParam String keyword, int size) {
        return searchService.getCountryPeril(keyword, size);
    }

    @GetMapping("year")
    Page<WorkspaceYears> searchWorkspaceYear(@RequestParam String keyword, int size) {
        return searchService.getWorkspaceYear(keyword, size);
    }

//    @PostMapping("workspace")
//    Page<CATContract> searchWorkspace(@RequestBody WorkspaceFilter filter, int size){
//    Page<WorkspaceProjection> searchWorkspace(@RequestBody NewWorkspaceFilter filter, int offset, int size){
//        return searchService.getWorkspaces(filter, offset,size);
//    }

    @GetMapping("searchcount")
    SearchCountResult countInWorkspace(@RequestParam TreatyTableNames table, @RequestParam String keyword, @RequestParam(defaultValue = "5") int size ){
        return searchService.countInWorkspace(table, keyword, size);
    }

    @PostMapping
    ResponseEntity<?> saveSearch(@RequestBody SavedSearchRequest request) {
        return ResponseEntity.ok(
                searchService.saveSearch(request)
        );
    }

    @GetMapping("saved-search")
    ResponseEntity<?> getSavedSearch(@RequestParam SearchType searchType, @RequestParam Integer userId) {
        return ResponseEntity.ok(
                searchService.getSavedSearches(searchType, userId)
        );
    }

    @GetMapping("recent")
    ResponseEntity<?> getRecentSearch(@RequestParam Integer userId) {
        return ResponseEntity.ok(
                searchService.getRecentSearch(userId)
        );
    }

    @GetMapping("saved-search/most")
    ResponseEntity<?> getMostUsedSavedSearch(@RequestParam SearchType searchType, @RequestParam Integer userId) {
        return ResponseEntity.ok(
                searchService.getMostUsedSavedSearch(searchType, userId)
        );
    }

    @DeleteMapping("saved-search")
    ResponseEntity<?> deleteSavedSearch(@RequestParam SearchType searchType, @RequestParam Long id) {
        try {
            searchService.deleteSavedSearch(searchType, id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PostMapping("workspace/expert-mode")
    Page<?> expertModeSearch(@RequestBody ExpertModeFilterRequest request) {
        return searchService.expertModeSearch(request);
    }

    @GetMapping("shortcuts")
    List<ShortCut> getShortCuts() {
        return this.shortCutService.getShortCuts();
    }

}
