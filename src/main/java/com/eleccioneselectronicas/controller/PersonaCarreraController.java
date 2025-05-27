package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.PersonaCarreraDTO;
import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.service.PersonaCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persona-carrera")
public class PersonaCarreraController {

    @Autowired
    private PersonaCarreraService service;

    @PostMapping
    public PersonaCarrera crear(@RequestBody PersonaCarreraDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<PersonaCarrera> listar() {
        return service.listar();
    }

    @PutMapping("/{idPersona}/{idCarrera}")
    public PersonaCarrera actualizar(
            @PathVariable Long idPersona,
            @PathVariable Long idCarrera,
            @RequestBody PersonaCarreraDTO dto) {
        return service.actualizar(idPersona, idCarrera, dto);
    }

    @DeleteMapping("/{idPersona}/{idCarrera}")
    public void eliminar(@PathVariable Long idPersona, @PathVariable Long idCarrera) {
        service.eliminar(idPersona, idCarrera);
    }
}
