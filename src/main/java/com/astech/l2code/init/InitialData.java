package com.astech.l2code.init;

import com.astech.l2code.model.Embalagem;
import com.astech.l2code.repository.EmbalagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitialData implements CommandLineRunner {

    @Autowired
    private EmbalagemRepository embalagemRepository;

    @Override
    public void run(String... args) throws Exception {
        if(embalagemRepository.count()==0){
            List<Embalagem> embalagens = List.of(
                    Embalagem.builder().descricao("Caixa Modelo 1")
                            .altura("30")
                            .largura("40")
                            .comprimento("80")
                            .build(),
                    Embalagem.builder().descricao("Caixa Modelo 2")
                            .altura("80")
                            .largura("50")
                            .comprimento("40")
                            .build(),
                    Embalagem.builder().descricao("Caixa Modelo 3")
                            .altura("50")
                            .largura("80")
                            .comprimento("60")
                            .build()
            );
            embalagemRepository.saveAll(embalagens);
            System.out.println("Embalagens iniciais criadas com sucesso!");
        }

    }
}
