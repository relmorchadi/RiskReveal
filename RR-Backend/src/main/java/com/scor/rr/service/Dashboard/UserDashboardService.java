package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.entities.Dashboard.UserDashboard;
import com.scor.rr.domain.requests.UserDashboardCreationRequest;
import com.scor.rr.repository.Dashboard.UserDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDashboardService {

    @Autowired
    private UserDashboardRepository userDashboardRepository;

    public List<UserDashboard> getDashboardForUser(long userId){

        return userDashboardRepository.findByUserId(userId);

    }

    public UserDashboard createDashboard(UserDashboardCreationRequest request){

        UserDashboard userDashboard = new UserDashboard();
        userDashboard.setDashboardName(request.getDashboardName());
        userDashboard.setUserId(request.getUserId());
        userDashboard.setSearchMode("Fac");
        userDashboard.setVisible(true);

        userDashboardRepository.save(userDashboard);
        return  userDashboard;

    }

    public void updateDashboard(UserDashboard userDashboard){
        userDashboardRepository.save(userDashboard);
    }

    public void deleteDashboard(long dashboardId){
       userDashboardRepository.deleteByUserDashboardId(dashboardId);
    }
}
