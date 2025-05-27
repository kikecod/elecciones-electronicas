package com.eleccioneselectronicas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleccioneselectronicas.dto.DocenteDTO;
import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.repository.DocenteRepository;
import com.eleccioneselectronicas.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService{
     @Autowired
    private DocenteRepository docenteRepository;

    private DocenteDTO toDTO(Docente d) {
        return DocenteDTO.builder()
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
        Docente d = docenteRepository.save(toEntity(dto));
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
        return toDTO(d);
    }

    @Override
    public void eliminarDocente(Long id) {
        Docente d = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        d.setActivo("no");
        docenteRepository.save(d);
    }
}
