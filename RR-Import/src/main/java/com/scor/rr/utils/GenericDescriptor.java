package com.scor.rr.utils;

/**
 * Generic Descriptor
 * 
 * @author HADDINI Zakariyae
 *
 */
public class GenericDescriptor implements Comparable<GenericDescriptor> {

	private String colName;
	private String dataType;
	private String targetName;
	private int targetOrder;
	private String targetFormat;

	public GenericDescriptor() {
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public int getTargetOrder() {
		return targetOrder;
	}

	public void setTargetOrder(int targetOrder) {
		this.targetOrder = targetOrder;
	}

	public String getTargetFormat() {
		return targetFormat;
	}

	public void setTargetFormat(String targetFormat) {
		this.targetFormat = targetFormat;
	}

	public int compareTo(GenericDescriptor gd) {
		return this.targetOrder - gd.targetOrder;
	}

}
