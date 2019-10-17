package com.scor.rr.service.params;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.entities.references.cat.Insured;
import com.scor.rr.domain.entities.references.cat.ProcessStatus;
import com.scor.rr.domain.entities.references.cat.message.MessageNotification;
import com.scor.rr.domain.entities.references.cat.message.MessageProcess;
import com.scor.rr.domain.entities.references.cat.message.MessageStatus;
import com.scor.rr.domain.entities.references.omega.PeriodBasis;
import com.scor.rr.utils.ALMFUtils;

import java.util.Date;

/**
 * MessageServiceParam
 * 
 * @author HADDINI Zakariyae
 *
 */
public class MessageServiceParam {

	private static final String UNKNOWN;

	private String entityKey;
	private String entityType;
	private String message;
	private String statusCode;
	private String process;
	private Integer divisionNumber;

	private CATRequest request;
	private PeriodBasis periodBasis;
	private NotifyParam notifyParam;

	static {
		UNKNOWN = "UNKNOWN";
	}

	public MessageServiceParam() {
	}

	public MessageServiceParam(String entityKey, CATRequest request, PeriodBasis periodBasis, NotifyParam notifyParam) {
		this.entityKey = entityKey;
		this.request = request;
		this.periodBasis = periodBasis;
		this.notifyParam = notifyParam;
	}

	public MessageServiceParam(String entityKey, String entityType, String message, String statusCode, String process,
			CATRequest request) {
		this.entityKey = entityKey;
		this.entityType = entityType;
		this.message = message;
		this.statusCode = statusCode;
		this.process = process;
		this.request = request;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public Integer getDivisionNumber() {
		return divisionNumber;
	}

	public void setDivisionNumber(Integer divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	public CATRequest getRequest() {
		return request;
	}

	public void setRequest(CATRequest request) {
		this.request = request;
	}

	public PeriodBasis getPeriodBasis() {
		return periodBasis;
	}

	public void setPeriodBasis(PeriodBasis periodBasis) {
		this.periodBasis = periodBasis;
	}

	public NotifyParam getNotifyParam() {
		return notifyParam;
	}

	public void setNotifyParam(NotifyParam notifyParam) {
		this.notifyParam = notifyParam;
	}

	/**
	 * instantiate MessageNotification object
	 * 
	 * @param messageStatus
	 * @param messageProcess
	 * @param code
	 * @return
	 */
	public MessageNotification instantiateMessageNotification(MessageStatus messageStatus,
															  MessageProcess messageProcess, String code) {
		return instantiateAndSetNotificationMessageFields(messageStatus, messageProcess, code,
				new MessageNotification());
	}

	/**
	 * instantiate ProcessStatus object
	 * 
	 * @return
	 */
	public ProcessStatus instantiateProcessStatus() {
		return new ProcessStatus(divisionNumber, request, periodBasis, notifyParam.getProcessStatus(),
				notifyParam.getRunningStatus());
	}

	/**
	 * instantiate and set notification message fields
	 * 
	 * @param messageStatus
	 * @param messageProcess
	 * @param code
	 * @param notification
	 * @return
	 */
	private MessageNotification instantiateAndSetNotificationMessageFields(MessageStatus messageStatus,
			MessageProcess messageProcess, String code, MessageNotification notification) {
		setMessageNotificationFields(messageStatus, messageProcess, code, notification);

		return setInsuredFieldsAndReturnNotificationMessage(notification);
	}

	/**
	 * set MessageNotification Fields
	 * 
	 * @param messageStatus
	 * @param messageProcess
	 * @param code
	 * @param notification
	 */
	private void setMessageNotificationFields(MessageStatus messageStatus, MessageProcess messageProcess, String code,
			MessageNotification notification) {
		// @formatter:off
		notification.setCode(code);
		notification.setRead(false);
		notification.setEntityKey(entityKey);
		notification.setDescription(message);
		notification.setEntityType(entityType);
		
		notification.setMessageNotificationId(
				"MSG-"
				.concat(request.getCatRequestId())
				.concat("_")
				.concat(
						String.valueOf(
								notification.getCreatedDate().getTime()
								)
						)
				);

		notification.setMessageStatus(messageStatus);
		notification.setMessageProcess(messageProcess);
		
		notification.setCreatedUser(request.getAssignedTo());
		notification.setCatRequestId(request.getCatRequestId());

		notification.setCreatedDate(new java.sql.Date(new Date().getTime()));
		notification.setEntityData(Long.toString(notification.getCreatedDate().getTime()));
		// @formatter:on
	}

	/**
	 * set Insured Fields
	 * 
	 * @param notification
	 */
	private MessageNotification setInsuredFieldsAndReturnNotificationMessage(MessageNotification notification) {
		if (ALMFUtils.isNotNull(request.getUwAnalysis()))
			if (ALMFUtils.isNotNull(request.getUwAnalysis().getContract())) {
				// @formatter:off
				Insured insured = request.getUwAnalysis().getContract().getInsured();
				String insuredName = ALMFUtils.isNotNull(insured) ? insured.getName() : UNKNOWN;

				notification.setInsuredName(insuredName);
				notification.setUwYear(
						String.valueOf(
								request.getUwAnalysis().getContract().getUwYear()
								)
						.concat("_")
						.concat(
								String.valueOf(
										request.getUwAnalysis().getContract().getUwOrder()
										)
								)
						);
				// @formatter:on
			}

		return notification;
	}

}
