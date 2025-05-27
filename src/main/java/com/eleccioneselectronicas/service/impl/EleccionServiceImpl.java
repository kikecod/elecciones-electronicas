package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.EleccionDTO;
import com.eleccioneselectronicas.model.Eleccion;
import com.eleccioneselectronicas.repository.EleccionRepository;
import com.eleccioneselectronicas.service.EleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EleccionServiceImpl implements EleccionService {

    @Autowired
    private EleccionRepository eleccionRepository;

    @Override
    public List<EleccionDTO> listar() {
        return eleccionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EleccionDTO obtenerPorId(Long id) {
        return eleccionRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public EleccionDTO registrar(EleccionDTO dto) {
        Eleccion eleccion = toEntity(dto);
        return toDTO(eleccionRepository.save(eleccion));
    }

    @Override
    public EleccionDTO actualizar(Long id, EleccionDTO dto) {
        Optional<Eleccion> eleccionOpt = eleccionRepository.findById(id);
        if (eleccionOpt.isEmpty())
            return null;

        Eleccion eleccion = eleccionOpt.get();

        eleccion.setNombre(dto.getNombre());
        eleccion.setTipo(dto.getTipo());
        eleccion.setNivel(dto.getNivel());
        eleccion.setFechaInicio(dto.getFechaInicio());
        eleccion.setFechaFin(dto.getFechaFin());
        eleccion.setEstado(dto.getEstado());
        eleccion.setDescripcion(dto.getDescripcion());
        eleccion.setVersion(dto.getVersion());

        return toDTO(eleccionRepository.save(eleccion));
    }

    @Override
    public void eliminar(Long id) {
        eleccionRepository.deleteById(id);
    }

    @Override
    public List<EleccionDTO> buscarPorNombre(String nombre) {
        return eleccionRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EleccionDTO toDTO(Eleccion eleccion) {
        EleccionDTO dto = new EleccionDTO();
        dto.setId(eleccion.getId());
        dto.setNombre(eleccion.getNombre());
        dto.setTipo(eleccion.getTipo());
        dto.setNivel(eleccion.getNivel());
        dto.setFechaInicio(eleccion.getFechaInicio());
        dto.setFechaFin(eleccion.getFechaFin());
        dto.setEstado(eleccion.getEstado());
        dto.setDescripcion(eleccion.getDescripcion());
        dto.setVersion(eleccion.getVersion());
        return dto;
    }

    private Eleccion toEntity(EleccionDTO dto) {
        Eleccion eleccion = new Eleccion();
        eleccion.setNombre(dto.getNombre());
        eleccion.setTipo(dto.getTipo());
        eleccion.setNivel(dto.getNivel());
        eleccion.setFechaInicio(dto.getFechaInicio());
        eleccion.setFechaFin(dto.getFechaFin());
        eleccion.setEstado(dto.getEstado());
        eleccion.setDescripcion(dto.getDescripcion());
        eleccion.setVersion(dto.getVersion());
        return eleccion;
    }
}
