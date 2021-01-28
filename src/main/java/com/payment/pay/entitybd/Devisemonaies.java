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
 * @author Carlos TCHIOZEM
 */
@Entity
@Table(name = "devisemonaies",catalog = "perfectpay", schema = "")

@NamedQueries({
    @NamedQuery(name = "Devisemonaies.findAll", query = "SELECT d FROM Devisemonaies d")})
public class Devisemonaies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "usdLivreSterling")
    private Float usdLivreSterling;
    @Column(name = "usdaed")
    private Float usdaed;
    @Column(name = "usdcad")
    private Float usdcad;
    @Column(name = "usdeur")
    private Float usdeur;
    @Column(name = "usdnaira")
    private Float usdnaira;
    @Column(name = "usdxof")
    private Float usdxof;
    @Column(name = "usdyuan")
    private Float usdyuan;
    @Column(name = "usdzar")
    private Float usdzar;
    @Column(name = "requestnumber")
    private Integer requestnumber;
    @Column(name = "keydevise")
    private String keydevise;

    public Devisemonaies() {
    }

    public Devisemonaies(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getUsdLivreSterling() {
        return usdLivreSterling;
    }

    public void setUsdLivreSterling(Float usdLivreSterling) {
        this.usdLivreSterling = usdLivreSterling;
    }

    public Float getUsdaed() {
        return usdaed;
    }

    public void setUsdaed(Float usdaed) {
        this.usdaed = usdaed;
    }

    public Float getUsdcad() {
        return usdcad;
    }

    public void setUsdcad(Float usdcad) {
        this.usdcad = usdcad;
    }

    public Float getUsdeur() {
        return usdeur;
    }

    public void setUsdeur(Float usdeur) {
        this.usdeur = usdeur;
    }

    public Float getUsdnaira() {
        return usdnaira;
    }

    public void setUsdnaira(Float usdnaira) {
        this.usdnaira = usdnaira;
    }

    public Float getUsdxof() {
        return usdxof;
    }

    public void setUsdxof(Float usdxof) {
        this.usdxof = usdxof;
    }

    public Float getUsdyuan() {
        return usdyuan;
    }

    public void setUsdyuan(Float usdyuan) {
        this.usdyuan = usdyuan;
    }

    public Float getUsdzar() {
        return usdzar;
    }

    public void setUsdzar(Float usdzar) {
        this.usdzar = usdzar;
    }

    public Integer getRequestnumber() {
        return requestnumber;
    }

    public void setRequestnumber(Integer requestnumber) {
        this.requestnumber = requestnumber;
    }

    public String getKeydevise() {
        return keydevise;
    }

    public void setKeydevise(String keydevise) {
        this.keydevise = keydevise;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Devisemonaies)) {
            return false;
        }
        Devisemonaies other = (Devisemonaies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Devisemonaies[ id=" + id + " ]";
    }
    
}
