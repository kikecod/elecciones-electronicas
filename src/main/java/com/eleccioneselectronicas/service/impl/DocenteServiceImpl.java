package com.eleccioneselectronicas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleccioneselectronicas.dto.DocenteDTO;
import com.eleccioneselectronicas.model.Carrera;
import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.repository.CarreraRepository;
import com.eleccioneselectronicas.repository.DocenteRepository;
import com.eleccioneselectronicas.repository.PersonaCarreraRepository;
import com.eleccioneselectronicas.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private PersonaCarreraRepository personaCarreraRepository;

    private DocenteDTO toDTO(Docente d) {
        DocenteDTO dto = DocenteDTO.builder()
                .id(d.getIdPersona())
                .ci(d.getCi())
                .nombre(d.getNombre())
                .apellido_paterno(d.getApellido_paterno())
                .apellido_materno(d.getApellido_materno())
                .fechaNacimiento(d.getFechaNacimiento())
                .email(d.getEmail())
                .telefono(d.getTelefono())
                .direccion(d.getDireccion())
                .genero(d.getGenero())
                .fechaAlta(d.getFechaAlta())
                .categoriaDocente(d.getCategoriaDocente())
                .gradoAcademico(d.getGradoAcademico())
                .fechaIngreso(d.getFechaIngreso())
                .activo(d.getActivo())
                .build();

        if (d.getPersonaCarreras() != null && !d.getPersonaCarreras().isEmpty()) {
            // Busca la relación con rol DOCENTE
            PersonaCarrera personaCarrera = d.getPersonaCarreras().stream()
                    .filter(pc -> pc.getRol() == PersonaCarrera.Rol.DOCENTE)
                    .findFirst().orElse(null);

            if (personaCarrera != null && personaCarrera.getCarrera() != null) {
                dto.setIdCarrera(personaCarrera.getCarrera().getId());
                dto.setNombreCarrera(personaCarrera.getCarrera().getNombre());
                if (personaCarrera.getCarrera().getFacultad() != null) {
                    dto.setNombreFacultad(personaCarrera.getCarrera().getFacultad().getNombre());
                }
            }
        }
        return dto;
    }

    private Docente toEntity(DocenteDTO dto) {
        return Docente.builder()
                .idPersona(dto.getId())
                .ci(dto.getCi())
                .nombre(dto.getNombre())
                .apellido_paterno(dto.getApellido_paterno())
                .apellido_materno(dto.getApellido_materno())
                .fechaNacimiento(dto.getFechaNacimiento())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .genero(dto.getGenero())
                .fechaAlta(dto.getFechaAlta())
                .categoriaDocente(dto.getCategoriaDocente())
                .gradoAcademico(dto.getGradoAcademico())
                .fechaIngreso(dto.getFechaIngreso())
                .activo(dto.getActivo())
                .build();
    }

    @Override
    public DocenteDTO crearDocente(DocenteDTO dto) {
        if (docenteRepository.existsByCi(dto.getCi())) {
            throw new RuntimeException("Ya existe un docente con ese CI");
        }
        // 1. Guardar el docente
        Docente d = docenteRepository.save(toEntity(dto));

        // 2. Asociar a carrera en PersonaCarrera
        if (dto.getIdCarrera() != null) {
            Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
            PersonaCarrera personaCarrera = new PersonaCarrera();
            personaCarrera.setPersona(d); // Docente hereda de Persona
            personaCarrera.setCarrera(carrera);
            personaCarrera.setRol(PersonaCarrera.Rol.DOCENTE);
            personaCarrera.setFechaAsignacion(java.time.LocalDate.now());
            personaCarrera.setActivo(true);
            personaCarreraRepository.save(personaCarrera);
        }

        return toDTO(d);
    }

    @Override
    public DocenteDTO obtenerDocentePorId(Long id) {
        Docente d = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        return toDTO(d);
    }

    @Override
    public List<DocenteDTO> obtenerDocentes() {
        return docenteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DocenteDTO actualizarDocente(Long id, DocenteDTO dto) {
        Docente d = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        d.setCi(dto.getCi());
        d.setNombre(dto.getNombre());
        d.setApellido_paterno(dto.getApellido_paterno());
        d.setApellido_materno(dto.getApellido_materno());
        d.setFechaNacimiento(dto.getFechaNacimiento());
        d.setEmail(dto.getEmail());
        d.setTelefono(dto.getTelefono());
        d.setDireccion(dto.getDireccion());
        d.setGenero(dto.getGenero());
        d.setFechaAlta(dto.getFechaAlta());
        d.setCategoriaDocente(dto.getCategoriaDocente());
        d.setGradoAcademico(dto.getGradoAcademico());
        d.setFechaIngreso(dto.getFechaIngreso());
        d.setActivo(dto.getActivo());
        docenteRepository.save(d);

        // Actualizar la carrera asociada en PersonaCarrera
        if (dto.getIdCarrera() != null) {
            Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

            // Buscar la relación actual
            PersonaCarrera personaCarrera = personaCarreraRepository
                    .findByPersonaAndRol(d, PersonaCarrera.Rol.DOCENTE)
                    .orElse(null);

            // Si existe y la carrera es diferente, elimina el registro anterior
            if (personaCarrera != null && !personaCarrera.getCarrera().getId().equals(dto.getIdCarrera())) {
                personaCarreraRepository.delete(personaCarrera);
                personaCarrera = null;
            }

            // Si no existe, crea la nueva relación
            if (personaCarrera == null) {
                personaCarrera = new PersonaCarrera();
                personaCarrera.setPersona(d);
                personaCarrera.setCarrera(carrera);
                personaCarrera.setRol(PersonaCarrera.Rol.DOCENTE);
                personaCarrera.setFechaAsignacion(java.time.LocalDate.now());
                personaCarrera.setActivo(true);
                personaCarreraRepository.save(personaCarrera);
            }
        }

        return toDTO(d);
    }

    @Override
    public void eliminarDocente(Long id) {
        if (!docenteRepository.existsById(id)) {
            throw new RuntimeException("Docente no encontrado");
        }
        docenteRepository.deleteById(id);
    }
}
