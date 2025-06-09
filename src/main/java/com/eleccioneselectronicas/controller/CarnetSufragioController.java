package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.service.CarnetSufragioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/{idVotante}/enviar-email")
    public ResponseEntity<String> enviarCarnetPorEmail(@PathVariable Long idVotante) {
        try {
            carnetSufragioService.enviarCarnetPorEmail(idVotante);
            return ResponseEntity.ok("Correo enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
