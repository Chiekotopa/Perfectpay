/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Transtatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos TCHIOZEM
 */
public interface TranstatusRepository extends JpaRepository<Transtatus, String> {
    
   @Query(value="SELECT t FROM Transtatus t WHERE t.payToken=?1")
   public Transtatus findByPaytoken(String paytoken);
}
