package com.scor.rr.service;


import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.UserPreference;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceService {


    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    public UserPreference getUserPreferencesByUser(Long userId) {
        return userPreferenceRepository.findByUserId(getUserId())
                .orElseThrow(() -> new RuntimeException("No available user configuration for userId: "+ getUserId()));

    }

    public UserPreference addUserPreferences(UserPreference userPreference) {
        userPreference.setUserId(getUserId());
        return userPreferenceRepository.save(userPreference);
    }

    public void deleteUserPreference(Long userPreferenceId) {
        userPreferenceRepository.deleteById(userPreferenceId);
    }

    private Long getUserId(){
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId();
    }
}

