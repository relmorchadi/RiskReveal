package com.scor.rr.service.exposure.abstraction;

import org.springframework.batch.core.ExitStatus;

public interface ExposureSummaryTaskletWrapper
{
	ExitStatus importExposureSummary();
}