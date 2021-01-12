/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.service;


import com.payment.pay.dao.SessiontransRepository;
import com.payment.pay.entitybd.Sessiontrans;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Service
public class MultiThread extends Thread {

    @Autowired
    SessiontransRepository sessiontransRepository;

    private String phoneDest;
    private String phoneExp;

    public MultiThread(SessiontransRepository sessiontransRepository) {
        this.sessiontransRepository = sessiontransRepository;
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
                if(sessiontransRepository.findListSessiontransBySecretcode(this.phoneDest, this.phoneExp)==null){
           
                    break;
                }else{
                    sessions = sessiontransRepository.findListSessiontransBySecretcode(this.phoneDest, this.phoneExp);
                System.out.println(this.phoneDest + "******************** dest");
                System.out.println(this.phoneExp + "******************** Exp");
                System.out.println(sessions.getStatus() + "********************");

                if (sessions.getStatus().equals("2")) {
                    sessions.setTread("0");
                    sessions.setStatus("2");
                    sessiontransRepository.save(sessions);
                    break;
                }
                if (sessions.getStatus().equals("-1")) {
                    sessions.setTread("0");
                     sessions.setStatus("-1");
                    sessiontransRepository.save(sessions);
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
            sessions.setCodesecret("time out");
            sessions.setStatus("0");
            sessions.setTread("0");
            sessiontransRepository.save(sessions);
            System.out.println("Ok");
        }

    }

    public void setphone(String phone) {
        this.phoneDest = phone;
    }

    public void setphoneExp(String phone) {
        this.phoneExp = phone;
    }

    public void getphone() {
        System.out.println(phoneDest);
    }
}
