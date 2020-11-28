/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos TCHIOZEM
 */
public interface ServiceRepository extends JpaRepository<Services, Integer> {
 
    @Query(value = "SELECT s FROM Services s WHERE s.codeService='C002'")
    public Services findServiceByCodeService();
}
