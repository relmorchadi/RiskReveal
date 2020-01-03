package com.scor.rr.domain.enums;

/**
 * PLTSimulationPeriod Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum PLTSimulationPeriod {

	SIM100K(100000), SIM800K(800000), SIMUNKNOWN(-1);

	private Integer code;

	PLTSimulationPeriod(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

}
