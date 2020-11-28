/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entities;

import java.io.Serializable;


/**
 *
 * @author  Chieko 
 */
public class InfoToken implements Serializable{
    
    private  String grant_type ="client_credentials";

    public InfoToken() {
    }
    

    public  String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    
    
}
