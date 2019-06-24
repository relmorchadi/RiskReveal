package com.scor.rr.rest;

import com.scor.rr.domain.UserTag;
import com.scor.rr.domain.dto.AssignPltsRequest;
import com.scor.rr.domain.dto.AssignUpdatePltsRequest;
import com.scor.rr.domain.dto.PltFilter;
import com.scor.rr.domain.dto.PltTagResponse;
import com.scor.rr.service.PltBrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/plt")
public class PltBrowserResource {

    @Autowired
    PltBrowserService pltBrowserService;


    @GetMapping
    public PltTagResponse searchPltTable(PltFilter filter) {
        return pltBrowserService.searchPltTable(filter);
    }

    @PostMapping("create-user-tag")
    public UserTag createUserTag(@RequestBody AssignPltsRequest assignPltsRequest) {
        return pltBrowserService.assignUserTag(assignPltsRequest);
    }

    @PostMapping("assign-user-tag")
    public List<UserTag> assignUpdateUserTag(@RequestBody AssignUpdatePltsRequest request) {
        return pltBrowserService.assignUpdateUserTag(request);
    }
    @DeleteMapping("user-tag/{id}")
    public void deleteUserTag(@PathVariable Integer id) {
        pltBrowserService.deleteUserTag(id);
    }

    @PutMapping("update-user-tag")
    public ResponseEntity<UserTag> updateTag(@RequestBody UserTag userTag) {
        try {
            return ResponseEntity.ok(pltBrowserService.updateUserTag(userTag));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
