package com.scor.rr.rest.Dashboard;

import com.scor.rr.domain.Response.UserDashboardResponse;
import com.scor.rr.domain.entities.Dashboard.UserDashboard;
import com.scor.rr.domain.requests.UserDashboardCreationRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.Dashboard.UserDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/DashBoard")
public class UserDashboardController {

    @Autowired
    private UserDashboardService userDashboardService;

    @GetMapping("getDashboards")
    public List<UserDashboardResponse> getDashboards(@RequestParam long userId) throws RRException {
        return userDashboardService.getDashboardForUser(userId);
    }

    @PostMapping("create")
    public UserDashboard createDashboard(@RequestBody UserDashboardCreationRequest request) throws RRException {
         return userDashboardService.createDashboard(request);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateDashboard(@RequestBody UserDashboard userDashboard) throws RRException {
        userDashboardService.updateDashboard(userDashboard);
        return ResponseEntity.ok("updated");
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteDashboard(@RequestParam long userDashboardId) throws RRException {
        userDashboardService.deleteDashboard(userDashboardId);
        return ResponseEntity.ok("deleted");
    }

}
