package com.astech.l2code.service;

import com.astech.l2code.dto.*;
import com.astech.l2code.model.Embalagem;
import com.astech.l2code.repository.EmbalagemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmbalagemServiceTest {

    @Mock
    private EmbalagemRepository embalagemRepository;

    @InjectMocks
    private EmbalagemService embalagemService;

    @Test
    void deveSelecionarEmbalagemMaisAjustadaParaProduto() {

        List<Embalagem> embalagens = List.of(
                new Embalagem(1L, "Caixa Pequena", "10", "10", "10"),
                new Embalagem(2L, "Caixa Média", "20", "20", "20"),
                new Embalagem(3L, "Caixa Grande", "50", "50", "50")
        );

        Mockito.when(embalagemRepository.findAll()).thenReturn(embalagens);

        ProdutoDTO produto1 = new ProdutoDTO("Produto A", new DimensoesDTO(8, 8, 8));
        PedidoDTO pedido = new PedidoDTO(1L, List.of(produto1));
        List<PedidoDTO> pedidos = List.of(pedido);

        ResponseDTO resposta = embalagemService.processarPedido(pedidos);

        assertEquals(1, resposta.getPedidos().size());
        PedidoResponseDTO pedidoResp = resposta.getPedidos().getFirst();

        assertEquals(1, pedidoResp.getCaixas().size());
        assertEquals("Caixa Pequena", pedidoResp.getCaixas().getFirst().getEmbalagemId());
        assertTrue(pedidoResp.getCaixas().getFirst().getProdutos().contains("Produto A"));
    }

    @Test
    void deveMarcarProdutoSemEmbalagemQuandoNaoHaEspacoSuficiente() {
        List<Embalagem> embalagens = List.of(
                new Embalagem(1L, "Caixa Pequena", "10", "10", "10"),
                new Embalagem(2L, "Caixa Média", "20", "20", "20")
        );

        Mockito.when(embalagemRepository.findAll()).thenReturn(embalagens);

        ProdutoDTO produtoGrande = new ProdutoDTO("Produto Grande", new DimensoesDTO(100, 50, 40));
        PedidoDTO pedido = new PedidoDTO(100L, List.of(produtoGrande));
        List<PedidoDTO> pedidos = List.of(pedido);

        ResponseDTO resposta = embalagemService.processarPedido(pedidos);

        assertEquals(1, resposta.getPedidos().size());
        PedidoResponseDTO pedidoResp = resposta.getPedidos().getFirst();

        assertEquals(1, pedidoResp.getCaixas().size());
        EmbalagemDTO caixa = pedidoResp.getCaixas().getFirst();

        assertEquals("Sem embalagem", caixa.getEmbalagemId());
        assertTrue(caixa.getProdutos().contains("Produto Grande"));
        assertNotNull(caixa.getObservacao());
        assertTrue(caixa.getObservacao().contains("Nenhuma embalagem disponível"));
    }


}
