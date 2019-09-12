package com.scor.rr.rest;


import com.scor.rr.domain.FacContract;
import com.scor.rr.service.FacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fac")
public class FacResource {


    @Autowired
    FacService facService;


    @GetMapping()
    Page<FacContract> getFacContracts(FacContract filter,@PageableDefault(size = 10) Pageable pageable){
        return facService.getFacContract(filter, pageable);
    }

}
