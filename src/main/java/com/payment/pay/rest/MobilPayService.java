/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.payment.pay.config.PaypalPaymentIntent;
import com.payment.pay.config.PaypalPaymentMethod;
import com.payment.pay.dao.ApiRepository;
import com.payment.pay.dao.ClientRepository;
import com.payment.pay.dao.InfopayRepository;
import com.payment.pay.dao.PartenaireRepository;
import com.payment.pay.dao.ServiceRepository;
import com.payment.pay.dao.SouscriptionRepository;
import com.payment.pay.dao.TokenomRepository;
import com.payment.pay.dao.TranstatusRepository;
import com.payment.pay.entities.InfoPayOrange;
import com.payment.pay.entities.InfoPayPapal;
import com.payment.pay.entities.InfoToken;
import com.payment.pay.entities.InfoTrasaction;
import com.payment.pay.entities.ObjectToUrlEncodedConverter;
import com.payment.pay.entities.OmStatus;
import com.payment.pay.entities.Pojo;
import com.payment.pay.entities.ResOrange;
import com.payment.pay.entities.Token;
import com.payment.pay.entitybd.Api;
import com.payment.pay.entitybd.Clients;
import com.payment.pay.entitybd.Infopayment;
import com.payment.pay.entitybd.Partenaire;
import com.payment.pay.entitybd.Services;
import com.payment.pay.entitybd.Souscriptions;
import com.payment.pay.entitybd.Tokenom;
import com.payment.pay.entitybd.Transtatus;
import com.payment.pay.gtplimited.BalanceRequest;
import com.payment.pay.gtplimited.BaseRequest;
import com.payment.pay.gtplimited.BaseRequestHeader;
import com.payment.pay.gtplimited.BaseResponse;
import com.payment.pay.gtplimited.ErrorResponse;
import com.payment.pay.gtplimited.FundTransferCardToCardRequest;
import com.payment.pay.gtplimited.FundTransferResponse;
import com.payment.pay.gtplimited.RequestHeader;
import com.payment.pay.monetbil.InfoMonetbil;
import com.payment.pay.monetbil.PojoMb;
import com.paypal.api.payments.Links;
import java.net.ProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.payment.pay.gtplimited.AllServicesStub;
import com.payment.pay.gtplimited.FundTransferRequest;
import com.payment.pay.service.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Chieko Topa
 */
@Controller

@CrossOrigin(origins = "*")
@RequestMapping("/rest/api/paiement")
public class MobilPayService {

    @Autowired
    TokenomRepository tokenomRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PartenaireRepository partenaireRepository;

    @Autowired
    ApiRepository apiRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    SouscriptionRepository souscriptionRepository;

    @Autowired
    TranstatusRepository transtatusRepository;

    @Autowired
    InfopayRepository infopayRepository;

    @Autowired
    private PaypalService paypalService;

    APIContext aPIContext;

    AllServicesStub stub;

    private RequestHeader requestHeader;

    private Logger log = LoggerFactory.getLogger(getClass());

