/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Souscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos TCHIOZEM
 */
public interface SouscriptionRepository extends JpaRepository<Souscriptions, Integer> {
    
    @Query(value = "SELECT ss FROM Souscriptions ss WHERE ss.indexeService=?1 AND ss.indexeClient=?2")
    public Souscriptions findSouscriptByIndexClientEtServices(String indexService,String indexeClient);
}
