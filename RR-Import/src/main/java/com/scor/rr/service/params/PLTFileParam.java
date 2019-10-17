package com.scor.rr.service.params;

import com.scor.rr.domain.utils.plt.PLT;
import com.scor.rr.utils.ALMFUtils;

/**
 * PLTFileParam
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PLTFileParam {

	private String region;
	private String peril;
	private String ccy;
	private String fp;

	private PLT plt;

	public PLTFileParam() {
	}

	public PLTFileParam(String region, String peril, String ccy, String fp, PLT plt) {
		this.region = region;
		this.peril = peril;
		this.ccy = ccy;
		this.fp = fp;
		this.plt = plt;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPeril() {
		return peril;
	}

	public void setPeril(String peril) {
		this.peril = peril;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getFp() {
		return fp;
	}

	public void setFp(String fp) {
		this.fp = fp;
	}

	public PLT getPlt() {
		return plt;
	}

	public void setPlt(PLT plt) {
		this.plt = plt;
	}

	public Boolean isNotEmpty() {
		return ALMFUtils.isNotEmpty(region) && ALMFUtils.isNotEmpty(peril) && ALMFUtils.isNotEmpty(ccy)
				&& ALMFUtils.isNotEmpty(fp) && ALMFUtils.isNotNull(plt);
	}

}
