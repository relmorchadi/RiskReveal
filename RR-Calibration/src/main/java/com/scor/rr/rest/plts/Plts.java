package com.scor.rr.rest.plts;

import com.scor.rr.domain.Calibration;
import com.scor.rr.service.Plt.PltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/plts")
public class Plts {

    @Autowired
    PltService pltService;

    @GetMapping
    public List<Calibration> getPlts(@RequestParam String wsId, @RequestParam Integer uwYear) { return  pltService.getPlts(wsId, uwYear); }
}
