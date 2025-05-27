package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.DocenteDTO;
import com.eleccioneselectronicas.service.DocenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@Validated
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @GetMapping
    public List<DocenteDTO> obtenerDocentes() {
        return docenteService.obtenerDocentes();
    }

    @GetMapping("/{id}")
    public DocenteDTO obtenerDocentePorId(@PathVariable Long id) {
        return docenteService.obtenerDocentePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocenteDTO crearDocente(@Valid @RequestBody DocenteDTO dto) {
        return docenteService.crearDocente(dto);
    }

    @PutMapping("/{id}")
    public DocenteDTO actualizarDocente(@PathVariable Long id, @Valid @RequestBody DocenteDTO dto) {
        return docenteService.actualizarDocente(id, dto);
    }

    @PutMapping("/{id}/baja")
    public void eliminarDocente(@PathVariable Long id) {
        docenteService.eliminarDocente(id);
    }
}