package com.prueba.jesus.model.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
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
@Table(name = "turn")
public class Turn {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer turnid;

  @Column(nullable = false)
  String description;

  @Column(nullable = false)
  Date date;

  Integer usergestorid;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cashid")
  Cash cashid;

}
