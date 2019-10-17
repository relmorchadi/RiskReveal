package com.scor.rr.service.params;

import com.scor.rr.domain.enums.ProcessStatuses;

/**
 * NotifyParam
 * 
 * @author HADDINI Zakariyae
 *
 */
public class NotifyParam {

	ProcessStatuses runningStatus;
	ProcessStatuses processStatus;

	public NotifyParam() {
	}

	public NotifyParam(ProcessStatuses runningStatus, ProcessStatuses processStatus) {
		this.runningStatus = runningStatus;
		this.processStatus = processStatus;
	}

	public ProcessStatuses getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(ProcessStatuses runningStatus) {
		this.runningStatus = runningStatus;
	}

	public ProcessStatuses getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(ProcessStatuses processStatus) {
		this.processStatus = processStatus;
	}

}
