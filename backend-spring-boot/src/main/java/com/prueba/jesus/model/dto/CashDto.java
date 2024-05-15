package com.prueba.jesus.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class CashDto {

    private Integer cashid;
    private String cashdescription;
    private String active;


}
