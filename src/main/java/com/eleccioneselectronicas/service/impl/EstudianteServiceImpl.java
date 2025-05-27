package com.eleccioneselectronicas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.service.EstudianteService;
import com.eleccioneselectronicas.repository.EstudianteRepository;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    private EstudianteDTO toDTO(Estudiante e) {
        return EstudianteDTO.builder()
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
        e.setCi(dto.getCi());
        e.setNombre(dto.getNombre());
        e.setApellido_paterno(dto.getApellido_paterno());
        e.setApellido_materno(dto.getApellido_materno());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setEmail(dto.getEmail());
        e.setTelefono(dto.getTelefono());
        e.setDireccion(dto.getDireccion());
        e.setGenero(dto.getGenero());
        e.setFechaAlta(dto.getFechaAlta());
        e.setMatricula(dto.getMatricula());
        e.setFechaIngreso(dto.getFechaIngreso());
        e.setSemestreActual(dto.getSemestreActual());
        e.setEstado(dto.getEstado());
        estudianteRepository.save(e);
        return toDTO(e);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        Estudiante e = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        e.setEstado("inactivo");
        estudianteRepository.save(e);
    }
}
