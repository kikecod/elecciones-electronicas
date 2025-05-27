package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.FacultadDTO;
import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.model.Facultad;
import com.eleccioneselectronicas.repository.DocenteRepository;
import com.eleccioneselectronicas.repository.FacultadRepository;
import com.eleccioneselectronicas.service.FacultadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultadServiceImpl implements FacultadService {

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public List<FacultadDTO> listar() {
        return facultadRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public FacultadDTO obtenerPorId(Long id) {
        return facultadRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public FacultadDTO registrar(FacultadDTO dto) {
        Facultad facultad = toEntity(dto);
        return toDTO(facultadRepository.save(facultad));
    }

    @Override
    public FacultadDTO actualizar(Long id, FacultadDTO dto) {
        Optional<Facultad> facultadOpt = facultadRepository.findById(id);
        if (facultadOpt.isEmpty())
            return null;

        Facultad facultad = facultadOpt.get();
        facultad.setNombre(dto.getNombre());
        facultad.setCodigo(dto.getCodigo());
        facultad.setFechaCreacion(dto.getFechaCreacion());
        facultad.setEstado(dto.getEstado());

        if (dto.getIdDecano() != null) {
            Docente decano = docenteRepository.findById(dto.getIdDecano()).orElse(null);
            facultad.setDecano(decano);
        }

        return toDTO(facultadRepository.save(facultad));
    }

    @Override
    public void eliminar(Long id) {
        facultadRepository.deleteById(id);
    }

    @Override
    public List<FacultadDTO> buscarPorNombre(String nombre) {
        return facultadRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private FacultadDTO toDTO(Facultad f) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(f.getId());
        dto.setNombre(f.getNombre());
        dto.setCodigo(f.getCodigo());
        dto.setFechaCreacion(f.getFechaCreacion());
        dto.setEstado(f.getEstado());
        if (f.getDecano() != null) {
            dto.setIdDecano(f.getDecano().getIdPersona());
        }
        return dto;
    }

    private Facultad toEntity(FacultadDTO dto) {
        Facultad f = new Facultad();
        f.setNombre(dto.getNombre());
        f.setCodigo(dto.getCodigo());
        f.setFechaCreacion(dto.getFechaCreacion());
        f.setEstado(dto.getEstado());

        if (dto.getIdDecano() != null) {
            Docente decano = docenteRepository.findById(dto.getIdDecano()).orElse(null);
            f.setDecano(decano);
        }

        return f;
    }
}
