package com.scor.rr.domain.enums;

/**
 * File DATA Format Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum FileDATAFormatType {

	FILE_DATA_FORMAT_FACULTATIVE("Facultative"), FILE_DATA_FORMAT_TREATY("Treaty"), FILE_DATA_FORMAT_MAT_R("Mat-R");

	private String code;

	FileDATAFormatType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}