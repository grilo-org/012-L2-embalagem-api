package com.astech.l2code.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    @JsonAlias({"pedido_id", "pedidoId"})
    private Long pedidoId;
    private List<ProdutoDTO> produtos;
}
