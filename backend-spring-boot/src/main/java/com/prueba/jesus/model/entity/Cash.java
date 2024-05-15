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
@Table(name="cash")
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cashid;

    @Column(nullable = false)
    private String cashdescription;

    @Column(nullable = false)
    private String active;



}
