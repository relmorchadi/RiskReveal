package com.scor.rr.service.batch.reader;

import com.scor.rr.configuration.RmsInstanceCache;
import com.scor.rr.service.state.FacParameters;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.EmbeddedQueries;
import org.apache.commons.lang3.StringUtils;
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
import java.util.LinkedList;
import java.util.List;

@StepScope
public class RLAccCursorItemReader extends JdbcCursorItemReader {
    private static final Logger log = LoggerFactory.getLogger(RLAccCursorItemReader.class);


    @Autowired
    private RmsInstanceCache rmsInstanceCache;

    @Autowired
    private FacParameters facParameters;

    @Autowired
    private TransformationPackage transformationPackage;

    @Value("#{jobParameters['instanceId']}")
    private String instanceId;

    @Value("#{jobParameters['rlPortfolioSelectionIds']}")
    private String rlPortfolioSelectionIds;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    private List<String> parameters;

    public RLAccCursorItemReader() {
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
            super.setSql(EmbeddedQueries.RL_ACC_QUERY);
            parameters.add("PORTFOLIO");
        } catch (Exception e) {
            log.error("init error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void openCursor(Connection con) {

        if (marketChannel.equalsIgnoreCase("Fac")) {

            if (!transformationPackage.getModelPortfolios().isEmpty()) {

                setSql(StringUtils.replaceEach(getSql(), new String[]{":edm:"}, new String[]{transformationPackage.getModelPortfolios().get(0).getDataSourceName()}));

                ListPreparedStatementSetter pss = new ListPreparedStatementSetter();
                List<Object> queryParameters = new LinkedList<>();

                queryParameters.add(transformationPackage.getModelPortfolios().get(0).getPortfolioName());

                pss.setParameters(queryParameters);
                setPreparedStatementSetter(pss);

                super.openCursor(con);

            }
        }
    }
}
