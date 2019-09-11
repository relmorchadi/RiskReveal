package com.scor.rr.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "FacContract", schema = "dbo", catalog = "RiskReveal")
public class FacContractEntity {
    private Long id;
    private String faculNum;
    private Short year;
    private Short orderNum;
    private Long uwfileId;
    private String masterCd;
    private Long insurNum;
    private String label;
    private String underWritCd;
    private String techAsstCd;
    private Short status;
    private String adminCd;
    private String unitCd;
    private Date incepDt;
    private Date expiryDt;
    private String cedentCd;
    private String intermedCd;
    private Date statusDt;
    private String secOpinionCd;
    private Short sectorCd;
    private String isLongTerm;
    private String isRenewable;
    private Short validDuration;
    private Date origInceptDt;
    private Date origExpiryDt;
    private Date offerDt;
    private Short portfolioOrigCd;
    private Short cancelDur;
    private Short subsidiaryCd;
    private Short subsidLedgerCd;
    private Short scorLeader;
    private Integer leader;
    private BigDecimal leaderShare;
    private Integer origLeaderCd;
    private Integer origCedentNum;
    private Integer origIntermeaCd;
    private Short reinsurType;
    private String cliInsuredNm;
    private String lobCd;
    private Date createdDt;
    private Date updatedDt;
    private String createdBy;
    private String updatedBy;
    private Short endorNum;
    private BigDecimal egpi;
    private String uwCenterCd;
    private String ip2Call;
    private Short lastInForce;
    private BigDecimal conOmgEgpi;
    private String techInspService;
    private Short orderNumUpdate;
    private Short pricingDerty;
    private Short renewUpdated;

    @Id
    @Basic
    @Column(name = "ID", nullable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FACUL_NUM", nullable = true, length = 10)
    public String getFaculNum() {
        return faculNum;
    }

    public void setFaculNum(String faculNum) {
        this.faculNum = faculNum;
    }

    @Basic
    @Column(name = "YEAR", nullable = true)
    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    @Basic
    @Column(name = "ORDER_NUM", nullable = true)
    public Short getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Short orderNum) {
        this.orderNum = orderNum;
    }

    @Basic
    @Column(name = "UWFILE_ID", nullable = true)
    public Long getUwfileId() {
        return uwfileId;
    }

    public void setUwfileId(Long uwfileId) {
        this.uwfileId = uwfileId;
    }

    @Basic
    @Column(name = "MASTER_CD", nullable = true, length = 8)
    public String getMasterCd() {
        return masterCd;
    }

    public void setMasterCd(String masterCd) {
        this.masterCd = masterCd;
    }

    @Basic
    @Column(name = "INSUR_NUM", nullable = true)
    public Long getInsurNum() {
        return insurNum;
    }

    public void setInsurNum(Long insurNum) {
        this.insurNum = insurNum;
    }

