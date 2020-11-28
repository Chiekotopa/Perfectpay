/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entitybd;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Embeddable
public class ApiPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NEng")
    private int nEng;
    @Basic(optional = false)
    @Column(name = "Indexe")
    private String indexe;

    public ApiPK() {
    }

    public ApiPK(int nEng, String indexe) {
        this.nEng = nEng;
        this.indexe = indexe;
    }

    public int getNEng() {
        return nEng;
    }

    public void setNEng(int nEng) {
        this.nEng = nEng;
    }

    public String getIndexe() {
        return indexe;
    }

    public void setIndexe(String indexe) {
        this.indexe = indexe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nEng;
        hash += (indexe != null ? indexe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApiPK)) {
            return false;
        }
        ApiPK other = (ApiPK) object;
        if (this.nEng != other.nEng) {
            return false;
        }
        if ((this.indexe == null && other.indexe != null) || (this.indexe != null && !this.indexe.equals(other.indexe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.ApiPK[ nEng=" + nEng + ", indexe=" + indexe + " ]";
    }
    
}
