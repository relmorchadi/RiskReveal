package com.scor.rr.service;


import com.scor.rr.domain.UserPreference;
import com.scor.rr.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceService {


    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    public UserPreference getUserPreferencesByUser(Long userId) {
        return userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No available user configuration for userId: "+ userId));

    }

    public UserPreference addUserPreferences(UserPreference userPreference) {
        return userPreferenceRepository.save(userPreference);
    }

    public void deleteUserPreference(Long userPreferenceId) {
        userPreferenceRepository.deleteById(userPreferenceId);
    }
}

