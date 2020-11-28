/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entities;

/**
 *
 * @author Carlos TCHIOZEM
 */
public class ResOrange {
    private String status, notif_token,txnid;

    public ResOrange() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotif_token() {
        return notif_token;
    }

    public void setNotif_token(String notif_token) {
        this.notif_token = notif_token;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }
    
    
}
