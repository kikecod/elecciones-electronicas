package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.AsignacionEncargadosDTO;
import com.eleccioneselectronicas.dto.EncargadoInfoDTO;
import com.eleccioneselectronicas.service.EncargadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asignar")
public class EncargadoController {

    @Autowired
    private EncargadoService encargadoService;

    @PostMapping("/encargados/recinto")
    public ResponseEntity<String> asignarEncargados(@RequestBody AsignacionEncargadosDTO dto) {
        encargadoService.asignarEncargadosARecinto(dto);
        return ResponseEntity.ok("Encargados asignados correctamente");
    }

    @GetMapping("/encargados/buscar/{ci}")
    public ResponseEntity<EncargadoInfoDTO> buscarEncargadoPorCi(@PathVariable String ci) {
        EncargadoInfoDTO dto = encargadoService.buscarEncargadoPorCi(ci);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/encargados/eleccion/{id}/pdf")
    public ResponseEntity<byte[]> generarPdf(@PathVariable Long id) {
        byte[] pdf = encargadoService.generarPdfEncargadosPorEleccion(id);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=encargados_eleccion_" + id + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }

}
