/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Infopayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos TCHIOZEM
 */
public interface InfopayRepository extends JpaRepository<Infopayment, Integer> {
    
    
     @Query(value="SELECT i FROM Infopayment i WHERE i.notifToken=?1")
   public Infopayment findByNotifToken(String notifToken);
}
