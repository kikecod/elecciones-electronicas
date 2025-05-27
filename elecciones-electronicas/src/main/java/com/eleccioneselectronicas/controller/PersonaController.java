package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.PersonaDTO;
import com.eleccioneselectronicas.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<PersonaDTO> listar() {
        return personaService.listar();
    }

    @GetMapping("/{id}")
    public PersonaDTO obtenerPorId(@PathVariable Long id) {
        return personaService.obtenerPorId(id);
    }

    @PostMapping
    public PersonaDTO registrar(@RequestBody PersonaDTO dto) {
        return personaService.registrar(dto);
    }

    @PutMapping("/{id}")
    public PersonaDTO actualizar(@PathVariable Long id, @RequestBody PersonaDTO dto) {
        return personaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        personaService.eliminar(id);
    }

    @GetMapping("/buscar/{nombre}")
    public List<PersonaDTO> buscarPorNombre(@PathVariable String nombre) {
        return personaService.buscarPorNombre(nombre);
    }
}
