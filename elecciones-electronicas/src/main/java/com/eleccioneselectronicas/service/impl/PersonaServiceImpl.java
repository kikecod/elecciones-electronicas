package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.PersonaDTO;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    private PersonaDTO toDTO(Persona p) {
        PersonaDTO dto = new PersonaDTO();
        dto.setIdPersona(p.getIdPersona());
        dto.setCi(p.getCi());
        dto.setNombres(p.getNombres());
        dto.setApellidoPaterno(p.getApellidoPaterno());
        dto.setApellidoMaterno(p.getApellidoMaterno());
        dto.setFechaNac(p.getFechaNac());
        dto.setEmail(p.getEmail());
        dto.setTelefono(p.getTelefono());
        dto.setDireccion(p.getDireccion());
        dto.setGenero(p.getGenero());
        dto.setFechaAlta(p.getFechaAlta());
        dto.setEstado(p.getEstado());
        return dto;
    }

    private Persona toEntity(PersonaDTO dto) {
        Persona p = new Persona();
        p.setIdPersona(dto.getIdPersona());
        p.setCi(dto.getCi());
        p.setNombres(dto.getNombres());
        p.setApellidoPaterno(dto.getApellidoPaterno());
        p.setApellidoMaterno(dto.getApellidoMaterno());
        p.setFechaNac(dto.getFechaNac());
        p.setEmail(dto.getEmail());
        p.setTelefono(dto.getTelefono());
        p.setDireccion(dto.getDireccion());
        p.setGenero(dto.getGenero());
        p.setFechaAlta(dto.getFechaAlta());
        p.setEstado(dto.getEstado());
        return p;
    }

    @Override
    public List<PersonaDTO> listar() {
        return personaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PersonaDTO obtenerPorId(Long id) {
        Optional<Persona> op = personaRepository.findById(id);
        return op.map(this::toDTO).orElse(null);
    }

    @Override
    public PersonaDTO registrar(PersonaDTO dto) {
        Persona persona = toEntity(dto);
        return toDTO(personaRepository.save(persona));
    }

    @Override
    public PersonaDTO actualizar(Long id, PersonaDTO dto) {
        Persona persona = toEntity(dto);
        persona.setIdPersona(id);
        return toDTO(personaRepository.save(persona));
    }

    @Override
    public void eliminar(Long id) {
        personaRepository.deleteById(id);
    }

    @Override
    public List<PersonaDTO> buscarPorNombre(String nombre) {
        return personaRepository.findByNombresContainingIgnoreCase(nombre).stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
