package com.scor.rr.repository.userPreferences;


import com.scor.rr.domain.entities.userPreferences.UserPreferenceView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferenceViewRepository extends JpaRepository<UserPreferenceView, Long> {

}
