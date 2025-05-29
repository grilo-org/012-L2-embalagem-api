package com.astech.l2code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long pedidoId;
    private List<EmbalagemDTO> caixas;
}
