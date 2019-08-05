package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CLIENT", schema = "dbo", catalog = "RiskReveal")
public class ClientEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String clientnumber;
    private Integer clienthierarchicallevel;
    private String clientshortname;
    private Short clientstatus;
    private Short clientmanagementtype;
    private Integer clienttypecode;
    private String headofficecity;
    private String headofficecountrycodeId;
    private String headofficepostalcode;
    private String headofficestatecode;
    private String headofficestreet1;
    private String headofficestreet2;
    private String clientactivitycode;
    private String clientlegalstatus;
    private Integer responsiblesubsidiarycode;
    private String responsibleunitcode;
    private String clientinactivenaturecode;
    private Timestamp clientinactivedate;
    private String replacingclientcode;
    private Boolean clientcedent;
    private Boolean clientpayer;
    private Boolean clientacceptation;
    private Boolean clientretrocession;
    private Boolean clientsuppression;
    private String creationusercode;
    private Timestamp creationdate;
    private String lastupdateuser;
    private Timestamp lastupdatedate;
    private String groupsegment;
    private String clientsubsidiarycode;
    private Short embargo;
    private String holdingnumber;
    private String holdingname;
    private String name;

    @Id
    @Column(name = "id", nullable = false, length = 255,insertable = false ,updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ISACTIVE")
    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    @Basic
    @Column(name = "LASTSYNCHRONIZED")
    public Timestamp getLastsynchronized() {
        return lastsynchronized;
    }

    public void setLastsynchronized(Timestamp lastsynchronized) {
        this.lastsynchronized = lastsynchronized;
    }

    @Basic
    @Column(name = "CLIENTNUMBER", length = 255,insertable = false ,updatable = false)
    public String getClientnumber() {
        return clientnumber;
    }

    public void setClientnumber(String clientnumber) {
        this.clientnumber = clientnumber;
    }

    @Basic
    @Column(name = "CLIENTHIERARCHICALLEVEL")
    public Integer getClienthierarchicallevel() {
        return clienthierarchicallevel;
    }

    public void setClienthierarchicallevel(Integer clienthierarchicallevel) {
        this.clienthierarchicallevel = clienthierarchicallevel;
    }

    @Basic
    @Column(name = "CLIENTSHORTNAME", length = 255,insertable = false ,updatable = false)
    public String getClientshortname() {
        return clientshortname;
    }

    public void setClientshortname(String clientshortname) {
        this.clientshortname = clientshortname;
    }

    @Basic
    @Column(name = "CLIENTSTATUS")
    public Short getClientstatus() {
        return clientstatus;
    }

    public void setClientstatus(Short clientstatus) {
        this.clientstatus = clientstatus;
    }

    @Basic
    @Column(name = "CLIENTMANAGEMENTTYPE")
    public Short getClientmanagementtype() {
        return clientmanagementtype;
    }

    public void setClientmanagementtype(Short clientmanagementtype) {
        this.clientmanagementtype = clientmanagementtype;
    }

    @Basic
    @Column(name = "CLIENTTYPECODE")
    public Integer getClienttypecode() {
        return clienttypecode;
    }

    public void setClienttypecode(Integer clienttypecode) {
        this.clienttypecode = clienttypecode;
    }

    @Basic
    @Column(name = "HEADOFFICECITY", length = 255,insertable = false ,updatable = false)
    public String getHeadofficecity() {
        return headofficecity;
    }

    public void setHeadofficecity(String headofficecity) {
        this.headofficecity = headofficecity;
    }

    @Basic
    @Column(name = "HEADOFFICECOUNTRYCODE_ID", length = 255,insertable = false ,updatable = false)
    public String getHeadofficecountrycodeId() {
        return headofficecountrycodeId;
    }

    public void setHeadofficecountrycodeId(String headofficecountrycodeId) {
        this.headofficecountrycodeId = headofficecountrycodeId;
    }

    @Basic
    @Column(name = "HEADOFFICEPOSTALCODE", length = 255,insertable = false ,updatable = false)
    public String getHeadofficepostalcode() {
        return headofficepostalcode;
    }

    public void setHeadofficepostalcode(String headofficepostalcode) {
        this.headofficepostalcode = headofficepostalcode;
    }

    @Basic
    @Column(name = "HEADOFFICESTATECODE", length = 255,insertable = false ,updatable = false)
    public String getHeadofficestatecode() {
        return headofficestatecode;
    }

    public void setHeadofficestatecode(String headofficestatecode) {
        this.headofficestatecode = headofficestatecode;
    }

    @Basic
    @Column(name = "HEADOFFICESTREET1", length = 255,insertable = false ,updatable = false)
    public String getHeadofficestreet1() {
        return headofficestreet1;
    }

    public void setHeadofficestreet1(String headofficestreet1) {
        this.headofficestreet1 = headofficestreet1;
    }

    @Basic
    @Column(name = "HEADOFFICESTREET2", length = 255,insertable = false ,updatable = false)
    public String getHeadofficestreet2() {
        return headofficestreet2;
    }

    public void setHeadofficestreet2(String headofficestreet2) {
        this.headofficestreet2 = headofficestreet2;
    }

    @Basic
    @Column(name = "CLIENTACTIVITYCODE", length = 255,insertable = false ,updatable = false)
    public String getClientactivitycode() {
        return clientactivitycode;
    }

    public void setClientactivitycode(String clientactivitycode) {
        this.clientactivitycode = clientactivitycode;
    }

    @Basic
    @Column(name = "CLIENTLEGALSTATUS", length = 255,insertable = false ,updatable = false)
    public String getClientlegalstatus() {
        return clientlegalstatus;
    }

    public void setClientlegalstatus(String clientlegalstatus) {
        this.clientlegalstatus = clientlegalstatus;
    }

    @Basic
    @Column(name = "RESPONSIBLESUBSIDIARYCODE")
    public Integer getResponsiblesubsidiarycode() {
        return responsiblesubsidiarycode;
    }

    public void setResponsiblesubsidiarycode(Integer responsiblesubsidiarycode) {
        this.responsiblesubsidiarycode = responsiblesubsidiarycode;
    }

    @Basic
    @Column(name = "RESPONSIBLEUNITCODE", length = 255,insertable = false ,updatable = false)
    public String getResponsibleunitcode() {
        return responsibleunitcode;
    }

    public void setResponsibleunitcode(String responsibleunitcode) {
        this.responsibleunitcode = responsibleunitcode;
    }

    @Basic
    @Column(name = "CLIENTINACTIVENATURECODE", length = 255,insertable = false ,updatable = false)
    public String getClientinactivenaturecode() {
        return clientinactivenaturecode;
    }

    public void setClientinactivenaturecode(String clientinactivenaturecode) {
        this.clientinactivenaturecode = clientinactivenaturecode;
    }

    @Basic
    @Column(name = "CLIENTINACTIVEDATE")
    public Timestamp getClientinactivedate() {
        return clientinactivedate;
    }

    public void setClientinactivedate(Timestamp clientinactivedate) {
        this.clientinactivedate = clientinactivedate;
    }

    @Basic
    @Column(name = "REPLACINGCLIENTCODE", length = 255,insertable = false ,updatable = false)
    public String getReplacingclientcode() {
        return replacingclientcode;
    }

    public void setReplacingclientcode(String replacingclientcode) {
        this.replacingclientcode = replacingclientcode;
    }

    @Basic
    @Column(name = "CLIENTCEDENT")
    public Boolean getClientcedent() {
        return clientcedent;
    }

    public void setClientcedent(Boolean clientcedent) {
        this.clientcedent = clientcedent;
    }

    @Basic
    @Column(name = "CLIENTPAYER")
    public Boolean getClientpayer() {
        return clientpayer;
    }

    public void setClientpayer(Boolean clientpayer) {
        this.clientpayer = clientpayer;
    }

    @Basic
    @Column(name = "CLIENTACCEPTATION")
    public Boolean getClientacceptation() {
        return clientacceptation;
    }

    public void setClientacceptation(Boolean clientacceptation) {
        this.clientacceptation = clientacceptation;
    }

    @Basic
    @Column(name = "CLIENTRETROCESSION")
    public Boolean getClientretrocession() {
        return clientretrocession;
    }

    public void setClientretrocession(Boolean clientretrocession) {
        this.clientretrocession = clientretrocession;
    }

    @Basic
    @Column(name = "CLIENTSUPPRESSION")
    public Boolean getClientsuppression() {
        return clientsuppression;
    }

    public void setClientsuppression(Boolean clientsuppression) {
        this.clientsuppression = clientsuppression;
    }

    @Basic
    @Column(name = "CREATIONUSERCODE", length = 255,insertable = false ,updatable = false)
    public String getCreationusercode() {
        return creationusercode;
    }

    public void setCreationusercode(String creationusercode) {
        this.creationusercode = creationusercode;
    }

    @Basic
    @Column(name = "CREATIONDATE")
    public Timestamp getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    @Basic
    @Column(name = "LASTUPDATEUSER", length = 255,insertable = false ,updatable = false)
    public String getLastupdateuser() {
        return lastupdateuser;
    }

    public void setLastupdateuser(String lastupdateuser) {
        this.lastupdateuser = lastupdateuser;
    }

    @Basic
    @Column(name = "LASTUPDATEDATE")
    public Timestamp getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Timestamp lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    @Basic
    @Column(name = "GROUPSEGMENT", length = 255,insertable = false ,updatable = false)
    public String getGroupsegment() {
        return groupsegment;
    }

    public void setGroupsegment(String groupsegment) {
        this.groupsegment = groupsegment;
    }

    @Basic
    @Column(name = "CLIENTSUBSIDIARYCODE", length = 255,insertable = false ,updatable = false)
    public String getClientsubsidiarycode() {
        return clientsubsidiarycode;
    }

    public void setClientsubsidiarycode(String clientsubsidiarycode) {
        this.clientsubsidiarycode = clientsubsidiarycode;
    }

    @Basic
    @Column(name = "EMBARGO")
    public Short getEmbargo() {
        return embargo;
    }

    public void setEmbargo(Short embargo) {
        this.embargo = embargo;
    }

    @Basic
    @Column(name = "HOLDINGNUMBER", length = 255,insertable = false ,updatable = false)
    public String getHoldingnumber() {
        return holdingnumber;
    }

    public void setHoldingnumber(String holdingnumber) {
        this.holdingnumber = holdingnumber;
    }

    @Basic
    @Column(name = "HOLDINGNAME", length = 255,insertable = false ,updatable = false)
    public String getHoldingname() {
        return holdingname;
    }

    public void setHoldingname(String holdingname) {
        this.holdingname = holdingname;
    }

    @Basic
    @Column(name = "NAME", length = 255,insertable = false ,updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(clientnumber, that.clientnumber) &&
                Objects.equals(clienthierarchicallevel, that.clienthierarchicallevel) &&
                Objects.equals(clientshortname, that.clientshortname) &&
                Objects.equals(clientstatus, that.clientstatus) &&
                Objects.equals(clientmanagementtype, that.clientmanagementtype) &&
                Objects.equals(clienttypecode, that.clienttypecode) &&
                Objects.equals(headofficecity, that.headofficecity) &&
                Objects.equals(headofficecountrycodeId, that.headofficecountrycodeId) &&
                Objects.equals(headofficepostalcode, that.headofficepostalcode) &&
                Objects.equals(headofficestatecode, that.headofficestatecode) &&
                Objects.equals(headofficestreet1, that.headofficestreet1) &&
                Objects.equals(headofficestreet2, that.headofficestreet2) &&
                Objects.equals(clientactivitycode, that.clientactivitycode) &&
                Objects.equals(clientlegalstatus, that.clientlegalstatus) &&
                Objects.equals(responsiblesubsidiarycode, that.responsiblesubsidiarycode) &&
                Objects.equals(responsibleunitcode, that.responsibleunitcode) &&
                Objects.equals(clientinactivenaturecode, that.clientinactivenaturecode) &&
                Objects.equals(clientinactivedate, that.clientinactivedate) &&
                Objects.equals(replacingclientcode, that.replacingclientcode) &&
                Objects.equals(clientcedent, that.clientcedent) &&
                Objects.equals(clientpayer, that.clientpayer) &&
                Objects.equals(clientacceptation, that.clientacceptation) &&
                Objects.equals(clientretrocession, that.clientretrocession) &&
                Objects.equals(clientsuppression, that.clientsuppression) &&
                Objects.equals(creationusercode, that.creationusercode) &&
                Objects.equals(creationdate, that.creationdate) &&
                Objects.equals(lastupdateuser, that.lastupdateuser) &&
                Objects.equals(lastupdatedate, that.lastupdatedate) &&
                Objects.equals(groupsegment, that.groupsegment) &&
                Objects.equals(clientsubsidiarycode, that.clientsubsidiarycode) &&
                Objects.equals(embargo, that.embargo) &&
                Objects.equals(holdingnumber, that.holdingnumber) &&
                Objects.equals(holdingname, that.holdingname) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, clientnumber, clienthierarchicallevel, clientshortname, clientstatus, clientmanagementtype, clienttypecode, headofficecity, headofficecountrycodeId, headofficepostalcode, headofficestatecode, headofficestreet1, headofficestreet2, clientactivitycode, clientlegalstatus, responsiblesubsidiarycode, responsibleunitcode, clientinactivenaturecode, clientinactivedate, replacingclientcode, clientcedent, clientpayer, clientacceptation, clientretrocession, clientsuppression, creationusercode, creationdate, lastupdateuser, lastupdatedate, groupsegment, clientsubsidiarycode, embargo, holdingnumber, holdingname, name);
    }
}
