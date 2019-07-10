package com.scor.rr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RmsService {

    @Autowired
    @Qualifier("jdbcRms")
    JdbcTemplate rmsJdbcTemplate;

    public void test(){
        log.debug("[Rms Service] debug");
        rmsJdbcTemplate.query("select * from dddd", (rs, rowNum) -> rs.getString("id") );
    }

}
