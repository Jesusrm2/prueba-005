package com.prueba.jesus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Cash;
import com.prueba.jesus.model.entity.Turn;

public interface TurnRepository extends JpaRepository<Turn, Integer>{
    List<Turn> findByCashid(Cash cash);
    
}
