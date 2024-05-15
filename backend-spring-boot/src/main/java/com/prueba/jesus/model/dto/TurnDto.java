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
public class TurnDto {

    private Integer turnid;
    private String description;
    private Date date;
    private Integer usergestorid;
    private Integer cash_cashid;

}
