/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entitybd;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Entity
@Table(name = "apiperfectpay",catalog = "perfectpay", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apiperfectpay.findAll", query = "SELECT a FROM Apiperfectpay a")})
public class Apiperfectpay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idapiperfectpay")
    private Integer idapiperfectpay;
    @Column(name = "codeclient")
    private String codeclient;
    @Column(name = "codeapi")
    private String codeapi;
    @Column(name = "projet")
    private String projet;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "idtransaction")
    private String idtransaction;
    @Column(name = "status")
    private String status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant")
    private Double montant;
    @Column(name = "teldestinataire")
    private String teldestinataire;

    public Apiperfectpay() {
    }

    public Apiperfectpay(Integer idapiperfectpay) {
        this.idapiperfectpay = idapiperfectpay;
    }

    public Integer getIdapiperfectpay() {
        return idapiperfectpay;
    }

    public void setIdapiperfectpay(Integer idapiperfectpay) {
        this.idapiperfectpay = idapiperfectpay;
    }

    public String getCodeclient() {
        return codeclient;
    }

    public void setCodeclient(String codeclient) {
        this.codeclient = codeclient;
    }

    public String getCodeapi() {
        return codeapi;
    }

    public void setCodeapi(String codeapi) {
        this.codeapi = codeapi;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(String idtransaction) {
        this.idtransaction = idtransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getTeldestinataire() {
        return teldestinataire;
    }

    public void setTeldestinataire(String teldestinataire) {
        this.teldestinataire = teldestinataire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idapiperfectpay != null ? idapiperfectpay.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apiperfectpay)) {
            return false;
        }
        Apiperfectpay other = (Apiperfectpay) object;
        if ((this.idapiperfectpay == null && other.idapiperfectpay != null) || (this.idapiperfectpay != null && !this.idapiperfectpay.equals(other.idapiperfectpay))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Apiperfectpay[ idapiperfectpay=" + idapiperfectpay + " ]";
    }
    
}
