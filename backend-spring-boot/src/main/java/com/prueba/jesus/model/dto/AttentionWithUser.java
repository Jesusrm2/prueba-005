package com.prueba.jesus.model.dto;
import com.prueba.jesus.model.entity.Attention;
import com.prueba.jesus.model.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AttentionWithUser {
    private Attention attention;
    private Usuario user;
}
