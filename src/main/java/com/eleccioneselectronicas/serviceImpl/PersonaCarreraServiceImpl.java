package com.eleccioneselectronicas.serviceImpl;

import com.eleccioneselectronicas.dto.PersonaCarreraDTO;
import com.eleccioneselectronicas.model.Carrera;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.model.PersonaCarreraId;

import com.eleccioneselectronicas.repository.CarreraRepository;


import com.eleccioneselectronicas.repository.PersonaCarreraRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.service.PersonaCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaCarreraServiceImpl implements PersonaCarreraService {

    @Autowired
    private PersonaCarreraRepository repository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    public PersonaCarrera crear(PersonaCarreraDTO dto) {
        Persona persona = personaRepository.findById(dto.getIdPersona()).orElseThrow();
        Carrera carrera = carreraRepository.findById(dto.getIdCarrera()).orElseThrow();

        PersonaCarrera pc = new PersonaCarrera();
        pc.setPersona(persona);
        pc.setCarrera(carrera);
        pc.setRol(dto.getRol());
        pc.setFechaAsignacion(dto.getFechaAsignacion());
        pc.setActivo(dto.getActivo());

        return repository.save(pc);
    }

    @Override
    public List<PersonaCarrera> listar() {
        return repository.findAll();
    }

    @Override
    public PersonaCarrera actualizar(Long idPersona, Long idCarrera, PersonaCarreraDTO dto) {
        PersonaCarreraId id = new PersonaCarreraId(idPersona, idCarrera);
        PersonaCarrera pc = repository.findById(id).orElseThrow();

        pc.setRol(dto.getRol());
        pc.setFechaAsignacion(dto.getFechaAsignacion());
        pc.setActivo(dto.getActivo());

        return repository.save(pc);
    }

    @Override
    public void eliminar(Long idPersona, Long idCarrera) {
        PersonaCarreraId id = new PersonaCarreraId(idPersona, idCarrera);
        repository.deleteById(id);
    }
}
