package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.DocenteDTO;
import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.repository.DocenteRepository;
import com.eleccioneselectronicas.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    private DocenteDTO toDTO(Docente d) {
        DocenteDTO dto = new DocenteDTO();
        dto.setIdPersona(d.getIdPersona());
        dto.setCi(d.getCi());
        dto.setNombres(d.getNombres());
        dto.setApellidoPaterno(d.getApellidoPaterno());
        dto.setApellidoMaterno(d.getApellidoMaterno());
        dto.setFechaNac(d.getFechaNac());
        dto.setEmail(d.getEmail());
        dto.setTelefono(d.getTelefono());
        dto.setDireccion(d.getDireccion());
        dto.setGenero(d.getGenero());
        dto.setFechaAlta(d.getFechaAlta());
        dto.setEstado(d.getEstado());
        dto.setCategoriaDocente(d.getCategoriaDocente());
        dto.setGradoAcademico(d.getGradoAcademico());
        dto.setFechaIngreso(d.getFechaIngreso());
        dto.setActivo(d.getActivo());
        return dto;
    }

    private Docente toEntity(DocenteDTO dto) {
        Docente d = new Docente();
        d.setIdPersona(dto.getIdPersona());
        d.setCi(dto.getCi());
        d.setNombres(dto.getNombres());
        d.setApellidoPaterno(dto.getApellidoPaterno());
        d.setApellidoMaterno(dto.getApellidoMaterno());
        d.setFechaNac(dto.getFechaNac());
        d.setEmail(dto.getEmail());
        d.setTelefono(dto.getTelefono());
        d.setDireccion(dto.getDireccion());
        d.setGenero(dto.getGenero());
        d.setFechaAlta(dto.getFechaAlta());
        d.setEstado(dto.getEstado());
        d.setCategoriaDocente(dto.getCategoriaDocente());
        d.setGradoAcademico(dto.getGradoAcademico());
        d.setFechaIngreso(dto.getFechaIngreso());
        d.setActivo(dto.getActivo());
        return d;
    }

    @Override
    public List<DocenteDTO> listar() {
        return docenteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DocenteDTO obtenerPorId(Long id) {
        return docenteRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public DocenteDTO registrar(DocenteDTO dto) {
        return toDTO(docenteRepository.save(toEntity(dto)));
    }

    @Override
    public DocenteDTO actualizar(Long id, DocenteDTO dto) {
        Docente d = toEntity(dto);
        d.setIdPersona(id);
        return toDTO(docenteRepository.save(d));
    }

    @Override
    public void eliminar(Long id) {
        docenteRepository.deleteById(id);
    }

    @Override
    public List<DocenteDTO> buscarPorNombre(String nombre) {
        return docenteRepository.findByNombresContainingIgnoreCase(nombre).stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
