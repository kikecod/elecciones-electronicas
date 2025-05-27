package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.RecintoDTO;
import com.eleccioneselectronicas.model.Recinto;
import com.eleccioneselectronicas.repository.RecintoRepository;
import com.eleccioneselectronicas.service.RecintoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecintoServiceImpl implements RecintoService {

    private final RecintoRepository repository;

    @Override
    public RecintoDTO crear(RecintoDTO dto) {
        Recinto recinto = repository.save(toEntity(dto));
        return toDTO(recinto);
    }

    @Override
    public RecintoDTO obtenerPorId(Long id) {
        return repository.findById(id).map(this::toDTO).orElseThrow();
    }

    @Override
    public List<RecintoDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RecintoDTO actualizar(Long id, RecintoDTO dto) {
        Recinto recinto = repository.findById(id).orElseThrow();
        recinto.setNombre(dto.getNombre());
        recinto.setCampus(dto.getCampus());
        recinto.setEdificio(dto.getEdificio());
        recinto.setAula(dto.getAula());
        recinto.setCapacidad(dto.getCapacidad());
        recinto.setEstado(dto.getEstado());
        return toDTO(repository.save(recinto));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private RecintoDTO toDTO(Recinto recinto) {
        return RecintoDTO.builder()
                .id(recinto.getIdRecinto())
                .nombre(recinto.getNombre())
                .campus(recinto.getCampus())
                .edificio(recinto.getEdificio())
                .aula(recinto.getAula())
                .capacidad(recinto.getCapacidad())
                .estado(recinto.getEstado())
                .build();
    }

    private Recinto toEntity(RecintoDTO dto) {
        return Recinto.builder()
                .idRecinto(dto.getId())
                .nombre(dto.getNombre())
                .campus(dto.getCampus())
                .edificio(dto.getEdificio())
                .aula(dto.getAula())
                .capacidad(dto.getCapacidad())
                .estado(dto.getEstado())
                .build();
    }
}
