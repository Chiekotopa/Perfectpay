/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entitybd;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Entity
@Table(name = "services")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Services.findAll", query = "SELECT s FROM Services s")})
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ServicesPK servicesPK;
    @Basic(optional = false)
    @Column(name = "Site")
    private String site;
    @Basic(optional = false)
    @Column(name = "CodeService")
    private String codeService;
    @Column(name = "LibelleF")
    private String libelleF;
    @Column(name = "LibelleA")
    private String libelleA;
    @Column(name = "SousServiceF")
    private String sousServiceF;
    @Column(name = "SousServiceA")
    private String sousServiceA;
    @Column(name = "Synchronization")
    private String synchronization;
    @Column(name = "LastUpDateTime")
    private String lastUpDateTime;
    @Column(name = "IDLastUpDate")
    private Integer iDLastUpDate;
    @Column(name = "CodeAgence")
    private String codeAgence;
    @Column(name = "DateTime")
    private String dateTime;
    @Column(name = "IDDateTime")
    private Integer iDDateTime;

    public Services() {
    }

    public Services(ServicesPK servicesPK) {
        this.servicesPK = servicesPK;
    }

    public Services(ServicesPK servicesPK, String site, String codeService) {
        this.servicesPK = servicesPK;
        this.site = site;
        this.codeService = codeService;
    }

    public Services(int nEng, String indexe) {
        this.servicesPK = new ServicesPK(nEng, indexe);
    }

    public ServicesPK getServicesPK() {
        return servicesPK;
    }

    public void setServicesPK(ServicesPK servicesPK) {
        this.servicesPK = servicesPK;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCodeService() {
        return codeService;
    }

    public void setCodeService(String codeService) {
        this.codeService = codeService;
    }

    public String getLibelleF() {
        return libelleF;
    }

    public void setLibelleF(String libelleF) {
        this.libelleF = libelleF;
    }

    public String getLibelleA() {
        return libelleA;
    }

    public void setLibelleA(String libelleA) {
        this.libelleA = libelleA;
    }

    public String getSousServiceF() {
        return sousServiceF;
    }

    public void setSousServiceF(String sousServiceF) {
        this.sousServiceF = sousServiceF;
    }

    public String getSousServiceA() {
        return sousServiceA;
    }

    public void setSousServiceA(String sousServiceA) {
        this.sousServiceA = sousServiceA;
    }

    public String getSynchronization() {
        return synchronization;
    }

    public void setSynchronization(String synchronization) {
        this.synchronization = synchronization;
    }

    public String getLastUpDateTime() {
        return lastUpDateTime;
    }

    public void setLastUpDateTime(String lastUpDateTime) {
        this.lastUpDateTime = lastUpDateTime;
    }

    public Integer getIDLastUpDate() {
        return iDLastUpDate;
    }

    public void setIDLastUpDate(Integer iDLastUpDate) {
        this.iDLastUpDate = iDLastUpDate;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getIDDateTime() {
        return iDDateTime;
    }

    public void setIDDateTime(Integer iDDateTime) {
        this.iDDateTime = iDDateTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servicesPK != null ? servicesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Services)) {
            return false;
        }
        Services other = (Services) object;
        if ((this.servicesPK == null && other.servicesPK != null) || (this.servicesPK != null && !this.servicesPK.equals(other.servicesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Services[ servicesPK=" + servicesPK + " ]";
    }
    
}
