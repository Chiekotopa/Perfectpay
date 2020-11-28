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
@Table(name = "souscriptions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Souscriptions.findAll", query = "SELECT s FROM Souscriptions s")})
public class Souscriptions implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SouscriptionsPK souscriptionsPK;
    @Basic(optional = false)
    @Column(name = "Site")
    private String site;
    @Basic(optional = false)
    @Column(name = "IndexeService")
    private String indexeService;
    @Basic(optional = false)
    @Column(name = "IndexeClient")
    private String indexeClient;
    @Column(name = "DateSouscription")
    private String dateSouscription;
    @Column(name = "IDDateSouscription")
    private Integer iDDateSouscription;
    @Column(name = "LastUpDateTime")
    private String lastUpDateTime;
    @Column(name = "IDLastUpDate")
    private Integer iDLastUpDate;
    @Column(name = "Etat")
    private Integer etat;
    @Column(name = "CodeAgence")
    private String codeAgence;
    @Column(name = "DateTime")
    private String dateTime;
    @Column(name = "IDDateTime")
    private Integer iDDateTime;

    public Souscriptions() {
    }

    public Souscriptions(SouscriptionsPK souscriptionsPK) {
        this.souscriptionsPK = souscriptionsPK;
    }

    public Souscriptions(SouscriptionsPK souscriptionsPK, String site, String indexeService, String indexeClient) {
        this.souscriptionsPK = souscriptionsPK;
        this.site = site;
        this.indexeService = indexeService;
        this.indexeClient = indexeClient;
    }

    public Souscriptions(int nEng, String indexe) {
        this.souscriptionsPK = new SouscriptionsPK(nEng, indexe);
    }

    public SouscriptionsPK getSouscriptionsPK() {
        return souscriptionsPK;
    }

    public void setSouscriptionsPK(SouscriptionsPK souscriptionsPK) {
        this.souscriptionsPK = souscriptionsPK;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getIndexeService() {
        return indexeService;
    }

    public void setIndexeService(String indexeService) {
        this.indexeService = indexeService;
    }

    public String getIndexeClient() {
        return indexeClient;
    }

    public void setIndexeClient(String indexeClient) {
        this.indexeClient = indexeClient;
    }

    public String getDateSouscription() {
        return dateSouscription;
    }

    public void setDateSouscription(String dateSouscription) {
        this.dateSouscription = dateSouscription;
    }

    public Integer getIDDateSouscription() {
        return iDDateSouscription;
    }

    public void setIDDateSouscription(Integer iDDateSouscription) {
        this.iDDateSouscription = iDDateSouscription;
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

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
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
        hash += (souscriptionsPK != null ? souscriptionsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Souscriptions)) {
            return false;
        }
        Souscriptions other = (Souscriptions) object;
        if ((this.souscriptionsPK == null && other.souscriptionsPK != null) || (this.souscriptionsPK != null && !this.souscriptionsPK.equals(other.souscriptionsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Souscriptions[ souscriptionsPK=" + souscriptionsPK + " ]";
    }
    
}
