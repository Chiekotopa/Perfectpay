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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author chiek
 */
@Service
public class OmService {

    public OmService() {
    }

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

    // Debiter un client Orange Money***************************************************************************************************
    //initier le payment pour obtenir le Paytoken
    public HashMap initPaymentOm() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap initPayMap = new HashMap();
        try {
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
        } catch (Exception e) {
           initPayMap.put("Message", e.getMessage());      
         return initPayMap;
        }
       
        

    }

    //obtenir le statut du payment a l'aide du paytoken
    public HashMap checkPaymentOm(String pay_token) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap response = new HashMap();
        // String infoPay = "";
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ///pour la desactivation de verification du certificat  
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial((X509Certificate[] chain, String authType) -> true)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        try {
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));
            InfoToken infoToken = new InfoToken();
            String url = "https://apiw.orange.cm/omcoreapis/1.0.2/mp/paymentstatus/" + pay_token;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().get("access_token"));
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
            headers.set("X-AUTH-TOKEN", "T01LQUtPVEVMMjAyMTpLQUtPVEVMU0FOREJPWDIwMjE=");
            HttpEntity<InfoToken> entity = new HttpEntity<>(infoToken, headers);
            ResponseEntity<String> infoPay = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JSONObject obj = new JSONObject(infoPay.getBody());
            if (obj.getJSONObject("data").getString("status").equals("SUCCESSFULL")) {
                response.put("message", "SUCCESSFULL");
                response.put("status", 1);
            }
            if (obj.getJSONObject("data").getString("status").equals("FAILED")) {
                response.put("message", "FAILED");
                response.put("status", -2);
            }

            if (obj.getJSONObject("data").getString("status").equals("CANCELLED")) {
                response.put("message", "CANCELLED");
                response.put("status", -1);
            }
            if (obj.getJSONObject("data").getString("status").equals("EXPIRED")) {
                response.put("message", "EXPIRED");
                response.put("status", 0);
            }
            if (obj.getJSONObject("data").getString("status").equals("PENDING")) {
                response.put("message", "PENDING");
                response.put("status", 2);
            }
            if (obj.getJSONObject("data").getString("status").equals("INITIATED")) {
                response.put("message", "INITIATED");
                response.put("status", 3);
            }

            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
        }

        return response;

    }

    //Crediter un Client Orange Money *********************************************************************************
    //initier le payment pour obtenir le Paytoken
    public HashMap initCashinOm() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
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
        String url = "https://apiw.orange.cm/omcoreapis/1.0.2/cashin/init";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().get("access_token"));
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("X-AUTH-TOKEN", "T01LQUtPVEVMMjAyMTpLQUtPVEVMU0FOREJPWDIwMjE=");
        HttpEntity<InfoToken> entity = new HttpEntity<>(infoToken, headers);
        initPayMap = restTemplate.postForObject(url, entity, HashMap.class);

        return initPayMap;

    }
    

    //obtenir le statut du payment a l'aide du paytoken
    public HashMap checkCashinOm(String pay_token) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap response = new HashMap();
        // String infoPay = "";
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ///pour la desactivation de verification du certificat  
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial((X509Certificate[] chain, String authType) -> true)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        try {
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));
            InfoToken infoToken = new InfoToken();
            String url = "https://apiw.orange.cm/omcoreapis/1.0.2/cashin/paymentstatus/" + pay_token;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().get("access_token"));
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
            headers.set("X-AUTH-TOKEN", "T01LQUtPVEVMMjAyMTpLQUtPVEVMU0FOREJPWDIwMjE=");
            HttpEntity<InfoToken> entity = new HttpEntity<>(infoToken, headers);
            ResponseEntity<String> infoPay = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JSONObject obj = new JSONObject(infoPay.getBody());
            if (obj.getJSONObject("data").getString("status").equals("SUCCESSFULL")) {
                response.put("message", "SUCCESSFULL");
                response.put("status", 1);
            }
            if (obj.getJSONObject("data").getString("status").equals("FAILED")) {
                response.put("message", "FAILED");
                response.put("status", -2);
            }

            if (obj.getJSONObject("data").getString("status").equals("CANCELLED")) {
                response.put("message", "CANCELLED");
                response.put("status", -1);
            }
            if (obj.getJSONObject("data").getString("status").equals("EXPIRED")) {
                response.put("message", "EXPIRED");
                response.put("status", 0);
            }
            if (obj.getJSONObject("data").getString("status").equals("PENDING")) {
                response.put("message", "PENDING");
                response.put("status", 2);
            }
            if (obj.getJSONObject("data").getString("status").equals("INITIATED")) {
                response.put("message", "INITIATED");
                response.put("status", 3);
            }

            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
        }

        return response;

    }

}
