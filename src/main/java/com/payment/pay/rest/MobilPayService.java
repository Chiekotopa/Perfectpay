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
import com.payment.pay.dao.ApiperfectRepository;
import com.payment.pay.dao.ClientRepository;
import com.payment.pay.dao.DeviseMonaieRepository;
import com.payment.pay.dao.InfopayRepository;
import com.payment.pay.dao.PartenaireRepository;
import com.payment.pay.dao.ServiceRepository;
import com.payment.pay.dao.SessiontransRepository;
import com.payment.pay.dao.SouscriptionRepository;
import com.payment.pay.dao.TokenomRepository;
import com.payment.pay.dao.TranstatusRepository;
import com.payment.pay.entities.InfoPayMtn;
import com.payment.pay.entities.InfoPayOrange;
import com.payment.pay.entities.InfoPayPapal;
import com.payment.pay.entities.InfoToken;
import com.payment.pay.entities.InfoTrasaction;
import com.payment.pay.entities.ObjectToUrlEncodedConverter;
import com.payment.pay.entities.OmStatus;
import com.payment.pay.entities.Pojo;
import com.payment.pay.entities.ResOrange;
import com.payment.pay.entities.ResOrangeUpdate;
import com.payment.pay.entities.Responses;
import com.payment.pay.entities.Token;
import com.payment.pay.entitybd.Api;
import com.payment.pay.entitybd.Apiperfectpay;
import com.payment.pay.entitybd.Clients;
import com.payment.pay.entitybd.Devisemonaies;
import com.payment.pay.entitybd.Infopayment;
import com.payment.pay.entitybd.Partenaire;
import com.payment.pay.entitybd.Services;
import com.payment.pay.entitybd.Sessiontrans;
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
import com.payment.pay.service.MultiThread;
import com.payment.pay.service.MutiThreadMarchand;
import com.payment.pay.service.OmService;
import com.payment.pay.service.PaypalService;
import com.payment.pay.service.UssdService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

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
    ApiperfectRepository apiperfectRepository;

    @Autowired
    ApiRepository apiRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    SouscriptionRepository souscriptionRepository;

    @Autowired
    TranstatusRepository transtatusRepository;

    @Autowired
    DeviseMonaieRepository deviseMonaieRepository;

    @Autowired
    InfopayRepository infopayRepository;

    @Autowired
    private PaypalService paypalService;

    @Autowired
    MultiThread multiThread1;

    @Autowired
    SessiontransRepository sessiontransRepository;

    @Autowired
    UssdService ussdservice;

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
            getToken();
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

    //paiement Orange money pour Gedomed
    @ResponseBody
    @RequestMapping(value = "/orange-money-gedomed/{Telephone}/{amount}/{codeClient}/{codeApi}/{nomProjet}/{operateur}/{index}", method = RequestMethod.POST)
    public InfoPayOrange InitOrangePaymentGedomed(@PathVariable(value = "codeClient") String codeClient,
            @PathVariable(value = "codeApi") String codeApi, @PathVariable(value = "nomProjet") String nomProjet,
            @PathVariable(value = "Telephone") String Telephone,
            @PathVariable(value = "amount") String amount,
            @PathVariable(value = "operateur") String operateur,
            @PathVariable(value = "index") String index,
            @RequestBody Pojo pojo) {
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
            getToken();
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
        pojo.setNotif_url("http://154.72.148.105:8081/Perfectpay/rest/api/paiement/orangeResponseGedomed");
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

        infopayment.setIndexpay(index);
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
            getToken();
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
                String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction&CodeClient=" + infopayment.getCodeClient() + "&CodeAPI=" + infopayment.getCodeAPI()
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
    @RequestMapping(value = "/orangeResponseUpdate", method = RequestMethod.POST)
    public String orangeResponseUpdate(@RequestBody ResOrangeUpdate resOrange) {
        System.out.println(resOrange.getStatus());
        HttpHeaders headers = new HttpHeaders();
        String body = "";
        
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        if (resOrange.getStatus().equals("SUCCESSFULL")) {
            Infopayment infopayment = new Infopayment();

            try {
                infopayment = infopayRepository.findByNotifToken(resOrange.getPayToken());
                String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction&CodeClient=" + infopayment.getCodeClient() + "&CodeAPI=" + infopayment.getCodeAPI()
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
    @RequestMapping(value = "/orangeResponseGedomed", method = RequestMethod.POST)
    public String orangeResponseGedomed(@RequestBody ResOrange resOrange) {
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
                String urls = "https://gedomed.com/api.php?action=PayerAbonnement&Indexe_abonnement=" + infopayment.getIndexpay() + "&MoyenPaiement=Orange&montant=" + infopayment.getMontant() + "";
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
                String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction_recharge&CodeClient=" + infopayment.getCodeClient() + "&CodeAPI=" + infopayment.getCodeAPI()
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
                    String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction&CodeClient=" + codeClient + "&CodeAPI=" + codeApi + "&Projet=" + nomProjet
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

    //paiement MOMO pour GEDOMED
    @ResponseBody
    @RequestMapping(value = "/mtn-money-gedomed/{Telephone}/{amount}/{codeClient}/{codeApi}/{nomProjet}/{operateur}/{index}", method = RequestMethod.GET)
    public String InitpaiementGedomed(@PathVariable(value = "Telephone") String phonenumber, @PathVariable(value = "amount") Double amount,
            @PathVariable(value = "codeClient") String codeClient, @PathVariable(value = "codeApi") String codeApi,
            @PathVariable(value = "operateur") String operateur, @PathVariable(value = "nomProjet") String nomProjet,
            @PathVariable(value = "index") String index) throws JSONException, IOException {
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
                    String urls = "https://gedomed.com/api.php?action=PayerAbonnement&Indexe_abonnement=" + index + "&MoyenPaiement=Mtn&montant=" + amount + "";

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
                    String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction_recharge&CodeClient=" + codeClient + "&CodeAPI=" + codeApi + "&Projet=" + nomProjet + ""
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

    //Paypal Paiement  ------------------------------------------------------------------------------------------------
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "paypalPayment")
    public HashMap paypalPayment(@RequestBody InfoPayPapal infoPayPapal) {
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
        String successUrl = "http://154.72.148.105:8081/Perfectpay/rest/api/paiement/ConfirmPayment?url_return=" + infoPayPapal.getUrl_return()
                + "&codeClient=" + infoPayPapal.getCodeClient() + "&codeApi=" + infoPayPapal.getCodeApi() + "&Projet=" + infoPayPapal.getProjet()
                + "&moyenTransaction=" + infoPayPapal.getMoyenTransaction() + "&compteClient=" + infoPayPapal.getCompteClient() + "&cancel_url=" + infoPayPapal.getUrl_cancel() + "&amount=" + infoPayPapal.getAmount()
                + "&telephone=" + infoPayPapal.getPhone() + "";
        System.out.println(infoPayPapal.getAmount());

        try {
            Payment payment = paypalService.createPayment(
                    infoPayPapal.getAmount(),
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

    @RequestMapping(method = RequestMethod.GET, value = "ConfirmPayment")
    public String successPayment(@RequestParam("url_return") String url_return, @RequestParam("codeClient") String codeClient,
            @RequestParam("codeApi") String codeApi, @RequestParam("Projet") String Projet, @RequestParam("moyenTransaction") String moyenTransaction,
            @RequestParam("compteClient") String compteClient, @RequestParam("cancel_url") String cancel_url,
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId, @RequestParam("amount") Double amount, @RequestParam("telephone") String telephone) {
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
                String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction&CodeClient=" + codeClient + "&CodeAPI=" + codeApi + "&Projet=" + Projet
                        + "&Montant=" + amount * 540.18 + "&MoyenTransaction=Paypal&Telephone=" + telephone + "";
                ResponseEntity<String> response = restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);
                System.out.println(response);
                return "redirect:" + url_return;

            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:" + cancel_url;
    }

    // Paypal Recharge  ------------------------------------------------------------------------------------------------
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
        String successUrl = "http://154.72.148.105:8081/Perfectpay/rest/api/paiement/ConfirmPay?url_return=" + infoPayPapal.getUrl_return()
                + "&codeClient=" + infoPayPapal.getCodeClient() + "&codeApi=" + infoPayPapal.getCodeApi() + "&Projet=" + infoPayPapal.getProjet()
                + "&moyenTransaction=" + infoPayPapal.getMoyenTransaction() + "&compteClient=" + infoPayPapal.getCompteClient() + "&cancel_url=" + infoPayPapal.getUrl_cancel() + "&amount=" + infoPayPapal.getAmount() + "";
        System.out.println(infoPayPapal.getAmount());

        try {
            Payment payment = paypalService.createPayment(
                    infoPayPapal.getAmount(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    infoPayPapal.getUrl_return());

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
                String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction_recharge_paypal&CodeClient=" + codeClient + "&CodeAPI=" + codeApi + "&Projet=" + Projet + ""
                        + "&Montant=" + amount * 540.18 + "&MoyenTransaction=" + moyenTransaction + "&Compte_client=" + compteClient + "";

                ResponseEntity<String> response = restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);
                System.out.println(response);
                return "redirect:" + url_return;

            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:" + cancel_url;
    }

    //******************Paypal for GEDOMED   
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "paypalGedomed/{index}")
    public HashMap payGedomed(@RequestBody InfoPayPapal infoPayPapal, @PathVariable(value = "index") String index) {
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
        String successUrl = "http://154.72.148.105:8081/Perfectpay/rest/api/paiement/ConfirmPayGedomed?url_return=" + infoPayPapal.getUrl_return()
                + "&codeClient=" + infoPayPapal.getCodeClient() + "&codeApi=" + infoPayPapal.getCodeApi() + "&Projet=" + infoPayPapal.getProjet()
                + "&moyenTransaction=" + infoPayPapal.getMoyenTransaction() + "&compteClient=" + infoPayPapal.getCompteClient() + "&cancel_url=" + infoPayPapal.getUrl_cancel() + "&amount=" + infoPayPapal.getAmount()
                + "&index=" + index + "";
        System.out.println(infoPayPapal.getAmount());

        try {
            Payment payment = paypalService.createPayment(
                    infoPayPapal.getAmount(),
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

    @RequestMapping(method = RequestMethod.GET, value = "ConfirmPayGedomed")
    public String successPayGedomed(@RequestParam("url_return") String url_return, @RequestParam("codeClient") String codeClient,
            @RequestParam("codeApi") String codeApi, @RequestParam("Projet") String Projet, @RequestParam("moyenTransaction") String moyenTransaction,
            @RequestParam("compteClient") String compteClient, @RequestParam("cancel_url") String cancel_url,
            @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("amount") Double amount, @RequestParam("index") String index) {
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
                String urls = "https://gedomed.com/api.php?action=PayerAbonnement&Indexe_abonnement=" + index + "&MoyenPaiement=Paypal&montant=" + amount * 540.18 + "";

                ResponseEntity<String> response = restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);
                System.out.println(response);
                return "redirect:" + url_return;

            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:" + cancel_url;
    }

    //**********************************Pour autres Developpeurs voulant intégré paypal
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "checkPaypal/{CodeClient}/{CodeAPI}/{Projet}/{Montant}/{MoyenTransaction}/{CompteClient}")
    public String checkPaypa(
            @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable("CodeClient") String CodeClient, @PathVariable("CodeAPI") String CodeAPI,
            @PathVariable("Projet") String Projet, @PathVariable("Montant") Double Montant,
            @PathVariable("MoyenTransaction") String MoyenTransaction, @PathVariable("CompteClient") String CompteClient) {
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

                String urls = "https://api.kakotel.com/api-perfectpay.php?action=create_transaction_recharge_paypal&CodeClient=" + CodeClient
                        + "&CodeAPI=" + CodeAPI + "&Projet=" + Projet + ""
                        + "&Montant=" + Montant * 540.18 + "&MoyenTransaction=" + MoyenTransaction + CompteClient + "";

                ResponseEntity<String> response = restTemplate.exchange(urls, HttpMethod.GET, entity, String.class);
                System.out.println(response);
                return "1";

            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "0";
    }

    //Api debit du client via perfectPay*******************************************************
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "initDebitClientPefectPay")
    public Responses initTransDebitClientPefectPay(@RequestBody Sessiontrans sessiontrans) {
        Responses responses = new Responses();
        if (ussdservice.checkerCompteClientetrait(sessiontrans.getPhoneagent(), sessiontrans.getPhonedestinataire()) != null) {
            responses = ussdservice.checkerCompteClientetrait(sessiontrans.getPhoneagent(), sessiontrans.getPhonedestinataire());
            if (responses.getSucces() == -2) {

                return responses;
            }
            if (responses.getSucces() == -1) {

                return responses;
            }
        }

        if (ussdservice.checkerSoldeExpediteurRetrait(sessiontrans.getPhoneagent(), sessiontrans.getPhonedestinataire(), sessiontrans.getMontant()) != null) {
            responses = ussdservice.checkerSoldeExpediteurRetrait(sessiontrans.getPhoneagent(), sessiontrans.getPhonedestinataire(), sessiontrans.getMontant());
            if (responses.getSucces() == -2) {

                return responses;
            }

        }
        if (ussdservice.validationInitilisationRretraitAccountPerfectPay(sessiontrans.getPhoneagent(), sessiontrans.getPhonedestinataire(), sessiontrans.getMontant(), sessiontrans.getCodesecret()) != null) {
            responses = ussdservice.validationInitilisationRretraitAccountPerfectPay(sessiontrans.getPhoneagent(), sessiontrans.getPhonedestinataire(), sessiontrans.getMontant(), sessiontrans.getCodesecret());
            if (responses.getSucces() == -2) {
                responses.setMsg("Votre code secret est incorrect");
                return responses;
            }

            if (responses.getSucces() == 1) {
                sessiontrans.setPhonedestinataire("237" + sessiontrans.getPhonedestinataire());
                sessiontrans.setStatus("1");
                sessiontrans.setTread("1");
                sessiontrans.setDate(new Date(System.currentTimeMillis()));
                sessiontrans.setType("0");
                sessiontransRepository.save(sessiontrans);
                MultiThread multiThread = new MultiThread(sessiontransRepository);
                multiThread.setphone(sessiontrans.getPhonedestinataire());
                multiThread.setphoneExp(sessiontrans.getPhoneagent());
                multiThread.start();
                return responses;
            }

        }

        return responses;
    }

    //Api debit par Marchand  via perfectPay*******************************************************
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "initDebitMarchandPefectPay")
    public Responses initDebitMarchandPefectPay(@RequestBody Sessiontrans sessiontrans) {
        Responses responses = new Responses();
        try {
            sessiontrans.setPhoneagent("237" + sessiontrans.getPhoneagent());
            sessiontrans.setPhonedestinataire("237" + sessiontrans.getPhonedestinataire());
            sessiontrans.setStatus("1");
            sessiontrans.setTread("1");
            sessiontrans.setDate(new Date(System.currentTimeMillis()));
            sessiontrans.setType("1");
            sessiontransRepository.save(sessiontrans);
            MultiThread multiThread = new MultiThread(sessiontransRepository);
            multiThread.setphone(sessiontrans.getPhonedestinataire());
            multiThread.setphoneExp(sessiontrans.getPhoneagent());
            multiThread.start();
            responses.setMsg("paiement initialisé ");
            responses.setSucces(1);
            return responses;
        } catch (Exception e) {
            responses.setMsg("Error");
            responses.setSucces(0);
            return responses;
        }

    }

    //Api integrations paiement perfectPay *************************************************************
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "initPaymentPerfectpay")
    public HashMap initPaymentPerfectpay(@RequestBody Apiperfectpay apiperfectpay) {
        HashMap map = new HashMap();
        Sessiontrans sessiontrans = new Sessiontrans();
        try {
            String resp = verifClient(apiperfectpay.getCodeclient(), apiperfectpay.getCodeapi(), apiperfectpay.getProjet());
            if (resp.equals("-2")) {
                map.put("msg", "Code Client n'existe pas");
                map.put("response", -2);
                return map;
            }

            if (resp.equals("-3")) {
                map.put("msg", "Vous n'avez pas souscrit au service API");
                map.put("response", -3);
                return map;
            }

            if (resp.equals("-3")) {
                map.put("msg", "CodeClient, CodeApi,Projet ne correspondent pas");
                map.put("response", -4);
                return map;
            }

            Responses responses = ussdservice.checkerCompteClientetrait(apiperfectpay.getCodeclient(), apiperfectpay.getTeldestinataire());
            if (responses.getSucces() == -2) {
                map.put("msg", responses.getMsg());
                map.put("response", -5);
                return map;
            }
            if (responses.getSucces() == -1) {
                map.put("msg", responses.getMsg());
                map.put("response", -6);
                return map;
            }

            responses = ussdservice.checkerSoldeExpediteurRetrait(apiperfectpay.getCodeclient(), apiperfectpay.getTeldestinataire(), apiperfectpay.getMontant());
            if (responses.getSucces() == -2) {
                map.put("msg", responses.getMsg());
                map.put("response", -7);
                return map;
            }
            sessiontrans.setPhoneagent("237" + apiperfectpay.getCodeclient());
            sessiontrans.setPhonedestinataire("237" + apiperfectpay.getTeldestinataire());
            sessiontrans.setStatus("1");
            sessiontrans.setTread("1");
            sessiontrans.setDate(new Date(System.currentTimeMillis()));
            sessiontrans.setType("1");
            sessiontransRepository.save(sessiontrans);
            String key = CreateUniKey();
            apiperfectpay.setIdtransaction(key);
            apiperfectpay.setStatus("ATTENTE");
            apiperfectRepository.save(apiperfectpay);
            MutiThreadMarchand multiThread = new MutiThreadMarchand(sessiontransRepository, apiperfectRepository);
            multiThread.setphone(sessiontrans.getPhonedestinataire());
            multiThread.setphoneExp(sessiontrans.getPhoneagent());
            multiThread.setKey(key);
            multiThread.start();

            map.put("Idtransaction", key);
            map.put("Status", "ATTENTE");

            return map;

        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("response", 0);
            return map;
        }

    }

    public static String CreateUniKey() {
        return UUID.randomUUID().toString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "checkPaymentPerfectpay/{idtransaction}")
    public HashMap checkPaymentPerfectpay(@PathVariable(value = "idtransaction") String idtransaction) {
        HashMap map = new HashMap();
        Apiperfectpay apiperfectpay = new Apiperfectpay();
        try {
            if (sessiontransRepository.findeByIdtransaction(idtransaction) != null) {
                apiperfectpay = sessiontransRepository.findeByIdtransaction(idtransaction);
                map.put("Status", apiperfectpay.getStatus());
                map.put("Idtransaction", apiperfectpay.getIdtransaction());
                map.put("montant", apiperfectpay.getMontant());
                map.put("PhoneClient", apiperfectpay.getTeldestinataire());
                return map;
            } else {
                map.put("msg", "idtransaction n'existe pas");
                map.put("response", -1);
                return map;
            }

        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("response", 0);
            return map;
        }

    }

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
            this.requestHeader = (RequestHeader) getTestObject(RequestHeader.class
            );

            BaseRequestHeader brh = new BaseRequestHeader();
            brh.setSecurityToken("UBCMKAKOTELq2ervuih1397fh1095437fh139pgv");
            brh.setRequestID("201");
            this.requestHeader.setRequestHeader(brh);
            BaseRequest baseRequest = (BaseRequest) getTestObject(BaseRequest.class
            );
            FundTransferCardToCardRequest fundTransferCardToCardRequest = new FundTransferCardToCardRequest();
            fundTransferCardToCardRequest.setFromCustomerID(Integer.valueOf(toCustomerId));
            fundTransferCardToCardRequest.setToCustomerID(toCustomerId);
            fundTransferCardToCardRequest.setLast4Digits(last4Digits);
            fundTransferCardToCardRequest.setPaymentType(paymentType);
            fundTransferCardToCardRequest.setTransferAmount(amount);
            String source = "XOF";
            fundTransferCardToCardRequest.setCurrencyCode("XOF");
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

//// 
    @ResponseBody
    @RequestMapping(value = "/testGet", method = RequestMethod.GET)
    public HashMap TestGet() {
        HashMap hashMap = new HashMap();
        Sessiontrans sessiontrans = new Sessiontrans();

        sessiontrans.setDate(new Date(System.currentTimeMillis()));
        sessiontransRepository.save(sessiontrans);
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
        HashMap hashMap = new HashMap();
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

    @ResponseBody
    @RequestMapping(value = "getOnlineDevises/{devise}", method = RequestMethod.GET)
    public String getDevise(@PathVariable("devise") String devise) {

        String source = null;
        String format = "1";
        Devisemonaies deviseMonaies = new Devisemonaies();
        deviseMonaies = deviseMonaieRepository.getOne(1);
        String currencies = null;
        try {
            if (devise.equals("DOLLAR")) {
                source = "USD";
                currencies = "EUR,XOF,CAD,GBP,NGN,AED,CNY,ZAR";
            }
            if (devise.equals("EURO")) {
                source = "EUR";
                currencies = "USD,XOF,CAD,GBP,NGN,AED,CNY,ZAR";
            }
            if (devise.equals("FCFA")) {
                source = "XOF";
                currencies = "USD,EUR,CAD,GBP,NGN,AED,CNY,ZAR";
            }
            if (devise.equals("CAD")) {
                source = "CAD";
                currencies = "USD,EUR,XOF,GBP,NGN,AED,CNY,ZAR";
            }
            if (devise.equals("YUAN")) {
                source = "CNY";
                currencies = "USD,EUR,XOF,CAD,GBP,NGN,AED,ZAR";
            }
            if (devise.equals("Livre sterling")) {
                source = "GBP";
                currencies = "USD,EUR,XOF,CAD,NGN,AED,CNY,ZAR";
            }
            if (devise.equals("DIRHAM")) {
                source = "AED";
                currencies = "USD,EUR,XOF,CAD,GBP,NGN,CNY,ZAR";
            }
            if (devise.equals("RAND")) {
                source = "ZAR";
                currencies = "USD,EUR,XOF,CAD,GBP,NGN,AED,CNY";
            }
            if (devise.equals("NAIRA")) {
                source = "NGN";
                currencies = "USD,EUR,XOF,CAD,GBP,AED,CNY,ZAR";
            }

            String url = "https://apilayer.net/api/live?access_key=" + deviseMonaies.getKeydevise() + "&currencies=" + currencies + "&source=" + source + "&format=" + format;
            CloseableHttpClient httpsclient = HttpClients.createDefault();

            HttpGet httpsget = new HttpGet(url);
            CloseableHttpResponse response;
            try {
                response = httpsclient.execute(httpsget);
                System.out.println("1------------------------------------1");
                System.out.println(response.getStatusLine());
                System.out.println("1------------------------------------1");

                // Get hold of the response entity
                org.apache.http.HttpEntity entity = response.getEntity();

                // If the response does not enclose an entity, there is no need
                // to bother about connection release
                byte[] buffer = new byte[1024];
                if (entity != null) {
                    System.out.println("response : " + entity);
                    InputStream inputStream = entity.getContent();
                    int bytesRead = 0;
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        String chunk = new String(buffer, 0, bytesRead);
                        JSONObject obj = new JSONObject(chunk);
                        if (obj.has("success")) {
                            if (obj.getBoolean("success")) {
                                JSONObject quotes = (JSONObject) obj.get("quotes");
                                deviseMonaies.setUsdLivreSterling(Float.valueOf(quotes.getString("USDGBP")));
                                deviseMonaies.setUsdaed(Float.valueOf(quotes.getString("USDAED")));
                                deviseMonaies.setUsdcad(Float.valueOf(quotes.getString("USDCAD")));
                                deviseMonaies.setUsdeur(Float.valueOf(quotes.getString("USDEUR")));
                                deviseMonaies.setUsdnaira(Float.valueOf(quotes.getString("USDNGN")));
                                deviseMonaies.setUsdxof(Float.valueOf(quotes.getString("USDXOF")));
                                deviseMonaies.setUsdyuan(Float.valueOf(quotes.getString("USDCNY")));
                                deviseMonaies.setUsdzar(Float.valueOf(quotes.getString("USDZAR")));
                                deviseMonaies.setRequestnumber(deviseMonaies.getRequestnumber() + 1);
                                deviseMonaieRepository.save(deviseMonaies);
                                return quotes.toString();

                                //req.updateDevise(Float.valueOf(quotes.getString("USDEUR")), Float.valueOf(quotes.getString("USDXOF")), Float.valueOf(quotes.getString("USDCAD")), Float.valueOf(quotes.getString("USDCNY")), Float.valueOf(quotes.getString("USDGBP")), Float.valueOf(quotes.getString("USDAED")), Float.valueOf(quotes.getString("USDZAR")), Float.valueOf(quotes.getString("USDNGN")));
                            } else {
                                return "-1";

                            }
                        } else {
                            return "-1";

                        }
                        //System.out.println("Sender : "+sms.getSender()+"; destinatiaire : "+sms.getDestinataire()+"; Code retour : "+chunk);
                        //System.out.println("flag : "+flag);
                    }
                } else {
                    return "-1";
                }
            } catch (ConnectException e) {
                e.printStackTrace();
                return "-1";
            } catch (IOException ioException) {
                ioException.printStackTrace();
                return "-1";
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
                return "-1";
            }
            return "-1";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    ///API MOMO******************************************************************
    @ResponseBody
    @RequestMapping(value = "initPaymentMoMo/{phone}/{amount}", method = RequestMethod.GET)
    public InfoPayMtn initPaymentMoMo(@PathVariable(value = "phone") String phone, @PathVariable(value = "amount") String amount) {

        HttpHeaders headers = new HttpHeaders();
        InfoPayMtn infoPayMtn = new InfoPayMtn();
        com.payment.pay.entities.Payer payer = new com.payment.pay.entities.Payer();
        RestTemplate restTemplate = new RestTemplate();

        try {

            String url = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay";

            headers.add("Authorization", "Bearer " + gettokenMoMo());
            headers.add("X-Reference-Id", getReferenceId());
            headers.add("X-Target-Environment", "sandbox");
            headers.add("Content-Type", "application/json");
            headers.add("Ocp-Apim-Subscription-Key", "7f38d67b3fab49c7be51995ab2671259");
            payer.setPartyId(phone);
            infoPayMtn.setAmount("5.0");
            infoPayMtn.setCurrency("EUR");
            infoPayMtn.setExternalId("1254778");
            infoPayMtn.setPayeeNote("test");
            infoPayMtn.setPayerMessage("testjava");
            infoPayMtn.setPayer(payer);
            HttpEntity<InfoPayMtn> entity = new HttpEntity<>(infoPayMtn, headers);
            String sg = restTemplate.postForObject(url, entity, String.class
            );
            System.out.println(sg);
            System.out.println("+++++++++++++++++++++");
            return infoPayMtn;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------");
        return infoPayMtn;
    }

    @ResponseBody
    public String getReferenceId() {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        String ref = "";
        try {
            String url = "https://www.uuidgenerator.net/api/version4";

            HttpEntity<String> entity = new HttpEntity<>(ref, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class
            );
            return response.getBody();

        } catch (Exception e) {

            return e.getMessage();
        }

    }

    @ResponseBody
    @RequestMapping(value = "gettokenMoMo", method = RequestMethod.GET)

    public String gettokenMoMo() {
        HashMap map = new HashMap();
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        try {

            headers.add("Authorization", "Basic YzhhYzE5Y2EtYzdkMi00ODA0LWIzM2QtNGM1NjhlYTAzNjJkOmJjMThmM2UwMzU3NzRlMzBiMDZiNTAwZTllNTliM2Nh");
            headers.add("Content-Type", "application/json");
            headers.add("Ocp-Apim-Subscription-Key", "7f38d67b3fab49c7be51995ab2671259");
            String url = "https://sandbox.momodeveloper.mtn.com/collection/token/";
            HttpEntity<HashMap> entity = new HttpEntity<>(map, headers);
            ResponseEntity<HashMap> response = restTemplate.exchange(url, HttpMethod.POST, entity, HashMap.class
            );
            map = response.getBody();
            System.out.println(map.get("access_token"));

            return map.get("access_token").toString();
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return map.toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "checkTransaction", method = RequestMethod.GET)
    public Object checkTransaction() {
        HashMap map = new HashMap();
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        try {
            System.out.println(gettokenMoMo());
            headers.add("Authorization", "Bearer " + gettokenMoMo());
            headers.add("Content-Type", "application/json");
            headers.add("X-Target-Environment", "sandbox");
            headers.add("Ocp-Apim-Subscription-Key", "7f38d67b3fab49c7be51995ab2671259");
            String url = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay/da2b3269-e298-4937-b206-a78e81f35c64";
            HttpEntity<HashMap> entity = new HttpEntity<>(map, headers);
            ResponseEntity<HashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity, HashMap.class
            );
            map = response.getBody();
            System.out.println(map.get("status"));
            return map;
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return map;
        }
    }

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, JSONException {
        OmService omService = new OmService(partenaireRepository, transtatusRepository, infopayRepository);
        
        Partenaire partenaire=new Partenaire();
        System.out.println("----------------------------------------1");
        if (partenaireRepository.findByOmReference("ORCA") == null) {
            
            partenaire.setOrderId(1);
            System.out.println("----------------------------------------2");
            partenaireRepository.save(partenaire);
        }
        omService.setAmount("1");
        omService.setCodeApi("327027256");
        omService.setCodeClient("5022756503");
        omService.setIndex("GEDO73782262735212279SYSTEMOq38e");
        omService.setNomProjet("ORCA");
        omService.setOperateur("ORANGE");
        omService.setTelephone("691788864");

        return omService.PaymentOm();

    }

}
