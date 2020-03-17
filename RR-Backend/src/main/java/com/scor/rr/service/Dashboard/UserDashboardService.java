package com.scor.rr.service.Dashboard;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.Response.UserDashboardResponse;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.entities.Dashboard.UserDashboard;
import com.scor.rr.domain.entities.Dashboard.UserDashboardWidget;
import com.scor.rr.domain.requests.UserDashboardCreationRequest;
import com.scor.rr.exceptions.Dashboard.ImpossibleDeletionOfDashboard;
import com.scor.rr.exceptions.Dashboard.UserDashboardNotFoundException;
import com.scor.rr.exceptions.Dashboard.UserNotFoundException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.UserDashboardRepository;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetRepository;
import com.scor.rr.repository.UserRrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserDashboardWidgetService userDashboardWidgetService;
    @Autowired
    private UserDashboardWidgetRepository userDashboardWidgetRepository;

    @Autowired
    private UserRrRepository userRrRepository;

    public List<UserDashboardResponse> getDashboardForUser() throws RRException {

        long userCode = 0;
        if(SecurityContextHolder.getContext() !=null && SecurityContextHolder.getContext().getAuthentication() !=null) {
            userCode=(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId());
        }
        UserRrEntity user = userRrRepository.findByUserId(userCode);
        if (user == null) throw new UserNotFoundException(userCode);

        List<UserDashboard> listDash = userDashboardRepository.findByUserId(userCode);

        List<UserDashboardResponse> responses = new ArrayList<>();

        if (listDash == null || listDash.isEmpty()) {

            listDash = new ArrayList<>();
            UserDashboard userDashboard = new UserDashboard();
            userDashboard.setDashboardName("Treaty Dashboard");
            userDashboard.setUserId(userCode);
            userDashboard.setSearchMode("Treaty");
            userDashboard.setVisible(true);
            listDash.add(userDashboard);
            UserDashboardResponse dashboardResponse = new UserDashboardResponse();
            dashboardResponse.setUserDashboard(userDashboard);
            responses.add(dashboardResponse);

            UserDashboard userDashboard1 = new UserDashboard();
            userDashboard1.setDashboardName("Fac Dashboard");
            userDashboard1.setUserId(userCode);
            userDashboard1.setSearchMode("Fac");
            userDashboard1.setVisible(true);
            listDash.add(userDashboard1);
            UserDashboardResponse dashboardResponse1 = new UserDashboardResponse();
            dashboardResponse.setUserDashboard(userDashboard1);
            responses.add(dashboardResponse1);

            userDashboardRepository.saveAll(listDash);
            return responses;
        }else{


            for(UserDashboard dash : listDash){
                UserDashboardResponse userDashboardResponse = new UserDashboardResponse();
                userDashboardResponse.setUserDashboard(dash);
                userDashboardResponse.setWidgets(userDashboardWidgetService.getDashboardWidget(dash.getUserDashboardId()));
                responses.add(userDashboardResponse);
            }
        }
       return responses;


    }

    public UserDashboard createDashboard(UserDashboardCreationRequest request) throws RRException {

        long userCode = -1;
        if(SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext() !=null ) {
            userCode = (((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId());
        }
        UserRrEntity user = userRrRepository.findByUserId(userCode);
        if (user == null) throw new UserNotFoundException(userCode);

        UserDashboard userDashboard = new UserDashboard();
        userDashboard.setDashboardName(request.getDashboardName());
        userDashboard.setUserId(userCode);
        userDashboard.setSearchMode("Fac");
        userDashboard.setVisible(true);

        userDashboard = userDashboardRepository.saveAndFlush(userDashboard);
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



        List<UserDashboardResponse> listDash = getDashboardForUser();
        if(listDash.size() == 1 ) throw new ImpossibleDeletionOfDashboard();
        List<UserDashboardWidget> widgets = userDashboardWidgetRepository.findByUserDashboardId(dashboardId);
        if(!widgets.isEmpty()){
            for(UserDashboardWidget wid : widgets){
                userDashboardWidgetService.deleteDashboardWidget(wid.getUserDashboardWidgetId());
            }

        }
        userDashboardRepository.deleteByUserDashboardId(dashboardId);
    }

}
