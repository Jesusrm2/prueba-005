package com.prueba.jesus.model.entity;
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
@Table(name="attention")
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attentionid;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "turnid")
    private Turn turn;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "clientid")
    private Client client;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "attentiontypeid")
    private AtentionType attentionType;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "statusid")
    private AttentionStatus attentionStatus;



}
