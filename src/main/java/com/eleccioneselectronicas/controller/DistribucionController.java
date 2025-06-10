package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.service.DistribucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distribucion")
public class DistribucionController {

    @Autowired
    private DistribucionService distribucionService;

    // Endpoint para generar el PDF de la distribución de votantes por elección
    @GetMapping("/eleccion/{idEleccion}/pdf")
    public ResponseEntity<byte[]> generarDistribucionPdf(@PathVariable Long idEleccion) {
        try {
            // Llamar al servicio para generar el PDF solo con el idEleccion
            byte[] pdf = distribucionService.generarDistribucionPdf(idEleccion);

            if (pdf == null) {
                return ResponseEntity.status(500).body("No se pudo generar el PDF".getBytes());
            }

            // Configurar los headers de la respuesta para devolver el PDF correctamente
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "inline; filename=distribucion_eleccion.pdf")
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al generar el PDF".getBytes());
        }
    }
}