/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.service;


import com.payment.pay.dao.SessiontransRepository;
import com.payment.pay.entities.Responses;
import com.payment.pay.entitybd.Sessiontrans;
import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Service
public class UssdService extends Thread {

    HttpHeaders headers = new HttpHeaders();

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    SessiontransRepository sessiontransRepository;

    Sessiontrans sessiontrans = new Sessiontrans();

    public void transfertToClientPerfectPay() {
        HashMap hashMap = new HashMap();

    }

  public Responses checkerCompteClientetrait(String codeClient, String phoneDest) {
        Responses response = new Responses();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        String boString = "";
        headers.set(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<Responses> entity = new HttpEntity<>(response, headers);

        String url = "https://api.kakotel.com/api-perfectpay.php?action=checker_compte_client_retrait&Code_clientExpediteur=" + codeClient + "&Code_clientDestinataire=" + phoneDest + "";
        response = restTemplate.getForObject(url, Responses.class, response);

        return response;
    }
   
   public Responses checkerSoldeExpediteurRetrait(String codeClient, String phoneDest, Double solde) {
        Responses response = new Responses();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        String boString = "";
        headers.set(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<Responses> entity = new HttpEntity<>(response, headers);

        String url = "https://api.kakotel.com/api-perfectpay.php?action=checker_solde_expediteur_retrait&Code_clientExpediteur=" + codeClient + "&Code_clientDestinataire=" + phoneDest + "&Montant=" + solde + "";
        response = restTemplate.getForObject(url, Responses.class, response);

        return response;
    }

    public Responses validationInitilisationRretraitAccountPerfectPay(String codeClient, String phoneDest, Double solde, String securiteCode) {
        Responses response = new Responses();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        String boString = "";
        headers.set(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<Responses> entity = new HttpEntity<>(response, headers);

        String url = "https://api.kakotel.com/api-perfectpay.php?action=validation_Initilisation_retrait_account_perfect_pay&Code_clientExpediteur=" + codeClient + "&Code_clientDestinataire=" + phoneDest + "&Montant=" + solde + "&CodeSecurite=" + securiteCode + "";
        response = restTemplate.getForObject(url, Responses.class, response);

        return response;
    }
}
