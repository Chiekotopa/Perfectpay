/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 *
 * @author Carlos TCHIOZEM
 */
public interface PartenaireRepository extends JpaRepository<Partenaire, Integer> {
    
    
    @Query(value = "SELECT p FROM Partenaire p WHERE p.omReference= ?1")
    public Partenaire findByOmReference( String ref);
}
