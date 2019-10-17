package com.scor.rr.importBatch.businessrules.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class BusinessRulesBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public  BusinessRulesBean(){
	}
	
	//the input parameters using for the rule
	private Map<String, Object> inputData = new HashMap<>();
	
	private final Map<String, Object> outputData = new HashMap<>();
	
	public Map<String, Object> getInputData() {
		return inputData;
	}
	public void setInputData(Map<String, Object> inputData) {
		this.inputData= inputData;
	}
	public Map<String, Object> getOutputData() {
		return outputData;
	}
	public void setOutputData(String key, Object value) {
		this.outputData.put(key, value);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BusinessRulesBean{");
		sb.append("inputData=").append(inputData);
		sb.append(", outputData=").append(outputData);
		sb.append('}');
		return sb.toString();
	}
}
