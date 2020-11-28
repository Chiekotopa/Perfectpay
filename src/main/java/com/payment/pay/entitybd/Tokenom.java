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
@Table(name = "tokenom", catalog = "perfectpay", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tokenom.findAll", query = "SELECT t FROM Tokenom t"),
    @NamedQuery(name = "Tokenom.findByIdtokenom", query = "SELECT t FROM Tokenom t WHERE t.idtokenom = :idtokenom"),
    @NamedQuery(name = "Tokenom.findByAccessToken", query = "SELECT t FROM Tokenom t WHERE t.accessToken = :accessToken"),
    @NamedQuery(name = "Tokenom.findByExpiresIn", query = "SELECT t FROM Tokenom t WHERE t.expiresIn = :expiresIn"),
    @NamedQuery(name = "Tokenom.findByTokenType", query = "SELECT t FROM Tokenom t WHERE t.tokenType = :tokenType")})
public class Tokenom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtokenom", nullable = false)
    private Integer idtokenom;
    @Basic(optional = false)
    @Column(name = "access_token", nullable = false, length = 255)
    private String accessToken;
    @Column(name = "expires_in")
    @Temporal(TemporalType.DATE)
    private Date expiresIn;
    @Column(name = "token_type", length = 50)
    private String tokenType;

    public Tokenom() {
    }

    public Tokenom(Integer idtokenom) {
        this.idtokenom = idtokenom;
    }

    public Tokenom(Integer idtokenom, String accessToken) {
        this.idtokenom = idtokenom;
        this.accessToken = accessToken;
    }

    public Integer getIdtokenom() {
        return idtokenom;
    }

    public void setIdtokenom(Integer idtokenom) {
        this.idtokenom = idtokenom;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtokenom != null ? idtokenom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tokenom)) {
            return false;
        }
        Tokenom other = (Tokenom) object;
        if ((this.idtokenom == null && other.idtokenom != null) || (this.idtokenom != null && !this.idtokenom.equals(other.idtokenom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Tokenom[ idtokenom=" + idtokenom + " ]";
    }
    
}
