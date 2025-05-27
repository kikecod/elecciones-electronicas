package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.PartidoDTO;
import com.eleccioneselectronicas.model.Partido;
import com.eleccioneselectronicas.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @PostMapping
    public Partido crearPartido(@RequestBody PartidoDTO dto) {
        return partidoService.crearPartido(dto);
    }

    @GetMapping
    public List<Partido> listarPartidos() {
        return partidoService.listarPartidos();
    }

    @PutMapping("/{id}")
    public Partido actualizarPartido(@PathVariable Long id, @RequestBody PartidoDTO dto) {
        return partidoService.actualizarPartido(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarPartido(@PathVariable Long id) {
        partidoService.eliminarPartido(id);
    }
}
