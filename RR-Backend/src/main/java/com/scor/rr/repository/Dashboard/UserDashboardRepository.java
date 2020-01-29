package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.UserDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDashboardRepository extends JpaRepository<UserDashboard,Long> {

    List<UserDashboard> findByUserId(long userId);
    void deleteByUserDashboardId(long id);
    UserDashboard findByUserDashboardId(long id);
}
