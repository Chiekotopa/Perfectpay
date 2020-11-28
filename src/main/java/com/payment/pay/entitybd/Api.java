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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Entity
@Table(name = "api", catalog = "perfectpay", schema = "")
@XmlRootElement

public class Api implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ApiPK apiPK;
    @Basic(optional = false)
    @Column(name = "Site")
    private String site;
    @Column(name = "Description")
    private String description;
    @Column(name = "IndexClient")
    private String indexClient;
    @Column(name = "Email")
    private String email;
    @Column(name = "Siteweb")
    private String siteweb;
    @Column(name = "NomApi")
    private String nomApi;
    @Column(name = "CodeApi")
    private String codeApi;
    @Column(name = "MotifRejet")
    private String motifRejet;
    @Column(name = "Synchronization")
    private String synchronization;
    @Column(name = "LastUpDateTime")
    private String lastUpDateTime;
    @Column(name = "IDLastUpDate")
    private Integer iDLastUpDate;
    @Column(name = "Statut")
    private Integer statut;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SolePaiement")
    private Double solePaiement;
    @Column(name = "CodeAgence")
    private String codeAgence;
    @Column(name = "DateTime")
    private String dateTime;
    @Column(name = "IDDateTime")
    private Integer iDDateTime;

    public Api() {
    }

    public Api(ApiPK apiPK) {
        this.apiPK = apiPK;
    }

    public Api(ApiPK apiPK, String site) {
        this.apiPK = apiPK;
        this.site = site;
    }

    public Api(int nEng, String indexe) {
        this.apiPK = new ApiPK(nEng, indexe);
    }

    public ApiPK getApiPK() {
        return apiPK;
    }

    public void setApiPK(ApiPK apiPK) {
        this.apiPK = apiPK;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndexClient() {
        return indexClient;
    }

    public void setIndexClient(String indexClient) {
        this.indexClient = indexClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getNomApi() {
        return nomApi;
    }

    public void setNomApi(String nomApi) {
        this.nomApi = nomApi;
    }

    public String getCodeApi() {
        return codeApi;
    }

    public void setCodeApi(String codeApi) {
        this.codeApi = codeApi;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
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

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public Double getSolePaiement() {
        return solePaiement;
    }

    public void setSolePaiement(Double solePaiement) {
        this.solePaiement = solePaiement;
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
        hash += (apiPK != null ? apiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Api)) {
            return false;
        }
        Api other = (Api) object;
        if ((this.apiPK == null && other.apiPK != null) || (this.apiPK != null && !this.apiPK.equals(other.apiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Api[ apiPK=" + apiPK + " ]";
    }
    
}
