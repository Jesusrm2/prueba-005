package com.prueba.jesus.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="userstatus")
public class UserStatus {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer statusid;

    
     @Column(nullable = false)
     private String description;


}
