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
@Table(name="contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractid;

    @Column(nullable = false)
    private Date startdate;

    @Column(nullable = false)
    private Date enddate;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "clientid")
    private Client client_clientid;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceid")
    private Servicio service_serviceid;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "statuscontractid")
    private StatusContract statuscontract_statuscontractid;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "methodpaymentid")
    private MethodPayment methodpayment_methodpaymentid;


}
