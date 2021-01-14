/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entitybd;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IPLANS-DEV
 */
@Entity
@Table(name = "infopayment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Infopayment.findAll", query = "SELECT i FROM Infopayment i")})
public class Infopayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinfo")
    private Integer idinfo;
    @Column(name = "Tel")
    private String tel;
    @Column(name = "Montant")
    private String montant;
    @Column(name = "CodeClient")
    private String codeClient;
    @Column(name = "Projet")
    private String projet;
    @Column(name = "MoyenTransaction")
    private String moyenTransaction;
    @Column(name = "CodeAPI")
    private String codeAPI;
    @Column(name = "CompteClient")
    private String compteClient;
    @Column(name = "notif_token")
    private String notifToken;
    @Column(name = "index")
    private String index;

    public Infopayment() {
    }

    public Infopayment(Integer idinfo) {
        this.idinfo = idinfo;
    }

    public Integer getIdinfo() {
        return idinfo;
    }

    public void setIdinfo(Integer idinfo) {
        this.idinfo = idinfo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getMoyenTransaction() {
        return moyenTransaction;
    }

    public void setMoyenTransaction(String moyenTransaction) {
        this.moyenTransaction = moyenTransaction;
    }

    public String getCodeAPI() {
        return codeAPI;
    }

    public void setCodeAPI(String codeAPI) {
        this.codeAPI = codeAPI;
    }

    public String getCompteClient() {
        return compteClient;
    }

    public void setCompteClient(String compteClient) {
        this.compteClient = compteClient;
    }

    public String getNotifToken() {
        return notifToken;
    }

    public void setNotifToken(String notifToken) {
        this.notifToken = notifToken;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinfo != null ? idinfo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Infopayment)) {
            return false;
        }
        Infopayment other = (Infopayment) object;
        if ((this.idinfo == null && other.idinfo != null) || (this.idinfo != null && !this.idinfo.equals(other.idinfo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Infopayment[ idinfo=" + idinfo + " ]";
    }
    
}
