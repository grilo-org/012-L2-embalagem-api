package com.astech.l2code.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    @JsonAlias({"produto_id", "produtoId"})
    private String produtoId;
    private DimensoesDTO dimensoes;
}