    public Token getToken() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Date date = new Date(System.currentTimeMillis());
        Tokenom tokenom = new Tokenom();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 90);
        date = calendar.getTime();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));
        InfoToken infoToken = new InfoToken();
        String url = "https://api.orange.com/oauth/v2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic VWtxOEdNSU9DYkd2NFJXVE1PSlZZMEh1S04xWVphNDc6a2tkTGJ2eFZNNzJkZGZrag==");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        HttpEntity<InfoToken> entity = new HttpEntity<>(infoToken, headers);

        Token token = restTemplate.postForObject(url, entity, Token.class);

        tokenom.setAccessToken(token.getAccess_token());
        tokenom.setExpiresIn(date);
        tokenom.setTokenType("Bearer");
        tokenom.setIdtokenom(1);
        tokenomRepository.save(tokenom);

        return token;

    }

    public String verifClient(String telephone, String CodeApi, String Projet) {
        Clients clients = new Clients();
        Services services = new Services();
        Souscriptions souscriptions = new Souscriptions();
        Api api = new Api();

        if (clientRepository.findByTelephone(telephone) == null) {
            System.out.println("telephone n'existe pas");
            return "-2";
        } else {
            clients = clientRepository.findByTelephone(telephone);
        }
        services = serviceRepository.findServiceByCodeService();

        if (souscriptionRepository.findSouscriptByIndexClientEtServices(services.getServicesPK().getIndexe(), clients.getClientsPK().getIndexe()) == null) {
            System.out.println("le Client n'a pas souscrit");
            return "-3";
        } else {
            souscriptions = souscriptionRepository.findSouscriptByIndexClientEtServices(services.getServicesPK().getIndexe(), clients.getClientsPK().getIndexe());
        }

        if (apiRepository.findApiByCodeapiIndexapi(clients.getClientsPK().getIndexe(), CodeApi, Projet) == null) {
            System.out.println("CodeClient, CodeApi,Projet ne correspondent pas");
            return "-4";
        } else {
            api = apiRepository.findApiByCodeapiIndexapi(clients.getClientsPK().getIndexe(), CodeApi, Projet);
        }

        return "-5";
    }

    //Paiement Orange-Money --------------------------------//
    @ResponseBody
    @RequestMapping(value = "/orange-money/{Telephone}/{amount}/{codeClient}/{codeApi}/{nomProjet}/{operateur}", method = RequestMethod.POST)
    public InfoPayOrange InitOrangePayment(@PathVariable(value = "codeClient") String codeClient,
            @PathVariable(value = "codeApi") String codeApi, @PathVariable(value = "nomProjet") String nomProjet,
            @PathVariable(value = "Telephone") String Telephone,
            @PathVariable(value = "amount") String amount,
            @PathVariable(value = "operateur") String operateur, @RequestBody Pojo pojo) {
        Transtatus transtatus = new Transtatus();
        InfoPayOrange infoPayOrange = new InfoPayOrange();
        Infopayment infopayment = new Infopayment();
        Tokenom tokenom = new Tokenom();
        Partenaire partenaire = new Partenaire();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        tokenom = tokenomRepository.getOne(1);
        String curentdate = sdf.format(new Date(System.currentTimeMillis()));
        String dateExpire = sdf.format(tokenom.getExpiresIn());
        operateur = operateur.toUpperCase();
        if (dateExpire.equals(curentdate)) {
            System.out.println(curentdate + " " + dateExpire);
            System.out.println("change date");
        } else {
            System.out.println(curentdate + " " + dateExpire);
        }

        if (verifClient(codeClient, codeApi, nomProjet).equals("-2")) {
            infoPayOrange.setMessage("-2");
            return infoPayOrange;
        }
        if (verifClient(codeClient, codeApi, nomProjet).equals("-3")) {
            infoPayOrange.setMessage("-3");
            return infoPayOrange;
        }

        if (verifClient(codeClient, codeApi, nomProjet).equals("-4")) {
            infoPayOrange.setMessage("-4");
            return infoPayOrange;
        }
        if (!"ORANGE".equals(operateur)) {
            infoPayOrange.setMessage("Opérateur ne correspond pas");
            return infoPayOrange;
        }

        if (partenaireRepository.findByOmReference(nomProjet) == null) {
            partenaire.setOmReference(nomProjet.toUpperCase());
            partenaire.setOrderId(1);
            partenaireRepository.save(partenaire);
        }
        partenaire = partenaireRepository.findByOmReference(nomProjet.toUpperCase());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        pojo.setAmount(amount);
        pojo.setCurrency("XAF");
        pojo.setMerchant_key("66b0ed55");
        pojo.setReference(partenaire.getOmReference());
        pojo.setOrder_id("OII_" + partenaire.getOrderId() + partenaire.getOmReference());
        pojo.setNotif_url("http://154.72.148.105:8081/Perfectpay/rest/api/paiement/orangeResponse");
        partenaire.setOrderId(partenaire.getOrderId() + 1);
        partenaireRepository.save(partenaire);

        String url = "https://api.orange.com/orange-money-webpay/cm/v1/webpayment";
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenom.getAccessToken());
        headers.set(HttpHeaders.ACCEPT, "application/json");

        HttpEntity<Pojo> entity = new HttpEntity<>(pojo, headers);

        InfoPayOrange ipo = restTemplate.postForObject(url, entity, InfoPayOrange.class);
        transtatus.setOrderId(pojo.getOrder_id());
        transtatus.setAmount(amount);
        transtatus.setPayToken(ipo.getPay_token());
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
        infopayment.setNotifToken(ipo.getNotif_token());
        infopayment.setProjet(nomProjet);
        infopayment.setTel(Telephone);

        infopayRepository.save(infopayment);
        return ipo;
    }

    @ResponseBody
    @RequestMapping(value = "/orange-money-recharge/{Telephone}/{amount}/{codeClient}/{codeApi}/{nomProjet}/{operateur}/{compteClient}", method = RequestMethod.POST)
    public InfoPayOrange InitOrangePaymentRecharge(@PathVariable(value = "codeClient") String codeClient,
            @PathVariable(value = "codeApi") String codeApi, @PathVariable(value = "nomProjet") String nomProjet,
            @PathVariable(value = "Telephone") String Telephone,
            @PathVariable(value = "amount") String amount, @PathVariable(value = "compteClient") String compteClient,
            @PathVariable(value = "operateur") String operateur, @RequestBody Pojo pojo) {
        try {
            
        } catch (Exception e) {
        }
        Transtatus transtatus = new Transtatus();
        InfoPayOrange infoPayOrange = new InfoPayOrange();
        Infopayment infopayment = new Infopayment();
        Tokenom tokenom = new Tokenom();
        Partenaire partenaire = new Partenaire();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        tokenom = tokenomRepository.getOne(1);
        String curentdate = sdf.format(new Date(System.currentTimeMillis()));
        String dateExpire = sdf.format(tokenom.getExpiresIn());
        operateur = operateur.toUpperCase();
        if (dateExpire.equals(curentdate)) {
            System.out.println(curentdate + " " + dateExpire);
            System.out.println("change date");
        } else {
            System.out.println(curentdate + " " + dateExpire);
        }

        if (verifClient(codeClient, codeApi, nomProjet).equals("-2")) {
            infoPayOrange.setMessage("-2");
            return infoPayOrange;
        }
        if (verifClient(codeClient, codeApi, nomProjet).equals("-3")) {
            infoPayOrange.setMessage("-3");
            return infoPayOrange;
        }

        if (verifClient(codeClient, codeApi, nomProjet).equals("-4")) {
            infoPayOrange.setMessage("-4");
            return infoPayOrange;
        }
        if (!"ORANGE".equals(operateur)) {
            infoPayOrange.setMessage("Opérateur ne correspond pas");
            return infoPayOrange;
        }

        if (partenaireRepository.findByOmReference(nomProjet) == null) {
            partenaire.setOmReference(nomProjet.toUpperCase());
            partenaire.setOrderId(1);
            partenaireRepository.save(partenaire);
        }
        partenaire = partenaireRepository.findByOmReference(nomProjet.toUpperCase());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        pojo.setAmount(amount);
        pojo.setCurrency("XAF");
        pojo.setMerchant_key("66b0ed55");
        pojo.setReference(partenaire.getOmReference());
        pojo.setOrder_id("OII_" + partenaire.getOrderId() + partenaire.getOmReference());
        pojo.setNotif_url("http://154.72.148.105:8081/Perfectpay/rest/api/paiement/orangeResponseRecharge");
        partenaire.setOrderId(partenaire.getOrderId() + 1);
        partenaireRepository.save(partenaire);

        String url = "https://api.orange.com/orange-money-webpay/cm/v1/webpayment";
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenom.getAccessToken());
        headers.set(HttpHeaders.ACCEPT, "application/json");

        HttpEntity<Pojo> entity = new HttpEntity<>(pojo, headers);

        InfoPayOrange ipo = restTemplate.postForObject(url, entity, InfoPayOrange.class);

        transtatus.setOrderId(pojo.getOrder_id());
        transtatus.setAmount(amount);
        transtatus.setPayToken(ipo.getPay_token());
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
        infopayment.setNotifToken(ipo.getNotif_token());
        infopayment.setProjet(nomProjet);
        infopayment.setTel(Telephone);
        infopayment.setCompteClient(compteClient);
        infopayRepository.save(infopayment);

        return ipo;
    }

    @ResponseBody
    @RequestMapping(value = "/orangeResponse", method = RequestMethod.POST)
    public String orangeResponse(@RequestBody ResOrange resOrange) {
        System.out.println(resOrange.getStatus());
        HttpHeaders headers = new HttpHeaders();
        String body = "";
        Tokenom tokenom = new Tokenom();

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        if (resOrange.getStatus().equals("SUCCESS")) {
            Infopayment infopayment = new Infopayment();

            try {
                infopayment = infopayRepository.findByNotifToken(resOrange.getNotif_token());
                String urls = "http://154.72.148.105/apipayment/api-perfectpay.php?action=create_transaction&CodeClient=" + infopayment.getCodeClient() + "&CodeAPI=" + infopayment.getCodeAPI()
                        + "&Projet=" + infopayment.getProjet()
                        + "&Montant=" + infopayment.getMontant() + "&MoyenTransaction=" + infopayment.getMoyenTransaction() + "&Telephone=" + infopayment.getTel() + "";
                restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "1";
    }

    @ResponseBody
    @RequestMapping(value = "/orangeResponseRecharge", method = RequestMethod.POST)
    public String orangeResponseRecharge(@RequestBody ResOrange resOrange) {
        System.out.println(resOrange.getStatus());
        HttpHeaders headers = new HttpHeaders();
        String body = "";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        if (resOrange.getStatus().equals("SUCCESS")) {
            Infopayment infopayment = new Infopayment();

            try {
                infopayment = infopayRepository.findByNotifToken(resOrange.getNotif_token());
                String urls = "http://154.72.148.105/apipayment/api-perfectpay.php?action=create_transaction_recharge&CodeClient=" + infopayment.getCodeClient() + "&CodeAPI=" + infopayment.getCodeAPI()
                        + "&Projet=" + infopayment.getProjet() + "&Montant=" + infopayment.getMontant()
                        + "&MoyenTransaction=" + infopayment.getMoyenTransaction() + "&Telephone=" + infopayment.getTel() + "&Compte_client=" + infopayment.getCompteClient() + "";
                restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping(value = "/checkPayment/{paytoken}", method = RequestMethod.GET)
    public String getStatusRecharge(@PathVariable(value = "paytoken") String paytoken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.orange.com/orange-money-webpay/cm/v1/transactionstatus";
        Transtatus transtatus = new Transtatus();
        Tokenom tokenom = tokenomRepository.getOne(1);
        HttpHeaders headers = new HttpHeaders();

        if (transtatusRepository.findByPaytoken(paytoken) == null) {
            return "0";
        }
        transtatus = transtatusRepository.findByPaytoken(paytoken);
        InfoTrasaction infoTrasaction = new InfoTrasaction();
        infoTrasaction.setAmount(transtatus.getAmount());
        infoTrasaction.setOrder_id(transtatus.getOrderId());
        infoTrasaction.setPay_token(transtatus.getPayToken());
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenom.getAccessToken());
        headers.set(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<InfoTrasaction> entity = new HttpEntity<>(infoTrasaction, headers);
        OmStatus omStatus = restTemplate.postForObject(url, entity, OmStatus.class);

        return omStatus.getStatus();
    }

    //Paiemnent MOMO---------------------------------------------------------------------------------------------
    @ResponseBody
    @RequestMapping(value = "/mtn-money/{Telephone}/{amount}/{codeClient}/{codeApi}/{nomProjet}/{operateur}", method = RequestMethod.GET)
    public String Initpaiement(@PathVariable(value = "Telephone") String phonenumber, @PathVariable(value = "amount") Double amount,
            @PathVariable(value = "codeClient") String codeClient, @PathVariable(value = "codeApi") String codeApi,
            @PathVariable(value = "operateur") String operateur, @PathVariable(value = "nomProjet") String nomProjet) throws JSONException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        PojoMb pojoMb = new PojoMb();
        String etat;
        HttpHeaders headers = new HttpHeaders();

        InfoMonetbil monetbil = new InfoMonetbil();

        monetbil.setService("FwDaTg1mnf7bt9NL6xfXVHa2Eay7Yhou");

        monetbil.setPhonenumber(phonenumber);
        monetbil.setAmount(amount);
        String url = "https://api.monetbil.com/payment/v1/placePayment";
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        if (verifClient(codeClient, codeApi, nomProjet).equals("-2")) {

            return "-2";
        }
        if (verifClient(codeClient, codeApi, nomProjet).equals("-3")) {
            
            return "-3";
        }

        if (verifClient(codeClient, codeApi, nomProjet).equals("-4")) {

            return "-4";
        }
        if (!"MTN".equals(operateur.toUpperCase())) {
            return "Opérateur ne correspond pas";
        }
        HttpEntity<InfoMonetbil> entity = new HttpEntity<>(monetbil, headers);

        PojoMb p = restTemplate.postForObject(url, entity, PojoMb.class);

        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime) < 130000) {
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() - startTime);
            etat = checkPaiement(p.getPaymentId());
            System.out.println(etat);
            if (etat.equals("1")) {
                try {
                    String urls = "http://154.72.148.105/apipayment/api-perfectpay.php?action=create_transaction&CodeClient=" + codeClient + "&CodeAPI=" + codeApi + "&Projet=" + nomProjet
                            + "&Montant=" + amount + "&MoyenTransaction=" + operateur + "&Telephone=" + phonenumber + "";

                    restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);

                } catch (Exception e) {
                }

                return etat;
            } else if (etat.equals("-1")) {
                return etat;
            } else if (etat.equals("0")) {
                return etat;
            }
        }

        return "";

    }

    @ResponseBody
    @RequestMapping(value = "/mtn-money-recharge/{Telephone}/{amount}/{codeClient}/{codeApi}/{nomProjet}/{operateur}/{compteClient}", method = RequestMethod.GET)
    public String InitpaiementRecharge(@PathVariable(value = "Telephone") String phonenumber, @PathVariable(value = "amount") Double amount,
            @PathVariable(value = "codeClient") String codeClient, @PathVariable(value = "codeApi") String codeApi,
            @PathVariable(value = "operateur") String operateur, @PathVariable(value = "nomProjet") String nomProjet, @PathVariable(value = "compteClient") String compteClient)
            throws JSONException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        PojoMb pojoMb = new PojoMb();
        String etat;
        HttpHeaders headers = new HttpHeaders();

        InfoMonetbil monetbil = new InfoMonetbil();

        monetbil.setService("FwDaTg1mnf7bt9NL6xfXVHa2Eay7Yhou");

        monetbil.setPhonenumber(phonenumber);
        monetbil.setAmount(amount);
        String url = "https://api.monetbil.com/payment/v1/placePayment";
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

      if (verifClient(codeClient, codeApi, nomProjet).equals("-2")) {

            return "-2";
        }
        if (verifClient(codeClient, codeApi, nomProjet).equals("-3")) {
            
            return "-3";
        }

        if (verifClient(codeClient, codeApi, nomProjet).equals("-4")) {

            return "-4";
        }
        if (!"MTN".equals(operateur.toUpperCase())) {
            return "Opérateur ne correspond pas";
        }
        HttpEntity<InfoMonetbil> entity = new HttpEntity<>(monetbil, headers);

        PojoMb p = restTemplate.postForObject(url, entity, PojoMb.class);

        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime) < 130000) {
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() - startTime);
            etat = checkPaiement(p.getPaymentId());
            System.out.println(etat);
            if (etat.equals("1")) {
                try {
                    String urls = "http://154.72.148.105/apipayment/api-perfectpay.php?action=create_transaction_recharge&CodeClient=" + codeClient + "&CodeAPI=" + codeApi + "&Projet=" + nomProjet + ""
                            + "&Montant=" + amount + "&MoyenTransaction=" + operateur + "&Telephone=" + phonenumber + "&Compte_client=" + compteClient + "";

                    ResponseEntity<String> response = restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);
                    System.out.println(response);

                } catch (Exception e) {
                    e.printStackTrace();
                    return e.getMessage();
                }

                return etat;
            } else if (etat.equals("-1")) {
                return etat;
            } else if (etat.equals("0")) {
                return etat;
            }
        }

        return "";

    }

    public String checkPaiement(String paymentId) throws JSONException, ProtocolException, IOException {

        String urlCheck = "https://api.monetbil.com/payment/v1/checkPayment";//placePayment";checkPayment
        String etat = "";
        URL myURL;
        try {
            myURL = new URL(urlCheck);

            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "xxxxxxxxx");

            String urlParameters = "paymentId=" + paymentId;

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));

            connection.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            String jsonData = "";
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                jsonData += line + "\n";
                response.append(line);
                response.append('\r');
            }
            rd.close();
