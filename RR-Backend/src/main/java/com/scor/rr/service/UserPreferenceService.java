package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.entities.userPreferences.UserPreference;
import com.scor.rr.domain.entities.userPreferences.UserPreferenceView;
import com.scor.rr.repository.userPreferences.UserPreferenceRepository;
import com.scor.rr.repository.userPreferences.UserPreferenceViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceService {


    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    @Autowired
    UserPreferenceViewRepository userPreferenceViewRepository;

    public UserPreferenceView getUserPreferencesByUser() {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return userPreferenceViewRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("No available user configuration for userId: "+ user.getUserId()));

    }

    public UserPreference saveOrUpdateUserPreferences(UserPreference userPreference) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        userPreference.setUserId(user.getUserId());

        userPreferenceRepository.findByUserId(user.getUserId()).
                ifPresent( userPreference1 -> userPreference.setUserPreferenceId(userPreference1.getUserPreferenceId()));

        return userPreferenceRepository.save(userPreference);
    }

    public void deleteUserPreference(Long userPreferenceId) {
        userPreferenceRepository.deleteById(userPreferenceId);
    }
}

