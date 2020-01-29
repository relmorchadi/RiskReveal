package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.entities.Dashboard.UserDashboard;
import com.scor.rr.domain.requests.UserDashboardCreationRequest;
import com.scor.rr.exceptions.Dashboard.ImpossibleDeletionOfDashboard;
import com.scor.rr.exceptions.Dashboard.UserDashboardNotFoundException;
import com.scor.rr.exceptions.Dashboard.UserNotFoundException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.UserDashboardRepository;
import com.scor.rr.repository.UserRrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDashboardService {

    @Autowired
    private UserDashboardRepository userDashboardRepository;

    @Autowired
    private UserRrRepository userRrRepository;

    public List<UserDashboard> getDashboardForUser(long userId) throws RRException {

        UserRrEntity user = userRrRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(userId);

        List<UserDashboard> listDash = userDashboardRepository.findByUserId(userId);

        if (listDash == null || listDash.isEmpty()) {
           listDash = new ArrayList<>();
            UserDashboard userDashboard = new UserDashboard();
            userDashboard.setDashboardName("Treaty Dashboard");
            userDashboard.setUserId(userId);
            userDashboard.setSearchMode("Treaty");
            userDashboard.setVisible(true);
            listDash.add(userDashboard);

            UserDashboard userDashboard1 = new UserDashboard();
            userDashboard1.setDashboardName("Fac Dashboard");
            userDashboard1.setUserId(userId);
            userDashboard1.setSearchMode("Fac");
            userDashboard1.setVisible(true);
            listDash.add(userDashboard1);

            userDashboardRepository.saveAll(listDash);
        }
       return listDash;


    }

    public UserDashboard createDashboard(UserDashboardCreationRequest request) throws RRException {

        UserRrEntity user = userRrRepository.findByUserId(request.getUserId());
        if (user == null) throw new UserNotFoundException(request.getUserId());

        UserDashboard userDashboard = new UserDashboard();
        userDashboard.setDashboardName(request.getDashboardName());
        userDashboard.setUserId(request.getUserId());
        userDashboard.setSearchMode("Fac");
        userDashboard.setVisible(true);

        userDashboardRepository.save(userDashboard);
        return userDashboard;

    }

    public void updateDashboard(UserDashboard userDashboard) throws RRException{

        UserDashboard dashboard = userDashboardRepository.findByUserDashboardId(userDashboard.getUserDashboardId());
        if(dashboard == null) throw new UserDashboardNotFoundException(userDashboard.getUserDashboardId());
        userDashboardRepository.save(userDashboard);
    }

    public void deleteDashboard(long dashboardId) throws RRException{

        UserDashboard dashboard = userDashboardRepository.findByUserDashboardId(dashboardId);
        if(dashboard == null) throw new UserDashboardNotFoundException(dashboardId);

        List<UserDashboard> listDash = getDashboardForUser(dashboard.getUserId());
        if(listDash.size() == 1 ) throw new ImpossibleDeletionOfDashboard();
        userDashboardRepository.deleteByUserDashboardId(dashboardId);
    }

}
