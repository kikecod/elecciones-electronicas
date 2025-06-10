package com.eleccioneselectronicas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleccioneselectronicas.dto.PersonaDTO;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.repository.DocenteRepository;
import com.eleccioneselectronicas.repository.EstudianteRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public PersonaDTO buscarPorCi(String ci) {
        var persona = personaRepository.findByCi(ci);
        if (persona == null) {
            throw new RuntimeException("No existe una persona con ese CI");
        }
        PersonaDTO dto = new PersonaDTO();
        dto.setIdPersona(persona.get().getIdPersona());
        dto.setCi(persona.get().getCi());
        dto.setNombre(persona.get().getNombre());
        dto.setApellidoPaterno(persona.get().getApellido_paterno());
        dto.setApellidoMaterno(persona.get().getApellido_materno());
        dto.setFechaNacimiento(persona.get().getFechaNacimiento());
        dto.setEmail(persona.get().getEmail());
        dto.setTelefono(persona.get().getTelefono());
        dto.setDireccion(persona.get().getDireccion());
        dto.setGenero(persona.get().getGenero());
        dto.setFechaAlta(persona.get().getFechaAlta());
        return dto;
    }

    @Override
    public List<PersonaDTO> filtrarPorRol(String rol) {
        List<? extends Persona> personas;
        if ("estudiante".equalsIgnoreCase(rol)) {
            personas = estudianteRepository.findAll();
        } else if ("docente".equalsIgnoreCase(rol)) {
            personas = docenteRepository.findAll();
        } else {
            return List.of();
        }
        return personas.stream().map(persona -> {
            PersonaDTO dto = new PersonaDTO();
            dto.setIdPersona(persona.getIdPersona());
            dto.setCi(persona.getCi());
            dto.setNombre(persona.getNombre());
            dto.setApellidoPaterno(persona.getApellido_paterno());
            dto.setApellidoMaterno(persona.getApellido_materno());
            dto.setFechaNacimiento(persona.getFechaNacimiento());
            dto.setEmail(persona.getEmail());
            dto.setTelefono(persona.getTelefono());
            dto.setDireccion(persona.getDireccion());
            dto.setGenero(persona.getGenero());
            dto.setFechaAlta(persona.getFechaAlta());
            return dto;
        }).toList();
    }

}
