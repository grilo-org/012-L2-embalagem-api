package com.astech.l2code.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class EmbalagemDTO {

    private String embalagemId;
    private List<String> produtos;
    private String observacao;
    @JsonIgnore
    private int volumeOcupado;
    @JsonIgnore
    private int alturaOcupada;
    @JsonIgnore
    private int larguraOcupada;
    @JsonIgnore
    private int comprimentoOcupado;
}
