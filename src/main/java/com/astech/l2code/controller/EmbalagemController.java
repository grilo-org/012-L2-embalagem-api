package com.astech.l2code.controller;

import com.astech.l2code.dto.RequestDTO;
import com.astech.l2code.dto.ResponseDTO;
import com.astech.l2code.service.EmbalagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/embalagens")
public class EmbalagemController {

    @Autowired
    private EmbalagemService embalagemService;

    @PostMapping("/buscarEmbalagens")
    public ResponseEntity<ResponseDTO> buscarEmbalagens(@RequestBody RequestDTO request) {
        ResponseDTO responseDTO = embalagemService.processarPedido(request.getPedidos());

        return ResponseEntity.ok(responseDTO);

    }
}
