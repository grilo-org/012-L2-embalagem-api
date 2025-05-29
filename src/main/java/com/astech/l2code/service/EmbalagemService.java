package com.astech.l2code.service;

import com.astech.l2code.dto.*;
import com.astech.l2code.model.Embalagem;
import com.astech.l2code.repository.EmbalagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmbalagemService {

    @Autowired
    private EmbalagemRepository embalagemRepository;

    public ResponseDTO processarPedido (List<PedidoDTO> pedidos){
        List<PedidoResponseDTO> pedidoResponseList = new ArrayList<>();

        List<Embalagem> embalagensDisponiveis = embalagemRepository.findAll();

        pedidos.forEach(pedido -> {
            List<EmbalagemDTO> embalagensSelecionadas = new ArrayList<>();

            pedido.getProdutos().forEach(produto -> {
                Embalagem melhorEmbalagem = definirMelhorEmbalagem(produto, embalagensDisponiveis);

                if (melhorEmbalagem != null) {

                    Optional<EmbalagemDTO> embalagemExistente = embalagensSelecionadas.stream()
                            .filter(embalagemDTO -> embalagemDTO.getEmbalagemId().equals(melhorEmbalagem.getDescricao()))
                            .findFirst();

                    if (embalagemExistente.isPresent()){
                        EmbalagemDTO embalagemUsada = embalagemExistente.get();
                        int volumeProduto = calcularVolume(produto);

                        int alturaProduto = produto.getDimensoes().getAltura();
                        int larguraProduto = produto.getDimensoes().getLargura();
                        int comprimentoProduto = produto.getDimensoes().getComprimento();

                        int alturaMelhorEmbalagem = Integer.parseInt(melhorEmbalagem.getAltura());
                        int larguraMelhorEmbalagem = Integer.parseInt(melhorEmbalagem.getLargura());
                        int comprimentoMelhorEmbalagem = Integer.parseInt(melhorEmbalagem.getComprimento());

                        boolean cabeFisicamente =
                                embalagemUsada.getAlturaOcupada() + alturaProduto <= alturaMelhorEmbalagem &&
                                        embalagemUsada.getLarguraOcupada() + larguraProduto <= larguraMelhorEmbalagem &&
                                        embalagemUsada.getComprimentoOcupado() + comprimentoProduto <= comprimentoMelhorEmbalagem;

                        if (cabeFisicamente) {
                            embalagemUsada.getProdutos().add(produto.getProdutoId());
                            embalagemUsada.setAlturaOcupada(embalagemUsada.getAlturaOcupada() + alturaProduto);
                            embalagemUsada.setLarguraOcupada(embalagemUsada.getLarguraOcupada() + larguraProduto);
                            embalagemUsada.setComprimentoOcupado(embalagemUsada.getComprimentoOcupado() + comprimentoProduto);
                            embalagemUsada.setVolumeOcupado(embalagemUsada.getVolumeOcupado() + volumeProduto);
                        }else{
                            EmbalagemDTO novaEmbalagem = new EmbalagemDTO();
                            novaEmbalagem.setEmbalagemId(melhorEmbalagem.getDescricao());
                            novaEmbalagem.setProdutos(new ArrayList<>(List.of(produto.getProdutoId())));
                            novaEmbalagem.setVolumeOcupado(volumeProduto);
                            embalagensSelecionadas.add(novaEmbalagem);
                        }


                    }else{
                        EmbalagemDTO novaEmbalagem = new EmbalagemDTO();
                        novaEmbalagem.setEmbalagemId(melhorEmbalagem.getDescricao());
                        novaEmbalagem.setProdutos(new ArrayList<>(List.of(produto.getProdutoId())));
                        embalagensSelecionadas.add(novaEmbalagem);
                    }

                }else{
                    EmbalagemDTO novaEmbalagem = new EmbalagemDTO();
                    novaEmbalagem.setEmbalagemId("Sem embalagem");
                    novaEmbalagem.setProdutos(new ArrayList<>(List.of(produto.getProdutoId())));
                    novaEmbalagem.setObservacao("Nenhuma embalagem disponível para o produto " + produto.getProdutoId());
                    embalagensSelecionadas.add(novaEmbalagem);
                }
            });

            pedidoResponseList.add(new PedidoResponseDTO(pedido.getPedidoId(), embalagensSelecionadas));

        });

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setPedidos(pedidoResponseList);

        return responseDTO;
    }

    private Embalagem definirMelhorEmbalagem(ProdutoDTO produto, List<Embalagem> embalagensDisponiveis) {

        DimensoesDTO dimensoesProduto = produto.getDimensoes();

        return embalagensDisponiveis.stream().filter(embalagem -> Integer.parseInt(embalagem.getAltura()) >= dimensoesProduto.getAltura() &&
                Integer.parseInt(embalagem.getLargura()) >= dimensoesProduto.getLargura() &&
                Integer.parseInt(embalagem.getComprimento()) >= dimensoesProduto.getComprimento())
                .min(Comparator.comparingInt(value -> {
                    int volumeEmbalagem = Integer.parseInt(value.getAltura()) * Integer.parseInt(value.getLargura()) * Integer.parseInt(value.getComprimento());
                    int volumeProduto = dimensoesProduto.getAltura() * dimensoesProduto.getLargura() * dimensoesProduto.getComprimento();
                    return volumeEmbalagem - volumeProduto;
                }))
                .orElse(null);
    }

    private int calcularVolume(ProdutoDTO produto) {
        DimensoesDTO dimensoes = produto.getDimensoes();
        return dimensoes.getAltura() * dimensoes.getLargura() * dimensoes.getComprimento();
    }

}