//            System.out.println(response.toString());
//            System.out.println(jsonData);
            JSONObject obj = null;
            String msg = "";
            String status = "";
            String device = "";

            try {
                obj = new JSONObject(jsonData);
                msg = obj.getString("message");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            if (msg.equals("payment finish")) {
                try {
                    status = obj.getJSONObject("transaction").getString("status");
                    device = obj.getJSONObject("transaction").getString("device");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            if (status.equals("1")) {
                System.out.println("status1 : " + status);
                return "1";
            } else if (status.equals("-1")) {

                System.out.println("status2 : " + status);
                return "-1";
            } else if (status.equals("0")) {
                System.out.println("status2 : " + status);
                return "0";
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    //Paiement Paypal ------------------------------------------------------------------------------------------------
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "paypal")
    public HashMap pay(@RequestBody InfoPayPapal infoPayPapal) {
        HashMap<String, String> lienpay = new HashMap<String, String>();

        if (verifClient(infoPayPapal.getCodeClient(), infoPayPapal.getCodeApi(), infoPayPapal.getProjet()).equals("-2")) {
            
            lienpay.put("redirect", "-2");
            return lienpay;
        }
          if (verifClient(infoPayPapal.getCodeClient(), infoPayPapal.getCodeApi(), infoPayPapal.getProjet()).equals("-3")) {
            
            lienpay.put("redirect", "-3");
            return lienpay;
        }
           if (verifClient(infoPayPapal.getCodeClient(), infoPayPapal.getCodeApi(), infoPayPapal.getProjet()).equals("-4")) {
            
            lienpay.put("redirect", "-4");
            return lienpay;
        }
        String cancelUrl = infoPayPapal.getUrl_cancel();
        String successUrl = "http://154.72.148.105:8082/Perfectpay/rest/api/paiement/ConfirmPay?url_return=" + infoPayPapal.getUrl_return()
                + "&codeClient=" + infoPayPapal.getCodeClient() + "&codeApi=" + infoPayPapal.getCodeApi() + "&Projet=" + infoPayPapal.getProjet()
                + "&moyenTransaction=" + infoPayPapal.getMoyenTransaction() + "&compteClient=" + infoPayPapal.getCompteClient() + "&cancel_url=" + infoPayPapal.getUrl_cancel() + "&amount=" + infoPayPapal.getAmount() + "";
                           System.out.println(infoPayPapal.getAmount());

        try {
            Payment payment = paypalService.createPayment(
                    5.5,                  
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    lienpay.put("redirect", links.getHref());
                    return lienpay;
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        lienpay.put("redirect", "empty");
        return lienpay;

    }

    @RequestMapping(method = RequestMethod.GET, value = "ConfirmPay")
    public String successPay(@RequestParam("url_return") String url_return, @RequestParam("codeClient") String codeClient,
            @RequestParam("codeApi") String codeApi, @RequestParam("Projet") String Projet, @RequestParam("moyenTransaction") String moyenTransaction,
            @RequestParam("compteClient") String compteClient, @RequestParam("cancel_url") String cancel_url,
            @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("amount") Double amount) {
        InfoPayOrange payOrange = new InfoPayOrange();
        RestTemplate restTemplate = new RestTemplate();
        InfoPayPapal infoPayPapal = new InfoPayPapal();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<InfoPayPapal> entity = new HttpEntity<>(infoPayPapal, headers);

        try {

            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {

                System.out.print(payment.getState());
                System.out.print("idpayer :" + payerId + " paymentId" + paymentId);
                System.out.print(codeApi);
                String urls = "http://154.72.148.105/apipayment/api-perfectpay.php?action=create_transaction_recharge_paypal&CodeClient=" + codeClient + "&CodeAPI=" + codeApi + "&Projet=" + Projet + ""
                        + "&Montant=" + amount + "&MoyenTransaction=" + moyenTransaction + "&Compte_client=" + compteClient + "";

                ResponseEntity<String> response = restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);
                System.out.println(response);
                return "redirect:" + url_return;

            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:" + cancel_url;
    }

    //**********************************Pour autre Developpeurs voulant intégré paypal
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "checkPaypal")
    public String checkPaypa(@RequestBody InfoPayPapal ipp,
            @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        InfoPayOrange payOrange = new InfoPayOrange();
        RestTemplate restTemplate = new RestTemplate();
        InfoPayPapal infoPayPapal = new InfoPayPapal();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<InfoPayPapal> entity = new HttpEntity<>(infoPayPapal, headers);
        if (verifClient(ipp.getCodeClient(), ipp.getCodeApi(), ipp.getProjet()).equals("-2")) {

            return "-2";
        }
        if (verifClient(ipp.getCodeClient(), ipp.getCodeApi(), ipp.getProjet()).equals("-3")) {

            return "-3";
        }
         if (verifClient(ipp.getCodeClient(), ipp.getCodeApi(), ipp.getProjet()).equals("-3")) {

            return "-4";
        }
        try {

            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {

                System.out.print(payment.getState());
                System.out.print("idpayer :" + payerId + " paymentId" + paymentId);
                System.out.print(ipp.getCodeApi());
                String urls = "http://154.72.148.105/apipayment/api-perfectpay.php?action=create_transaction_recharge_paypal&CodeClient=" + ipp.getCodeClient()
                        + "&CodeAPI=" + ipp.getCodeApi() + "&Projet=" + ipp.getProjet() + ""
                        + "&Montant=" + ipp.getAmount() + "&MoyenTransaction=" + ipp.getMoyenTransaction() + "&Compte_client=" + ipp.getCompteClient() + "";

                ResponseEntity<String> response = restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);
                System.out.println(response);
                return "1";

            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "0";
    }

//    @RequestMapping(value = "/stripePayment/{paymentId}/{amount}", method = RequestMethod.GET)
//    public String stripePayment(@PathVariable("paymentId") String paymentId, @PathVariable("amount") String amount) throws StripeException {
//
//        Stripe.apiKey = "sk_live_jMmaSoLH8mWwW34XPkZS5bXh";
//
//        Map<String, Object> chargeParams = new HashMap<String, Object>();
//        Float val = Float.valueOf(amount);
//        val = val * 100;
//        chargeParams.put("amount", val.longValue());
//        chargeParams.put("currency", "usd");
//        chargeParams.put("source", paymentId);
//        Charge charge;
//
//        try {
//            charge = Charge.create(chargeParams);
//            System.err.println(charge.getStatus());
//            if (charge.getStatus().equals("succeeded")) {
//                System.err.println("paymentId==============================================================" + paymentId);
//                System.err.println("amount==============================================================" + amount);
//
//                return "1";
//            } else {
//                return "-1";
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "-2";
//    }
//       
//  
    //paiement UBA --------------------------------------------------------------------------------------------
    public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception {
        return (org.apache.axis2.databinding.ADBBean) type.newInstance();
    }

    @ResponseBody
    @RequestMapping(value = "fundTransfertCardToCard/{userId}/{paymentType}/{amount}/{toCustomerId}/{currencyCode}/{last4Digits}", method = RequestMethod.GET)
    public Integer fundTransfertCardToCard(@PathVariable("userId") Integer userId, @PathVariable("paymentType") String paymentType,
            @PathVariable("amount") BigDecimal amount, @PathVariable("toCustomerId") Integer toCustomerId, @PathVariable("currencyCode") String currencyCode, @PathVariable("last4Digits") String last4Digits) {
        try {
//            partenaire = partenaireService.find(PartenaireInfo.code);
//            Client client = clientService.findClient(userId);
//            if (client.getCustomerID() == null) {
//                return Response.ok(-4).build();
//            }

            this.stub = new AllServicesStub();
            this.requestHeader = (RequestHeader) getTestObject(RequestHeader.class);

            BaseRequestHeader brh = new BaseRequestHeader();
            brh.setSecurityToken("UBCMKAKOTELq2ervuih1397fh1095437fh139pgv");
            brh.setRequestID("202");
            this.requestHeader.setRequestHeader(brh);
            BaseRequest baseRequest = (BaseRequest) getTestObject(BaseRequest.class
            );
            FundTransferCardToCardRequest fundTransferCardToCardRequest = new FundTransferCardToCardRequest();
            fundTransferCardToCardRequest.setFromCustomerID(3929709);
            fundTransferCardToCardRequest.setToCustomerID(toCustomerId);
            fundTransferCardToCardRequest.setLast4Digits(last4Digits);
            fundTransferCardToCardRequest.setPaymentType(paymentType);
            fundTransferCardToCardRequest.setTransferAmount(amount);
            String source = "XAF";
            fundTransferCardToCardRequest.setCurrencyCode("XAF");
            baseRequest.setRequestData(fundTransferCardToCardRequest);

            BaseResponse response = stub.submit(baseRequest, requestHeader);
            //response.ge
            if (response.getResponseData() instanceof FundTransferResponse) {
                FundTransferResponse ftr = (FundTransferResponse) response.getResponseData();
                System.out.println("Good");
                System.out.println(ftr.getTransactionID());
            } else if (response.getResponseData() instanceof ErrorResponse) {
                ErrorResponse er = (ErrorResponse) response.getResponseData();
                System.out.println(er.getErrorNumber());
                System.out.println(er.getErrorMessage());
                return -2;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
            return -1;

        }
        BalanceRequest balanceR = new BalanceRequest();

        return 0;
    }

////// 
    @RequestMapping(value = "fundTransfertToCard/{userId}/{paymentType}/{amount}/{toCustomerId}/{currencyCode}/{last4Digits}/{phone}", method = RequestMethod.GET)
    public Integer fundTransfertToCard(@PathVariable("userId") Integer userId, @PathVariable("paymentType") String paymentType, @PathVariable("amount") BigDecimal amount,
            @PathVariable("toCustomerId") Integer toCustomerId, @PathVariable("currencyCode") String currencyCode, @PathVariable("last4Digits") String last4Digits,
            @PathVariable("phone") String phone) {
        try {
            
            BaseRequestHeader brh = new BaseRequestHeader();
            brh.setSecurityToken("");
            brh.setRequestID("" + 202);
            this.requestHeader.setRequestHeader(brh);
            com.payment.pay.gtplimited.BaseRequest baseRequest = (com.payment.pay.gtplimited.BaseRequest) getTestObject(com.payment.pay.gtplimited.BaseRequest.class);
            FundTransferCardToCardRequest fundTransferCardToCardRequest= new FundTransferCardToCardRequest();
            FundTransferRequest fundTransferRequest = new FundTransferRequest();
            fundTransferRequest.setCustomerID(toCustomerId);
            fundTransferRequest.setLast4Digits(last4Digits);
            fundTransferRequest.setTransferType("WalletToCard");
            fundTransferRequest.setTransferAmount(amount);
            fundTransferRequest.setReferenceMemo("Recharge de la carte cliente : " + userId);

            String source = "XOF";
            fundTransferRequest.setCurrencyCode("XAF");
            baseRequest.setRequestData(fundTransferRequest);
//            Client client = clientService.findClient(userId);
//            double solde = operationsService.getSoldeClient(client.getIdClient());
            double dollarAmount = amount.doubleValue();
            if (currencyCode.equals("DOLLAR")) {
                source = "USD";
                dollarAmount = amount.doubleValue();
            }
            if (currencyCode.equals("EURO")) {
                source = "EUR";
                dollarAmount = amount.doubleValue() / 1.32965;
            }
            if (currencyCode.equals("FCFA")) {
                source = "XOF";
                dollarAmount = amount.doubleValue() / 1.32965;
            }
            if (currencyCode.equals("CAD")) {
                source = "CAD";
                dollarAmount = amount.doubleValue() / 1.32965;
            }
            if (currencyCode.equals("YUAN")) {
                source = "CNY";
                dollarAmount = amount.doubleValue() / 1.32965;
            }
            if (currencyCode.equals("Livre sterling")) {
                source = "GBP";
                dollarAmount = amount.doubleValue() / 1.32965;
            }
            if (currencyCode.equals("DIRHAM")) {
                source = "AED";
                dollarAmount = amount.doubleValue() / 1.32965;
            }
            if (currencyCode.equals("RAND")) {
                source = "ZAR";
                dollarAmount = amount.doubleValue() / 1.32965;
            }
            if (currencyCode.equals("NAIRA")) {
                source = "NGN";
                dollarAmount = amount.doubleValue() / 1.32965;
            }

           // if (solde > (amount.doubleValue() /  1.32965)) {
                BaseResponse response = stub.submit(baseRequest, requestHeader);
                //response.ge
                if (response.getResponseData() instanceof FundTransferResponse) {
                    FundTransferResponse ftr = (FundTransferResponse) response.getResponseData();
                   // this.crediter(phone, phone, dollarAmount, "retrait");
                    System.out.println("Good");
                    System.out.println(ftr.getTransactionID());
                } else if (response.getResponseData() instanceof ErrorResponse) {
                    ErrorResponse er = (ErrorResponse) response.getResponseData();
                    System.out.println(er.getErrorNumber());
                    System.out.println(er.getErrorMessage());
                    return -2;

                }
//            } else {
//                return -3;
//            }
        } catch (Exception e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
            return -1;

        }
        BalanceRequest balanceR = new BalanceRequest();

        return 0;
    }
//    public int crediter(String numeroClient, String numeroDeposant, double montant, String type) {
//        Operations operation = new Operations();
//        BigDecimal amount = (new BigDecimal(montant)).setScale(4, BigDecimal.ROUND_DOWN);
//        montant = amount.doubleValue();
//        System.err.println(montant);
//        if (numeroClient.equals(numeroDeposant)) {
//            Client clientDeposant = clientService.findByTelephone(numeroDeposant);
//            double solde = operationsService.getSoldeClient(clientDeposant.getIdClient());
//            if (type.equals("credit")) {
//                operation.setMontant(montant);
//            } else if (type.equals("debit") || type.equals("retrait")) {
//                if (solde < (montant + calculerCommission(montant))) {
//                    return -1;
//                }
//                operation.setMontant(-(montant + calculerCommission(montant)));
//            }
//            operation.setIdClient(clientDeposant.getIdClient());
//            operation.setIdDeposant(clientDeposant.getIdClient());
//            operation.setType(type);
//            operation.setDate(Calendar.getInstance().getTime());
//            operationsService.create(operation);
//            return 0;
//        } else {
//            Client client = clientService.findByTelephone(numeroClient);
//            Client clientDeposant = clientService.findByTelephone(numeroDeposant);
//            double solde = operationsService.getSoldeClient(clientDeposant.getIdClient());
//            this.em.getTransaction().begin();
//            try {
//                operation = new Operations();
//                if (solde < (montant + calculerCommission(montant))) {
//                    return -1;
//                }
//                operation.setIdClient(client.getIdClient());
//                operation.setIdDeposant(clientDeposant.getIdClient());
//                operation.setType(type);
//                operation.setDate(Calendar.getInstance().getTime());
//                if (type.equals("credit")) {
//                    operation.setMontant(montant);
//                } else if (type.equals("debit")) {
//                    operation.setMontant(-montant);
//                }
//                operationsService.create(operation);
//                operation = new Operations();
//                operation.setIdClient(clientDeposant.getIdClient());
//                operation.setIdDeposant(clientDeposant.getIdClient());
//                operation.setDate(Calendar.getInstance().getTime());
//                if (type.equals("credit")) {
//                    operation.setMontant(-(montant + calculerCommission(montant)));
//                    operation.setType("debit");
//                } else if (type.equals("debit")) {
//                    operation.setMontant((montant + calculerCommission(montant)));
//                    operation.setType("credit");
//                }
//                operationsService.create(operation);
//                this.em.persist(operation); 
//            } catch (Exception ex) {
//                this.em.getTransaction().rollback();
//            }
//            this.em.getTransaction().commit();
//            return 0;
//        }
//    
//    }
    
    
    
     @ResponseBody
    @RequestMapping(value = "/testGet", method = RequestMethod.GET)
    public HashMap TestGet() {
       HashMap hashMap=new HashMap();
         try {
         hashMap.put("message", "Get Request");
         hashMap.put("Status", "OK");
         } catch (Exception e) {
              hashMap.put("message", "Get Request");
                hashMap.put("Status", e.getMessage());
             return hashMap;
         }
      
        return hashMap;
    }
    
     @ResponseBody
    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
    public HashMap getTestPost() {
       HashMap hashMap=new HashMap();
         try {
         hashMap.put("message", "Post Request");
         hashMap.put("Status", "OK");
         } catch (Exception e) {
              hashMap.put("message", "Post Request");
                hashMap.put("Status", e.getMessage());
             return hashMap;
         }
      
        return hashMap;
    }

}
