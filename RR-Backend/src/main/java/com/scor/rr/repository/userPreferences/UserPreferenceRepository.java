package com.scor.rr.repository.userPreferences;

import com.scor.rr.domain.entities.userPreferences.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

    Optional<UserPreference> findByUserId(Long userId);

}
