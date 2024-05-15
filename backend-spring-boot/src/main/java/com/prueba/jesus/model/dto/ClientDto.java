package com.prueba.jesus.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    Integer clientid;
    String name;
    String lastname;
    String identification;
    String email;
    String phonenumber;
    String address;
    String referenceaddress;

}
