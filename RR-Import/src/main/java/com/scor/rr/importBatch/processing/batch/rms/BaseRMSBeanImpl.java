package com.scor.rr.importBatch.processing.batch.rms;

import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.datasources.DSCache;
import com.scor.rr.repository.workspace.ProjectRepository;
import com.scor.rr.service.RmsDataProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Created by U002629 on 03/04/2015.
 */
public abstract class BaseRMSBeanImpl extends BaseBatchBeanImpl implements BaseRMSBean {
    private static final Logger log = LoggerFactory.getLogger(BaseRMSBeanImpl.class);
    protected static final String DELIMITER = ";";

    protected String edm;
    protected String rdm;
    protected long edmId;
    protected long rdmId;

    protected JdbcTemplate jdbcTemplate;
    protected RmsDataProviderService rmsDataProvider;
    @Autowired
    private ProjectRepository projectRepository;
    private DSCache dsCache;

    @Override
    public void setEdm(String edm) {
        this.edm = edm;
    }

    @Override
    public void setRdm(String rdm) {
        this.rdm = rdm;
    }

    public long getEdmId() {
        return edmId;
    }

    public void setEdmId(long edmId) {
        this.edmId = edmId;
    }

    public long getRdmId() {
        return rdmId;
    }

    public void setRdmId(long rdmId) {
        this.rdmId = rdmId;
    }

    public DSCache getDsCache() {
        return dsCache;
    }

    public void setDsCache(DSCache dsCache) {
        this.dsCache = dsCache;
    }

    public RmsDataProviderService getRmsDataProvider() {
        return rmsDataProvider;
    }

    public void setRmsDataProvider(RmsDataProviderService rmsDataProvider) {
        this.rmsDataProvider = rmsDataProvider;
    }

    @Override
    public void init() {
        try {
//            this.jdbcTemplate = new JdbcTemplate(dsCache.getDatasource(instanceId));
        } catch (Exception e) {
            log.error("init error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Map<String, Object> getKPInfo() {
        Map<String, Object> kpiInfo = super.getKPInfo();
        kpiInfo.put("edm", edm);
        kpiInfo.put("rdm", rdm);
        return kpiInfo;
    }
}
