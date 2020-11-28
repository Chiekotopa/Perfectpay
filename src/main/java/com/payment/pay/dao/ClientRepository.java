/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos TCHIOZEM
 */
public interface ClientRepository extends JpaRepository<Clients, Integer> {

    @Query(value = "SELECT c FROM Clients c WHERE c.telephone= ?1")
    public Clients findByTelephone(String telephone);
}