    @Basic
    @Column(name = "LABEL", nullable = true, length = 64)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "UNDER_WRIT_CD", nullable = true, length = 4)
    public String getUnderWritCd() {
        return underWritCd;
    }

    public void setUnderWritCd(String underWritCd) {
        this.underWritCd = underWritCd;
    }

    @Basic
    @Column(name = "TECH_ASST_CD", nullable = true, length = 4)
    public String getTechAsstCd() {
        return techAsstCd;
    }

    public void setTechAsstCd(String techAsstCd) {
        this.techAsstCd = techAsstCd;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ADMIN_CD", nullable = true, length = 4)
    public String getAdminCd() {
        return adminCd;
    }

    public void setAdminCd(String adminCd) {
        this.adminCd = adminCd;
    }

    @Basic
    @Column(name = "UNIT_CD", nullable = true, length = 4)
    public String getUnitCd() {
        return unitCd;
    }

    public void setUnitCd(String unitCd) {
        this.unitCd = unitCd;
    }

    @Basic
    @Column(name = "INCEP_DT", nullable = true)
    public Date getIncepDt() {
        return incepDt;
    }

    public void setIncepDt(Date incepDt) {
        this.incepDt = incepDt;
    }

    @Basic
    @Column(name = "EXPIRY_DT", nullable = true)
    public Date getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(Date expiryDt) {
        this.expiryDt = expiryDt;
    }

    @Basic
    @Column(name = "CEDENT_CD", nullable = true, length = 6)
    public String getCedentCd() {
        return cedentCd;
    }

    public void setCedentCd(String cedentCd) {
        this.cedentCd = cedentCd;
    }

    @Basic
    @Column(name = "INTERMED_CD", nullable = true, length = 6)
    public String getIntermedCd() {
        return intermedCd;
    }

    public void setIntermedCd(String intermedCd) {
        this.intermedCd = intermedCd;
    }

    @Basic
    @Column(name = "STATUS_DT", nullable = true)
    public Date getStatusDt() {
        return statusDt;
    }

    public void setStatusDt(Date statusDt) {
        this.statusDt = statusDt;
    }

    @Basic
    @Column(name = "SEC_OPINION_CD", nullable = true, length = 4)
    public String getSecOpinionCd() {
        return secOpinionCd;
    }

    public void setSecOpinionCd(String secOpinionCd) {
        this.secOpinionCd = secOpinionCd;
    }

    @Basic
    @Column(name = "SECTOR_CD", nullable = true)
    public Short getSectorCd() {
        return sectorCd;
    }

    public void setSectorCd(Short sectorCd) {
        this.sectorCd = sectorCd;
    }

    @Basic
    @Column(name = "IS_LONG_TERM", nullable = true, length = 5)
    public String getIsLongTerm() {
        return isLongTerm;
    }

    public void setIsLongTerm(String isLongTerm) {
        this.isLongTerm = isLongTerm;
    }

    @Basic
    @Column(name = "IS_RENEWABLE", nullable = true, length = 5)
    public String getIsRenewable() {
        return isRenewable;
    }

    public void setIsRenewable(String isRenewable) {
        this.isRenewable = isRenewable;
    }

    @Basic
    @Column(name = "VALID_DURATION", nullable = true)
    public Short getValidDuration() {
        return validDuration;
    }

    public void setValidDuration(Short validDuration) {
        this.validDuration = validDuration;
    }

    @Basic
    @Column(name = "ORIG_INCEPT_DT", nullable = true)
    public Date getOrigInceptDt() {
        return origInceptDt;
    }

    public void setOrigInceptDt(Date origInceptDt) {
        this.origInceptDt = origInceptDt;
    }

    @Basic
    @Column(name = "ORIG_EXPIRY_DT", nullable = true)
    public Date getOrigExpiryDt() {
        return origExpiryDt;
    }

    public void setOrigExpiryDt(Date origExpiryDt) {
        this.origExpiryDt = origExpiryDt;
    }

    @Basic
    @Column(name = "OFFER_DT", nullable = true)
    public Date getOfferDt() {
        return offerDt;
    }

    public void setOfferDt(Date offerDt) {
        this.offerDt = offerDt;
    }

    @Basic
    @Column(name = "PORTFOLIO_ORIG_CD", nullable = true)
    public Short getPortfolioOrigCd() {
        return portfolioOrigCd;
    }

    public void setPortfolioOrigCd(Short portfolioOrigCd) {
        this.portfolioOrigCd = portfolioOrigCd;
    }

    @Basic
    @Column(name = "CANCEL_DUR", nullable = true)
    public Short getCancelDur() {
        return cancelDur;
    }

    public void setCancelDur(Short cancelDur) {
        this.cancelDur = cancelDur;
    }

    @Basic
    @Column(name = "SUBSIDIARY_CD", nullable = true)
    public Short getSubsidiaryCd() {
        return subsidiaryCd;
    }

    public void setSubsidiaryCd(Short subsidiaryCd) {
        this.subsidiaryCd = subsidiaryCd;
    }

    @Basic
    @Column(name = "SUBSID_LEDGER_CD", nullable = true)
    public Short getSubsidLedgerCd() {
        return subsidLedgerCd;
    }

    public void setSubsidLedgerCd(Short subsidLedgerCd) {
        this.subsidLedgerCd = subsidLedgerCd;
    }

    @Basic
    @Column(name = "SCOR_LEADER", nullable = true)
    public Short getScorLeader() {
        return scorLeader;
    }

    public void setScorLeader(Short scorLeader) {
        this.scorLeader = scorLeader;
    }

    @Basic
    @Column(name = "LEADER", nullable = true)
    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }

    @Basic
    @Column(name = "LEADER_SHARE", nullable = true, precision = 8)
    public BigDecimal getLeaderShare() {
        return leaderShare;
    }

    public void setLeaderShare(BigDecimal leaderShare) {
        this.leaderShare = leaderShare;
    }

    @Basic
    @Column(name = "ORIG_LEADER_CD", nullable = true)
    public Integer getOrigLeaderCd() {
        return origLeaderCd;
    }

    public void setOrigLeaderCd(Integer origLeaderCd) {
        this.origLeaderCd = origLeaderCd;
    }

    @Basic
    @Column(name = "ORIG_CEDENT_NUM", nullable = true)
    public Integer getOrigCedentNum() {
        return origCedentNum;
    }

    public void setOrigCedentNum(Integer origCedentNum) {
        this.origCedentNum = origCedentNum;
    }

    @Basic
    @Column(name = "ORIG_INTERMEA_CD", nullable = true)
    public Integer getOrigIntermeaCd() {
        return origIntermeaCd;
    }

    public void setOrigIntermeaCd(Integer origIntermeaCd) {
        this.origIntermeaCd = origIntermeaCd;
    }

    @Basic
    @Column(name = "REINSUR_TYPE", nullable = true)
    public Short getReinsurType() {
        return reinsurType;
    }

    public void setReinsurType(Short reinsurType) {
        this.reinsurType = reinsurType;
    }

    @Basic
    @Column(name = "CLI_INSURED_NM", nullable = true, length = 64)
    public String getCliInsuredNm() {
        return cliInsuredNm;
    }

    public void setCliInsuredNm(String cliInsuredNm) {
        this.cliInsuredNm = cliInsuredNm;
    }

    @Basic
    @Column(name = "LOB_CD", nullable = true, length = 2)
    public String getLobCd() {
        return lobCd;
    }

    public void setLobCd(String lobCd) {
        this.lobCd = lobCd;
    }

    @Basic
    @Column(name = "CREATED_DT", nullable = true)
    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    @Basic
    @Column(name = "UPDATED_DT", nullable = true)
    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    @Basic
    @Column(name = "CREATED_BY", nullable = true, length = 16)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "UPDATED_BY", nullable = true, length = 16)
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "ENDOR_NUM", nullable = true)
    public Short getEndorNum() {
        return endorNum;
    }

    public void setEndorNum(Short endorNum) {
        this.endorNum = endorNum;
    }

    @Basic
    @Column(name = "EGPI", nullable = true, precision = 3)
    public BigDecimal getEgpi() {
        return egpi;
    }

    public void setEgpi(BigDecimal egpi) {
        this.egpi = egpi;
    }

    @Basic
    @Column(name = "UW_CENTER_CD", nullable = true, length = 5)
    public String getUwCenterCd() {
        return uwCenterCd;
    }

    public void setUwCenterCd(String uwCenterCd) {
        this.uwCenterCd = uwCenterCd;
    }

    @Basic
    @Column(name = "IP2_CALL", nullable = true, length = 5)
    public String getIp2Call() {
        return ip2Call;
    }

    public void setIp2Call(String ip2Call) {
        this.ip2Call = ip2Call;
    }

    @Basic
    @Column(name = "LAST_IN_FORCE", nullable = true)
    public Short getLastInForce() {
        return lastInForce;
    }

    public void setLastInForce(Short lastInForce) {
        this.lastInForce = lastInForce;
    }

    @Basic
    @Column(name = "CON_OMG_EGPI", nullable = true, precision = 3)
    public BigDecimal getConOmgEgpi() {
        return conOmgEgpi;
    }

    public void setConOmgEgpi(BigDecimal conOmgEgpi) {
        this.conOmgEgpi = conOmgEgpi;
    }

    @Basic
    @Column(name = "TECH_INSP_SERVICE", nullable = true, length = 250)
    public String getTechInspService() {
        return techInspService;
    }

    public void setTechInspService(String techInspService) {
        this.techInspService = techInspService;
    }

    @Basic
    @Column(name = "ORDER_NUM_UPDATE", nullable = true)
    public Short getOrderNumUpdate() {
        return orderNumUpdate;
    }

    public void setOrderNumUpdate(Short orderNumUpdate) {
        this.orderNumUpdate = orderNumUpdate;
    }

    @Basic
    @Column(name = "PRICING_DERTY", nullable = true)
    public Short getPricingDerty() {
        return pricingDerty;
    }

    public void setPricingDerty(Short pricingDerty) {
        this.pricingDerty = pricingDerty;
    }

    @Basic
    @Column(name = "RENEW_UPDATED", nullable = true)
    public Short getRenewUpdated() {
        return renewUpdated;
    }

    public void setRenewUpdated(Short renewUpdated) {
        this.renewUpdated = renewUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacContractEntity that = (FacContractEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(faculNum, that.faculNum) &&
                Objects.equals(year, that.year) &&
                Objects.equals(orderNum, that.orderNum) &&
                Objects.equals(uwfileId, that.uwfileId) &&
                Objects.equals(masterCd, that.masterCd) &&
                Objects.equals(insurNum, that.insurNum) &&
                Objects.equals(label, that.label) &&
                Objects.equals(underWritCd, that.underWritCd) &&
                Objects.equals(techAsstCd, that.techAsstCd) &&
                Objects.equals(status, that.status) &&
                Objects.equals(adminCd, that.adminCd) &&
                Objects.equals(unitCd, that.unitCd) &&
                Objects.equals(incepDt, that.incepDt) &&
                Objects.equals(expiryDt, that.expiryDt) &&
                Objects.equals(cedentCd, that.cedentCd) &&
                Objects.equals(intermedCd, that.intermedCd) &&
                Objects.equals(statusDt, that.statusDt) &&
                Objects.equals(secOpinionCd, that.secOpinionCd) &&
                Objects.equals(sectorCd, that.sectorCd) &&
                Objects.equals(isLongTerm, that.isLongTerm) &&
                Objects.equals(isRenewable, that.isRenewable) &&
                Objects.equals(validDuration, that.validDuration) &&
                Objects.equals(origInceptDt, that.origInceptDt) &&
                Objects.equals(origExpiryDt, that.origExpiryDt) &&
                Objects.equals(offerDt, that.offerDt) &&
                Objects.equals(portfolioOrigCd, that.portfolioOrigCd) &&
                Objects.equals(cancelDur, that.cancelDur) &&
                Objects.equals(subsidiaryCd, that.subsidiaryCd) &&
                Objects.equals(subsidLedgerCd, that.subsidLedgerCd) &&
                Objects.equals(scorLeader, that.scorLeader) &&
                Objects.equals(leader, that.leader) &&
                Objects.equals(leaderShare, that.leaderShare) &&
                Objects.equals(origLeaderCd, that.origLeaderCd) &&
                Objects.equals(origCedentNum, that.origCedentNum) &&
                Objects.equals(origIntermeaCd, that.origIntermeaCd) &&
                Objects.equals(reinsurType, that.reinsurType) &&
                Objects.equals(cliInsuredNm, that.cliInsuredNm) &&
                Objects.equals(lobCd, that.lobCd) &&
                Objects.equals(createdDt, that.createdDt) &&
                Objects.equals(updatedDt, that.updatedDt) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(endorNum, that.endorNum) &&
                Objects.equals(egpi, that.egpi) &&
                Objects.equals(uwCenterCd, that.uwCenterCd) &&
                Objects.equals(ip2Call, that.ip2Call) &&
                Objects.equals(lastInForce, that.lastInForce) &&
                Objects.equals(conOmgEgpi, that.conOmgEgpi) &&
                Objects.equals(techInspService, that.techInspService) &&
                Objects.equals(orderNumUpdate, that.orderNumUpdate) &&
                Objects.equals(pricingDerty, that.pricingDerty) &&
                Objects.equals(renewUpdated, that.renewUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, faculNum, year, orderNum, uwfileId, masterCd, insurNum, label, underWritCd, techAsstCd, status, adminCd, unitCd, incepDt, expiryDt, cedentCd, intermedCd, statusDt, secOpinionCd, sectorCd, isLongTerm, isRenewable, validDuration, origInceptDt, origExpiryDt, offerDt, portfolioOrigCd, cancelDur, subsidiaryCd, subsidLedgerCd, scorLeader, leader, leaderShare, origLeaderCd, origCedentNum, origIntermeaCd, reinsurType, cliInsuredNm, lobCd, createdDt, updatedDt, createdBy, updatedBy, endorNum, egpi, uwCenterCd, ip2Call, lastInForce, conOmgEgpi, techInspService, orderNumUpdate, pricingDerty, renewUpdated);
    }
}
