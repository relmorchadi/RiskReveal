package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.enums.XLTAssetType;
import com.scor.rr.domain.enums.XLTOT;
import com.scor.rr.domain.enums.XLTOrigin;
import com.scor.rr.domain.enums.XLTSubType;
import com.scor.rr.util.PathUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@StepScope
public abstract class AbstractWriter {

    @Value("#{jobParameters['projectId']}")
    protected String projectId;

    @Value("#{jobParameters['clientId']}")
    protected String clientId;

    @Value("#{jobParameters['clientName']}")
    protected String clientName;

    @Value("#{jobParameters['uwYear']}")
    protected String uwYear;

    @Value("#{jobParameters['contractId']}")
    protected String contractId;

    @Value("#{jobParameters['reinsuranceType']}")
    protected String reinsuranceType;

    @Value("#{jobParameters['prefix']}")
    protected String prefix;

    @Value("#{jobParameters['division']}")
    protected String division;

    @Value("#{jobParameters['sourceVendor']}")
    protected String sourceVendor;

    @Value("#{jobParameters['modelSystemVersion']}")
    protected String modelSystemVersion;

    @Value("#{jobParameters['periodBasis']}")
    protected String periodBasis;

    @Value("#{jobParameters['importSequence']}")
    protected Long importSequence;

    protected synchronized String makeELTFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long uniqueId,String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.DAT,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makeAPSFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long uniqueId, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.APS,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }
}
