package com.scor.rr.service.batch.reader;

import com.scor.rr.configuration.RmsInstanceCache;
import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.mapper.RLAccItemRowMapper;
import com.scor.rr.repository.StepRepository;
import com.scor.rr.service.abstraction.JobManager;
import com.scor.rr.service.batch.processor.rows.RLAccRow;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.EmbeddedQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.ExtendedConnectionDataSourceProxy;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.support.ListPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@StepScope
@Service
public class RLAccCursorItemReader extends JdbcCursorItemReader<RLAccRow> {
    private static final Logger log = LoggerFactory.getLogger(RLAccCursorItemReader.class);


    @Autowired
    private RmsInstanceCache rmsInstanceCache;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    @Qualifier("jobManagerImpl")
    private JobManager jobManager;

    @Autowired
    private StepRepository stepRepository;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

    @Value("#{jobParameters['instanceId']}")
    private String instanceId;

    @Value("#{jobParameters['rlPortfolioSelectionIds']}")
    private String rlPortfolioSelectionIds;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("${rms.ds.dbname}")
    private String database;

    private List<String> parameters;

    private StepEntity step;

    private boolean isOpen = false;

    public RLAccCursorItemReader() {
        super();
        setName(ClassUtils.getShortName(JdbcCursorItemReader.class));
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @PostConstruct
    public void init() {
        try {
            super.setDataSource(new ExtendedConnectionDataSourceProxy(rmsInstanceCache.getDataSource(instanceId)));
            super.setSql(EmbeddedQueries.RL_ACC_QUERY_PROC);
            super.setRowMapper(new RLAccItemRowMapper());
            parameters = new ArrayList<>();
            parameters.add("PORTFOLIO");
        } catch (Exception e) {
            log.error("init error", e);
            super.close();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void openCursor(Connection con) {

        if (step == null)
            step = jobManager.createStep(Long.valueOf(taskId), "ExtractACC", 15);

        if (!transformationPackage.getModelPortfolios().isEmpty()) {

            try {
                setSql(getSql().replace("@database", database));

                ListPreparedStatementSetter pss = new ListPreparedStatementSetter();
                List<Object> queryParameters = new LinkedList<>();

                queryParameters.add(transformationPackage.getModelPortfolios().get(0).getDataSourceName());
                queryParameters.add(transformationPackage.getModelPortfolios().get(0).getPortfolioName());

                pss.setParameters(queryParameters);
                setPreparedStatementSetter(pss);

                super.openCursor(con);
                isOpen = true;
            } catch (Exception ex) {
                jobManager.onTaskError(Long.valueOf(taskId));
//                jobManager.logStep(step.getStepId(), StepStatus.FAILED);
                step.setStatus(StepStatus.FAILED.getCode());
                stepRepository.save(step);
                if (isOpen)
                    super.close();
                ex.printStackTrace();
            }

        }

    }
}
