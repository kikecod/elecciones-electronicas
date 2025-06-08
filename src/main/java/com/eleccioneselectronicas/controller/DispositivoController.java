package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.DispositivoDTO;
import com.eleccioneselectronicas.service.DispositivoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/dispositivos")
@RequiredArgsConstructor
public class DispositivoController {

    private final DispositivoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DispositivoDTO crear(@Valid @RequestBody DispositivoDTO dto) {
        return service.crear(dto);
    }

    @GetMapping("/{id}")
    public DispositivoDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @GetMapping
    public List<DispositivoDTO> listar() {
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    public DispositivoDTO actualizar(@PathVariable Long id, @Valid @RequestBody DispositivoDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
