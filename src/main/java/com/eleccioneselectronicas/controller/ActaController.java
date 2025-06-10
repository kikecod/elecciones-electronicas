package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.service.ActaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/actas")
public class ActaController {

    @Autowired
    private ActaService actaService;

    // Endpoint para generar el PDF del acta de escrutinio del recinto
    @GetMapping("/recinto/{idRecinto}/eleccion/{idEleccion}/pdf")
    public ResponseEntity<byte[]> generarActaRecintoPdf(@PathVariable Long idRecinto, @PathVariable Long idEleccion) {
        try {
            // Llamar al servicio para generar el PDF
            byte[] pdf = actaService.generarActaRecintoPdf(idRecinto, idEleccion);

            if (pdf == null) {
                return ResponseEntity.status(500).body("No se pudo generar el PDF".getBytes());
            }

            // Configurar los headers de la respuesta para devolver el PDF correctamente
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "inline; filename=acta_recinto.pdf")
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al generar el PDF".getBytes());
        }
    }
}