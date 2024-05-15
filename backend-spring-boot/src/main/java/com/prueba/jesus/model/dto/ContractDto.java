package com.prueba.jesus.model.dto;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {


    // Contract
    Integer[] devices;
    Integer serviceid;
    Integer methodpaymentid;
    Integer statuscontractid;
    Integer clientid;
    Date startdate;
    Date enddate;


}
