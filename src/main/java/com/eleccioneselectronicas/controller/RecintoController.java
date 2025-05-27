package com.eleccioneselectronicas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eleccioneselectronicas.dto.RecintoDTO;
import com.eleccioneselectronicas.service.RecintoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recintos")
@RequiredArgsConstructor
public class RecintoController {

    private final RecintoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecintoDTO crear(@Valid @RequestBody RecintoDTO dto) {
        return service.crear(dto);
    }

    @GetMapping("/{id}")
    public RecintoDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @GetMapping
    public List<RecintoDTO> listar() {
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    public RecintoDTO actualizar(@PathVariable Long id, @Valid @RequestBody RecintoDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
