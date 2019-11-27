package com.scor.rr.rest;

import com.scor.rr.domain.*;
import com.scor.rr.domain.TargetBuild.Search.ShortCut;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.dto.TargetBuild.SavedSearchRequest;
import com.scor.rr.domain.enums.SearchType;
import com.scor.rr.service.SearchService;
import com.scor.rr.service.TargetBuild.ShortCutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("treaty")
    Page<Treaty> searchTreaty(@RequestParam String keyword, int size) {
        return searchService.getTreaty(keyword, size);
    }

    @GetMapping("cedant")
    Page<Cedant> searchCedants(@RequestParam String keyword, int size) {
        return searchService.getCedants(keyword, size);
    }

    @GetMapping("country")
    Page<Country> searchCountries(@RequestParam String keyword, int size) {
        return searchService.getCountryPeril(keyword, size);
    }

    @GetMapping("year")
    Page<WorkspaceYears> searchWorkspaceYear(@RequestParam String keyword, int size) {
        return searchService.getWorkspaceYear(keyword, size);
    }

    @GetMapping("workspace")
//    Page<CATContract> searchWorkspace(@RequestBody WorkspaceFilter filter, int size){
    Page<?> globalSearchWorkspace(NewWorkspaceFilter filter, int offset, int size) {
        return searchService.globalSearchWorkspaces(filter, offset, size);
    }

//    @PostMapping("workspace")
//    Page<CATContract> searchWorkspace(@RequestBody WorkspaceFilter filter, int size){
//    Page<WorkspaceProjection> searchWorkspace(@RequestBody NewWorkspaceFilter filter, int offset, int size){
//        return searchService.getWorkspaces(filter, offset,size);
//    }

    @GetMapping("searchcount")
    SearchCountResult countInWorkspace(@RequestParam TableNames table, @RequestParam String keyword, @RequestParam(defaultValue = "5") int size ){
        return searchService.countInWorkspace(table, keyword, size);
    }

    @GetMapping("worspace/{workspaceId}/{uwy}")
    WorkspaceDetailsDTO getWorkspaceDetails(@PathVariable("workspaceId") String workspaceId, @PathVariable("uwy") String uwy){
        return  searchService.getWorkspaceDetails(workspaceId, uwy);
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

    @GetMapping("saved-search/most")
    ResponseEntity<?> getMostUsedSavedSearch(@RequestParam SearchType searchType, @RequestParam Integer userId) {
        return ResponseEntity.ok(
                searchService.getMostUsedSavedSearch(searchType, userId)
        );
    }

    @DeleteMapping
    ResponseEntity<?> deleteSavedSearch(@RequestParam SearchType searchType, @RequestParam Long id) {
        searchService.deleteSavedSearch(searchType, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("fac-treaties")
    Page<VwFacTreaty> getAllFacTreaties(VwFacTreatyFilter filter, Pageable pageable) {
        return searchService.getAllFacTreaties(filter, pageable);
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
