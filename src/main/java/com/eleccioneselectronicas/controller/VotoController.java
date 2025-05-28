package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.VotoDTO;
import com.eleccioneselectronicas.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votos")
@Validated
public class VotoController {

    @Autowired
    private VotoService service;

    @PostMapping
    public ResponseEntity<VotoDTO> emitir(@Valid @RequestBody VotoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.emitirVoto(dto));
    }

    @GetMapping
    public List<VotoDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
