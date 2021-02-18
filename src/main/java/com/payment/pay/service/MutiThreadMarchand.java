/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.service;

import com.payment.pay.dao.ApiperfectRepository;
import com.payment.pay.dao.SessiontransRepository;
import com.payment.pay.entitybd.Apiperfectpay;
import com.payment.pay.entitybd.Sessiontrans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Service
public class MutiThreadMarchand extends Thread {

    @Autowired
    SessiontransRepository sessiontransRepository;

    @Autowired
    ApiperfectRepository apiperfectRepository;

    private String phoneDest;
    private String phoneExp;
    private String key;

    public MutiThreadMarchand(SessiontransRepository sessiontransRepository, ApiperfectRepository apiperfectRepository) {
        this.sessiontransRepository = sessiontransRepository;
        this.apiperfectRepository = apiperfectRepository;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println(phoneDest);
        while ((System.currentTimeMillis() - startTime) < 65000) {
            try {
                System.out.println(System.currentTimeMillis() - startTime);
                Thread.sleep(3000);
                Sessiontrans sessions = new Sessiontrans();
                Apiperfectpay apiperfectpay = new Apiperfectpay();
                if (sessiontransRepository.findListSessiontransBySecretcode(this.phoneDest, this.phoneExp) == null) {

                    break;
                } else {
                    sessions = sessiontransRepository.findListSessiontransBySecretcode(this.phoneDest, this.phoneExp);
                    apiperfectpay = sessiontransRepository.findeByIdtransaction(this.key);
                    System.out.println(this.phoneDest + "******************** dest");
                    System.out.println(this.phoneExp + "******************** Exp");
                    System.out.println(sessions.getStatus() + "********************");

                    if (sessions.getStatus().equals("2")) {
                        sessions.setTread("0");
                        sessions.setStatus("2");
                        apiperfectpay.setStatus("SUCCES");
                        sessiontransRepository.save(sessions);
                        apiperfectRepository.save(apiperfectpay);
                        break;
                    }
                    if (sessions.getStatus().equals("-1")) {
                        sessions.setTread("0");
                        sessions.setStatus("-1");
                        apiperfectpay.setStatus("ECHEC");
                        sessiontransRepository.save(sessions);
                        apiperfectRepository.save(apiperfectpay);
                        break;

                    }
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if ((System.currentTimeMillis() - startTime) >= 65000) {
            Sessiontrans sessions = new Sessiontrans();
            sessions = sessiontransRepository.findListSessiontransBySecretcode(this.phoneDest, this.phoneExp);
            Apiperfectpay apiperfectpay = sessiontransRepository.findeByIdtransaction(this.key);
            sessions.setCodesecret("time out");
            sessions.setStatus("0");
            sessions.setTread("0");
            apiperfectpay.setStatus("TIME OUT");
            sessiontransRepository.save(sessions);
            apiperfectRepository.save(apiperfectpay);
            System.out.println("time out");
        }

    }

    public void setphone(String phone) {
        this.phoneDest = phone;
    }

    public void setphoneExp(String phone) {
        this.phoneExp = phone;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
