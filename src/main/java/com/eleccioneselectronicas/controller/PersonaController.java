package com.eleccioneselectronicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eleccioneselectronicas.dto.PersonaDTO;
import com.eleccioneselectronicas.service.PersonaService;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/buscar-por-ci/{ci}")
    public PersonaDTO buscarPorCi(@PathVariable String ci) {
        return personaService.buscarPorCi(ci);
    }

    @GetMapping("/filtrar")
    public List<PersonaDTO> filtrarPorRol(@RequestParam String rol) {
        return personaService.filtrarPorRol(rol);
    }
}
