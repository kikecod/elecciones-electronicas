package com.eleccioneselectronicas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.model.Carrera;
import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.service.EstudianteService;
import com.eleccioneselectronicas.repository.CarreraRepository;
import com.eleccioneselectronicas.repository.EstudianteRepository;
import com.eleccioneselectronicas.repository.PersonaCarreraRepository;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private PersonaCarreraRepository personaCarreraRepository;

    private EstudianteDTO toDTO(Estudiante e) {
        EstudianteDTO dto = EstudianteDTO.builder()
                .id(e.getIdPersona())
                .ci(e.getCi())
                .nombre(e.getNombre())
                .apellido_paterno(e.getApellido_paterno())
                .apellido_materno(e.getApellido_materno())
                .fechaNacimiento(e.getFechaNacimiento())
                .email(e.getEmail())
                .telefono(e.getTelefono())
                .direccion(e.getDireccion())
                .genero(e.getGenero())
                .fechaAlta(e.getFechaAlta())
                .matricula(e.getMatricula())
                .fechaIngreso(e.getFechaIngreso())
                .semestreActual(e.getSemestreActual())
                .estado(e.getEstado())
                .build();

        // Obtener la carrera y facultad asociada
        if (e.getPersonaCarreras() != null && !e.getPersonaCarreras().isEmpty()) {
            PersonaCarrera pc = e.getPersonaCarreras().stream()
                    .filter(p -> p.getRol() == PersonaCarrera.Rol.ESTUDIANTE)
                    .findFirst().orElse(null);
            if (pc != null && pc.getCarrera() != null) {
                dto.setIdCarrera(pc.getCarrera().getId());
                dto.setNombreCarrera(pc.getCarrera().getNombre());
                if (pc.getCarrera().getFacultad() != null) {
                    dto.setNombreFacultad(pc.getCarrera().getFacultad().getNombre());
                }
            }
        }
        return dto;
    }

    private Estudiante toEntity(EstudianteDTO dto) {
        return Estudiante.builder()
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
                .matricula(dto.getMatricula())
                .fechaIngreso(dto.getFechaIngreso())
                .semestreActual(dto.getSemestreActual())
                .estado(dto.getEstado())
                .build();
    }

    @Override
    public EstudianteDTO crearEstudiante(EstudianteDTO dto) {
        if (estudianteRepository.existsByCi(dto.getCi())) {
            throw new RuntimeException("Ya existe un estudiante con ese CI");
        }
        Estudiante e = estudianteRepository.save(toEntity(dto));

        if (dto.getIdCarrera() != null) {
            Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
            PersonaCarrera personaCarrera = new PersonaCarrera();
            personaCarrera.setPersona(e);
            personaCarrera.setCarrera(carrera);
            personaCarrera.setRol(PersonaCarrera.Rol.ESTUDIANTE);
            personaCarrera.setFechaAsignacion(java.time.LocalDate.now());
            personaCarrera.setActivo(true);
            personaCarreraRepository.save(personaCarrera);
        }
        return toDTO(e);
    }

    @Override
    public EstudianteDTO obtenerEstudiantePorId(Long id) {
        Estudiante e = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return toDTO(e);
    }

    @Override
    public List<EstudianteDTO> obtenerEstudiante() {
        return estudianteRepository
                .findAll()
                .stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO dto) {
        Estudiante e = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        // ...actualiza los campos de e...
        estudianteRepository.save(e);

        // Actualizar la carrera asociada en PersonaCarrera
        if (dto.getIdCarrera() != null) {
            Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

            // Buscar la relación actual
            PersonaCarrera personaCarrera = personaCarreraRepository
                    .findByPersonaAndRol(e, PersonaCarrera.Rol.ESTUDIANTE)
                    .orElse(null);

            // Si existe y la carrera es diferente, elimina el registro anterior
            if (personaCarrera != null && !personaCarrera.getCarrera().getId().equals(dto.getIdCarrera())) {
                personaCarreraRepository.delete(personaCarrera);
                personaCarrera = null;
            }

            // Si no existe, crea la nueva relación
            if (personaCarrera == null) {
                personaCarrera = new PersonaCarrera();
                personaCarrera.setPersona(e);
                personaCarrera.setCarrera(carrera);
                personaCarrera.setRol(PersonaCarrera.Rol.ESTUDIANTE);
                personaCarrera.setFechaAsignacion(java.time.LocalDate.now());
                personaCarrera.setActivo(true);
                personaCarreraRepository.save(personaCarrera);
            }
        }

        return toDTO(e);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        estudianteRepository.deleteById(id);
    }
}
