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
public class InfoPayOrange {
    private String status;
    private String message;
    private String pay_token;
    private String payment_url;
    private String notif_token;

    public InfoPayOrange() {
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getNotif_token() {
        return notif_token;
    }

    public void setNotif_token(String notif_token) {
        this.notif_token = notif_token;
    }
    
    
}
