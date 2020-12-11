/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.dao;

import com.payment.pay.entitybd.Partenaireuba;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Administrateur
 */
public interface PartenaireUbaRepository extends JpaRepository<Partenaireuba, Integer> {
    
}
