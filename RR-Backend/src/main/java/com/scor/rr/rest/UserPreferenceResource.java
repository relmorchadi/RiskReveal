package com.scor.rr.rest;


import com.scor.rr.domain.entities.userPreferences.UserPreference;
import com.scor.rr.domain.entities.userPreferences.UserPreferenceView;
import com.scor.rr.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("api/user-preferences")
public class UserPreferenceResource {

    @Autowired
    UserPreferenceService userPreferenceService;

    @GetMapping
    public ResponseEntity<UserPreferenceView> getUserPreferences(){
        return ResponseEntity.of(ofNullable(userPreferenceService.getUserPreferencesByUser()));
    }


    @PostMapping
    public ResponseEntity<UserPreference> addUserPreferences(@RequestBody UserPreference userPreference){
        return ResponseEntity.of( ofNullable(userPreferenceService.saveOrUpdateUserPreferences(userPreference)) );
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserPreferences(@RequestParam Long userPreferenceId){
        userPreferenceService.deleteUserPreference(userPreferenceId);
        return ResponseEntity.ok().build();
    }


}
