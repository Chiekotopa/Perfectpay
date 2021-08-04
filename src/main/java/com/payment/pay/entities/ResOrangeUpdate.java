/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entities;

/**
 *
 * @author chiek
 */
public class ResOrangeUpdate {
    
    private String payToken,status,message;

    public ResOrangeUpdate() {
    }
    
    

    public String getPayToken() {
        return payToken;
    }

    public void setPayToken(String payToken) {
        this.payToken = payToken;
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
    
    
    
}
