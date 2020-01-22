package com.scor.rr.service.batch.reader;

import com.scor.rr.configuration.RmsInstanceCache;
import com.scor.rr.domain.dto.CARDivisionDto;
import com.scor.rr.mapper.RLLocItemRowMapper;
import com.scor.rr.service.abstraction.ConfigurationService;
import com.scor.rr.service.batch.processor.rows.RLLocRow;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.EmbeddedQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.ExtendedConnectionDataSourceProxy;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.support.ListPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@StepScope
public class RLLocCursorItemReader extends JdbcCursorItemReader<RLLocRow> {
    private static final Logger log = LoggerFactory.getLogger(RLLocCursorItemReader.class);

    @Autowired
    private RmsInstanceCache rmsInstanceCache;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ConfigurationService configurationService;

    @Value("#{jobParameters['instanceId']}")
    private String instanceId;

    @Value("#{jobParameters['rlPortfolioSelectionIds']}")
    private String rlPortfolioSelectionIds;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Value("${rms.ds.dbname}")
    private String database;


    private List<String> parameters;

    public RLLocCursorItemReader() {
        super();
        setName(ClassUtils.getShortName(JdbcCursorItemReader.class));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("nothing to see...");
    }

    @PostConstruct
    public void init() {
        try {
            super.setDataSource(new ExtendedConnectionDataSourceProxy(rmsInstanceCache.getDataSource(instanceId)));
            super.setSql(EmbeddedQueries.RL_LOC_QUERY_PROC);
            super.setRowMapper(new RLLocItemRowMapper());
            parameters = new ArrayList<>();
            parameters.add("LIABILITY_CCY");
            parameters.add("PORTFOLIO");
            parameters.add("PORTFOLIO");
        } catch (Exception e) {
            log.error("init error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void openCursor(Connection con) {

        if (!transformationPackage.getModelPortfolios().isEmpty()) {

            String edm = transformationPackage.getModelPortfolios().get(0).getDataSourceName();
            String rdm = transformationPackage.getModelPortfolios().get(0).getDataSourceName().replaceAll("(_E$)", "_R");

            setSql(getSql().replace("@database", database));

            Integer division = transformationPackage.getModelPortfolios().get(0).getDivision();

            ListPreparedStatementSetter pss = new ListPreparedStatementSetter();
            List<Object> queryParameters = new LinkedList<>();

            queryParameters.add(edm);
            queryParameters.add(rdm);
            queryParameters.add(transformationPackage.getModelPortfolios().get(0).getPortfolioName());
            queryParameters.add(
                    configurationService.getDivisions(carId).stream().filter(div -> div.getDivisionNumber().equals(division))
                            .map(CARDivisionDto::getCurrency)
                            .findFirst().orElse("USD"));

            pss.setParameters(queryParameters);
            setPreparedStatementSetter(pss);

            super.openCursor(con);

        }
    }
}
