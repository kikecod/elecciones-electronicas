package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.VotanteDTO;
import com.eleccioneselectronicas.service.VotanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votantes")
@Validated
public class VotanteController {

    @Autowired
    private VotanteService service;

    @GetMapping
    public List<VotanteDTO> listarTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotanteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<VotanteDTO> crear(@Valid @RequestBody VotanteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VotanteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody VotanteDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // NUEVO: Registrar con imagen y UUID generado
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarConQRyImagen(@RequestBody VotanteDTO dto) {
        VotanteDTO registrado = service.registrarConImagen(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id_votante", registrado.getIdVotante(),
                "qr_uuid", registrado.getQrUuid(),
                "mensaje", "Votante registrado correctamente"
        ));
    }

    // NUEVO: Buscar por CI
    @GetMapping("/buscar")
    public ResponseEntity<VotanteDTO> buscarPorCI(@RequestParam String ci) {
        return ResponseEntity.ok(service.buscarPorCI(ci));
    }
}
