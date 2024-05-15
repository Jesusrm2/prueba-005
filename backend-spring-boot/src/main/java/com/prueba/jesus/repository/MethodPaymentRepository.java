package com.prueba.jesus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.MethodPayment;

public interface MethodPaymentRepository extends JpaRepository<MethodPayment, Integer>{
    boolean existsByDescription(String description);

}
