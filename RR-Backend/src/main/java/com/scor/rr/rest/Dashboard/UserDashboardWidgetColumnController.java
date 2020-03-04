package com.scor.rr.rest.Dashboard;

import com.scor.rr.domain.requests.OrderAndVisibilityRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.Dashboard.UserDashboardWidgetColumnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/DashboardWidgetColumns")
public class UserDashboardWidgetColumnController {

    @Autowired
    private UserDashboardWidgetColumnsService userDashboardWidgetColumnsService;

    @PutMapping("updateWidth")
    public ResponseEntity<?> updateColumnWidth(@RequestParam long columnId,@RequestParam int width) throws RRException{
        userDashboardWidgetColumnsService.updateColumnWidth(columnId,width);
        return ResponseEntity.ok("Updated");
    }

    @PutMapping("updateFilter")
    public ResponseEntity<?> updateColumnFilter(@RequestParam long columnId,@RequestParam String filter) throws RRException{
        userDashboardWidgetColumnsService.updateColumnFilter(columnId,filter);
        return ResponseEntity.ok("Updated");
    }

    @PutMapping("resetFilter")
    public ResponseEntity<?> resetFilter(@RequestParam long widgetId) {
        userDashboardWidgetColumnsService.resetFilterForWidget(widgetId);
        return ResponseEntity.ok("Updated");
    }

    @PutMapping("resetSort")
    public ResponseEntity<?> resetSort(@RequestParam long widgetId) {
        userDashboardWidgetColumnsService.resetSortForWidget(widgetId);
        return ResponseEntity.ok("Updated");
    }


    @PutMapping("updateSort")
    public ResponseEntity<?> updateColumnSort(@RequestParam long columnId,@RequestParam int sort,@RequestParam String sortType) throws RRException{
        userDashboardWidgetColumnsService.updateColumnSort(columnId,sort,sortType);
        return ResponseEntity.ok("Updated");
    }

    @PutMapping("updateVisibilityAndOrder")
    public ResponseEntity<?> updateColumnVisibilityAndOrder(@RequestBody List<OrderAndVisibilityRequest> request) throws RRException{
        userDashboardWidgetColumnsService.updateOrderAndVisibility(request);
        return ResponseEntity.ok("Updated");
    }
}
