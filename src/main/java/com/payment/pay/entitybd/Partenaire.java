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
@Table(name = "partenaire", catalog = "perfectpay", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partenaire.findAll", query = "SELECT p FROM Partenaire p"),
    @NamedQuery(name = "Partenaire.findByIdpartenaire", query = "SELECT p FROM Partenaire p WHERE p.idpartenaire = :idpartenaire"),
    @NamedQuery(name = "Partenaire.findByOmReference", query = "SELECT p FROM Partenaire p WHERE p.omReference = :omReference"),
    @NamedQuery(name = "Partenaire.findByOrderId", query = "SELECT p FROM Partenaire p WHERE p.orderId = :orderId")})
public class Partenaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpartenaire", nullable = false)
    private Integer idpartenaire;
    @Column(name = "om_reference", length = 255)
    private String omReference;
    @Column(name = "order_id")
    private Integer orderId;

    public Partenaire() {
    }

    public Partenaire(Integer idpartenaire) {
        this.idpartenaire = idpartenaire;
    }

    public Integer getIdpartenaire() {
        return idpartenaire;
    }

    public void setIdpartenaire(Integer idpartenaire) {
        this.idpartenaire = idpartenaire;
    }

    public String getOmReference() {
        return omReference;
    }

    public void setOmReference(String omReference) {
        this.omReference = omReference;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpartenaire != null ? idpartenaire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partenaire)) {
            return false;
        }
        Partenaire other = (Partenaire) object;
        if ((this.idpartenaire == null && other.idpartenaire != null) || (this.idpartenaire != null && !this.idpartenaire.equals(other.idpartenaire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Partenaire[ idpartenaire=" + idpartenaire + " ]";
    }
    
}
