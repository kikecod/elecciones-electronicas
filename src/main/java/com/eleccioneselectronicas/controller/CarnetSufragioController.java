package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.service.CarnetSufragioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carnet-sufragio")
public class CarnetSufragioController {

    @Autowired
    private CarnetSufragioService carnetSufragioService;

    @GetMapping("/{idVotante}/pdf")
    public ResponseEntity<byte[]> generarCarnetSufragio(@PathVariable Long idVotante) {
        byte[] pdf = carnetSufragioService.generarPdfCarnet(idVotante);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
