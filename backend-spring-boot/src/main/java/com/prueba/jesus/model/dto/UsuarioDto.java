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
public class UsuarioDto {
    private Integer userid;
    private String username;
    private String email;
    private String password;
    private Date creationdate;
    private Integer usercreate;
    private Integer userapproval;
    private Date dateapproval;
    private Integer rol_rolid;
    private Integer status_statusid;
}
