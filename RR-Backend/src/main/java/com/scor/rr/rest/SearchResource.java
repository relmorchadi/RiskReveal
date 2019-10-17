package com.scor.rr.rest;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.views.VwFacTreaty;
import com.scor.rr.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/search")
public class SearchResource {

    @Autowired
    private SearchService searchService;

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
//    Page<ContractSearchResult> searchWorkspace(@RequestBody WorkspaceFilter filter, int size){
    Page<?> globalSearchWorkspace(NewWorkspaceFilter filter, int offset, int size){
        return searchService.globalSearchWorkspaces(filter, offset, size);
    }

//    @PostMapping("workspace")
//    Page<ContractSearchResult> searchWorkspace(@RequestBody WorkspaceFilter filter, int size){
//    Page<WorkspaceProjection> searchWorkspace(@RequestBody NewWorkspaceFilter filter, int offset, int size){
//        return searchService.getWorkspaces(filter, offset,size);
//    }

    @GetMapping("searchcount")
    Page<?> countInWorkspace(@RequestParam TableNames table, @RequestParam String keyword, @RequestParam(defaultValue = "5") int size ){
        return searchService.countInWorkspace(table, keyword, size);
    }

    @GetMapping("worspace/{workspaceId}/{uwy}")
    ResponseEntity<WorkspaceDetailsDTO> getWorkspaceDetails(@PathVariable("workspaceId") String worspaceId, @PathVariable("uwy") String uwy){
        return  searchService.getWorkspaceDetails(worspaceId, uwy)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("fac-treaties")
    Page<VwFacTreaty> getAllFacTreaties(VwFacTreatyFilter filter, Pageable pageable){
        return searchService.getAllFacTreaties(filter, pageable);
    }

    @PostMapping("workspace/expert-mode")
    Page<?> expertModeSearch(@RequestBody ExpertModeFilterRequest request){
        return searchService.expertModeSearch(request);
    }
}
