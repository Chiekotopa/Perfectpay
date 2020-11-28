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
@Table(name = "transtatus",catalog = "perfectpay", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transtatus.findAll", query = "SELECT t FROM Transtatus t")})
public class Transtatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "amount")
    private String amount;
    @Basic(optional = false)
    @Column(name = "pay_token")
    private String payToken;
    @Column(name = "permission")
    private String permission;
    @Column(name = "codeclient")
    private String codeclient;
    @Column(name = "codeapi")
    private String codeapi;
    @Column(name = "nomprojet")
    private String nomprojet;
   
    @Column(name = "operateur")
    private String operateur;
    @Column(name = "tel")
    private String tel;

    public Transtatus() {
    }

    public Transtatus(Integer id) {
        this.id = id;
    }

    public Transtatus(Integer id, String orderId, String payToken) {
        this.id = id;
        this.orderId = orderId;
        this.payToken = payToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayToken() {
        return payToken;
    }

    public void setPayToken(String payToken) {
        this.payToken = payToken;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    public String getNomprojet() {
        return nomprojet;
    }

    public void setNomprojet(String nomprojet) {
        this.nomprojet = nomprojet;
    }

   

    public String getOperateur() {
        return operateur;
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
        if (!(object instanceof Transtatus)) {
            return false;
        }
        Transtatus other = (Transtatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Transtatus[ id=" + id + " ]";
    }
    
}
