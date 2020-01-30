package com.scor.rr.service.Dashboard;

import com.scor.rr.repository.Dashboard.UserDashboardWidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDashboardWidgetService {

    @Autowired
    private UserDashboardWidgetRepository userDashboardWidgetRepository;
}
