package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.AsignacionVotanteDTO;
import com.eleccioneselectronicas.service.AsignacionVotanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignar")
public class AsignacionVotanteController {

    @Autowired
    private AsignacionVotanteService asignacionVotanteService;

    // POST /asignar/recintos-y-dispositivos
    @PostMapping("/recintos-y-dispositivos")
    public ResponseEntity<List<AsignacionVotanteDTO>> asignar() {
        List<AsignacionVotanteDTO> resultado = asignacionVotanteService.asignarRecintosYDispositivos();
        return ResponseEntity.ok(resultado);
    }
}
