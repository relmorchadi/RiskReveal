package com.scor.rr.importBatch.processing.batch.rms;

import com.scor.rr.importBatch.processing.batch.ParameterBean;
import com.scor.rr.importBatch.processing.datasources.DSCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.ExtendedConnectionDataSourceProxy;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.support.ListPreparedStatementSetter;
import org.springframework.util.ClassUtils;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 03/04/2015.
 */
public class RMSJdbcCursorItemReader extends JdbcCursorItemReader {
    private static final Logger log = LoggerFactory.getLogger(RMSJdbcCursorItemReader.class);
    private ParameterBean rmsParameters;
    private List<String> parameters;
    protected String instanceId;
    private DSCache dsCache;

    public RMSJdbcCursorItemReader() {
        super();
        setName(ClassUtils.getShortName(JdbcCursorItemReader.class));
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setRmsParameters(ParameterBean rmsParameters) {
        this.rmsParameters = rmsParameters;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setDsCache(DSCache dsCache) {
        this.dsCache = dsCache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("nothing to see...");
    }

    public void init() {
        try {
            super.setDataSource(new ExtendedConnectionDataSourceProxy(dsCache.getDataSource(instanceId)));
        } catch (Exception e) {
            log.error("init error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void openCursor(Connection con) {
        List<String>keys = new LinkedList<String>();
        List<String>values = new LinkedList<String>();
        for (Map.Entry<String, String> placeholder : rmsParameters.getQueryPlaceHolders().entrySet()) {
            keys.add(placeholder.getKey());
            values.add(placeholder.getValue());
        }
        setSql(StringUtils.replaceEach(getSql(), keys.toArray(new String[keys.size()]), values.toArray(new String[values.size()])));

        ListPreparedStatementSetter pss = new ListPreparedStatementSetter();
        List<Object> queryParameters = new LinkedList<>();
        for (String parameter : parameters) {
            queryParameters.add(rmsParameters.getQueryParameters().get(parameter));
        }
        pss.setParameters(queryParameters);
        setPreparedStatementSetter(pss);

        super.openCursor(con);
    }
}
