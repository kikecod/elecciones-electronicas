package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.repository.EstudianteRepository;
import com.eleccioneselectronicas.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    private EstudianteDTO toDTO(Estudiante e) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setIdPersona(e.getIdPersona());
        dto.setCi(e.getCi());
        dto.setNombres(e.getNombres());
        dto.setApellidoPaterno(e.getApellidoPaterno());
        dto.setApellidoMaterno(e.getApellidoMaterno());
        dto.setFechaNac(e.getFechaNac());
        dto.setEmail(e.getEmail());
        dto.setTelefono(e.getTelefono());
        dto.setDireccion(e.getDireccion());
        dto.setGenero(e.getGenero());
        dto.setFechaAlta(e.getFechaAlta());
        dto.setEstado(e.getEstado());
        dto.setMatricula(e.getMatricula());
        dto.setFechaIngreso(e.getFechaIngreso());
        dto.setSemestreActual(e.getSemestreActual());
        return dto;
    }

    private Estudiante toEntity(EstudianteDTO dto) {
        Estudiante e = new Estudiante();
        e.setIdPersona(dto.getIdPersona());
        e.setCi(dto.getCi());
        e.setNombres(dto.getNombres());
        e.setApellidoPaterno(dto.getApellidoPaterno());
        e.setApellidoMaterno(dto.getApellidoMaterno());
        e.setFechaNac(dto.getFechaNac());
        e.setEmail(dto.getEmail());
        e.setTelefono(dto.getTelefono());
        e.setDireccion(dto.getDireccion());
        e.setGenero(dto.getGenero());
        e.setFechaAlta(dto.getFechaAlta());
        e.setEstado(dto.getEstado());
        e.setMatricula(dto.getMatricula());
        e.setFechaIngreso(dto.getFechaIngreso());
        e.setSemestreActual(dto.getSemestreActual());
        return e;
    }

    @Override
    public List<EstudianteDTO> listar() {
        return estudianteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public EstudianteDTO obtenerPorId(Long id) {
        return estudianteRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public EstudianteDTO registrar(EstudianteDTO dto) {
        return toDTO(estudianteRepository.save(toEntity(dto)));
    }

    @Override
    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {
        Estudiante e = toEntity(dto);
        e.setIdPersona(id);
        return toDTO(estudianteRepository.save(e));
    }

    @Override
    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }

    @Override
    public List<EstudianteDTO> buscarPorNombre(String nombre) {
        return estudianteRepository.findByNombresContainingIgnoreCase(nombre)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }
}
