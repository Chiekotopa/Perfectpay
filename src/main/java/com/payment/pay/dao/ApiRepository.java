/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos TCHIOZEM
 */
public interface ApiRepository extends JpaRepository<Api, Integer> {
    
    @Query(value = "SELECT a FROM Api a WHERE a.indexClient=?1 AND a.codeApi=?2 AND a.nomApi=?3")
    public Api findApiByCodeapiIndexapi(String indexClient, String codeApi,String nomApi);
}
