/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.payment.pay.dao.InfopayRepository;
import com.payment.pay.dao.PartenaireRepository;
import com.payment.pay.dao.TranstatusRepository;
import com.payment.pay.entities.InfoToken;
import com.payment.pay.entities.ObjectToUrlEncodedConverter;
import com.payment.pay.entities.Token;
import com.payment.pay.entitybd.Infopayment;
import com.payment.pay.entitybd.Partenaire;
import com.payment.pay.entitybd.Transtatus;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author chiek
 */
@Service
public class OmService {

    @Autowired
    PartenaireRepository partenaireRepository;
    
    @Autowired
    TranstatusRepository transtatusRepository;
    
    @Autowired
    InfopayRepository infopayRepository;

    public HashMap getToken() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap tokenMap = new HashMap();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ///pour la desactivation de verification du certificat  
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial((X509Certificate[] chain, String authType) -> true)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));
        InfoToken infoToken = new InfoToken();
        String url = "https://apiw.orange.cm/token";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic VTBySDVNUFBkUEV1dDVYQlYydm5GSGZQNjFNYTpQT21MRGo5amliQWkxd19DOFBpblpCVlN5NTRh");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        HttpEntity<InfoToken> entity = new HttpEntity<>(infoToken, headers);
        tokenMap = restTemplate.postForObject(url, entity, HashMap.class);

        return tokenMap;

    }

    //initier le payment pour obtenir le Paytoken
    public HashMap initPaymentOm() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap initPayMap = new HashMap();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ///pour la desactivation de verification du certificat  
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial((X509Certificate[] chain, String authType) -> true)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));
        InfoToken infoToken = new InfoToken();
        String url = "https://apiw.orange.cm/omcoreapis/1.0.2/mp/init";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().get("access_token"));
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("X-AUTH-TOKEN", "T01LQUtPVEVMMjAyMTpLQUtPVEVMU0FOREJPWDIwMjE=");
        HttpEntity<InfoToken> entity = new HttpEntity<>(infoToken, headers);
        initPayMap = restTemplate.postForObject(url, entity, HashMap.class);

        return initPayMap;

    }

    //initier le payment pour obtenir le Paytoken
    public HashMap PaymentOm(String Telephone, String amount, String codeClient, String codeApi, String nomProjet, String operateur, String index)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap initPayMap = new HashMap();
        Partenaire partenaire = new Partenaire();
        Transtatus transtatus = new Transtatus();
        Infopayment infopayment = new Infopayment();
        JSONObject obj = new JSONObject(initPaymentOm().toString());
        
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ///pour la desactivation de verification du certificat  
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial((X509Certificate[] chain, String authType) -> true)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));

        if (partenaireRepository.findByOmReference(nomProjet) == null) {
            partenaire.setOmReference(nomProjet.toUpperCase());
            partenaire.setOrderId(1);
            partenaireRepository.save(partenaire);
        }
        partenaire = partenaireRepository.findByOmReference(nomProjet.toUpperCase());

        HashMap infoPayMap = new HashMap();
        infoPayMap.put("subscriberMsisdn", Telephone);
        infoPayMap.put("channelUserMsisdn", "691301143");
        infoPayMap.put("amount", amount);
        infoPayMap.put("description", "payment test");
        infoPayMap.put("orderId", "OII_" + partenaire.getOrderId() + partenaire.getOmReference());
        infoPayMap.put("pin", "2222");
        infoPayMap.put("payToken", obj.getJSONObject("data").getString("payToken"));
        infoPayMap.put("notifUrl", "http://192.168.40.221:8081/Perfectpay/rest/api/paiement/orangeResponseRecharge");

        String url = "https://apiw.orange.cm/omcoreapis/1.0.2/mp/pay";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().get("access_token"));
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("X-AUTH-TOKEN", "T01LQUtPVEVMMjAyMTpLQUtPVEVMU0FOREJPWDIwMjE=");

        HttpEntity<HashMap> entity = new HttpEntity<>(infoPayMap, headers);
        initPayMap = restTemplate.postForObject(url, entity, HashMap.class);
        JSONObject obj2 = new JSONObject(initPayMap.toString());

        transtatus.setOrderId("OII_" + partenaire.getOrderId() + partenaire.getOmReference());
        transtatus.setAmount(amount);
        transtatus.setPayToken(obj.getJSONObject("data").getString("payToken"));
        transtatus.setNomprojet(nomProjet);
        transtatus.setPermission("1");
        transtatus.setCodeclient(codeClient);
        transtatus.setCodeapi(codeApi);
        transtatus.setOperateur(operateur);
        transtatus.setTel(Telephone);
        
        transtatusRepository.save(transtatus);

        infopayment.setCodeAPI(codeApi);
        infopayment.setCodeClient(codeClient);
        infopayment.setMontant(amount);
        infopayment.setMoyenTransaction(operateur);
        infopayment.setDescription("payment test");
        infopayment.setDate(new Date(System.currentTimeMillis()));
        infopayment.setProjet(nomProjet);
        infopayment.setTel(Telephone);
        infopayment.setTxnid(obj2.getJSONObject("data").getString("txnid"));
        infopayment.setPayToken(obj.getJSONObject("data").getString("payToken"));
        infopayRepository.save(infopayment);
        
        partenaire.setOrderId(partenaire.getOrderId() + 1);
        partenaireRepository.save(partenaire);

        return initPayMap;

    }

}
