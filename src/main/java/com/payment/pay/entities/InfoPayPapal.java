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
public class InfoPayPapal {
    
 private String amount;
 private String url_return,url_cancel,codeClient,codeApi,projet,moyenTransaction,compteClient,phone;

    public InfoPayPapal() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUrl_return() {
        return url_return;
    }

    public void setUrl_return(String url_return) {
        this.url_return = url_return;
    }

    public String getUrl_cancel() {
        return url_cancel;
    }

    public void setUrl_cancel(String url_cancel) {
        this.url_cancel = url_cancel;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getCodeApi() {
        return codeApi;
    }

    public void setCodeApi(String codeApi) {
        this.codeApi = codeApi;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String Projet) {
        this.projet = Projet;
    }

    public String getMoyenTransaction() {
        return moyenTransaction;
    }

    public void setMoyenTransaction(String moyenTransaction) {
        this.moyenTransaction = moyenTransaction;
    }

    public String getCompteClient() {
        return compteClient;
    }

    public void setCompteClient(String compteClient) {
        this.compteClient = compteClient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
 
 
    
}
