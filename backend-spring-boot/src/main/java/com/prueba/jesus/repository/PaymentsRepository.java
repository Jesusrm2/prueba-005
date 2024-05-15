package com.prueba.jesus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Integer>{

}
