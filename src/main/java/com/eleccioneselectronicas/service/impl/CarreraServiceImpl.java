package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.CarreraDTO;
import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.model.Carrera;
import com.eleccioneselectronicas.model.Facultad;
import com.eleccioneselectronicas.repository.CarreraRepository;
import com.eleccioneselectronicas.repository.FacultadRepository;
import com.eleccioneselectronicas.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private FacultadRepository facultadRepository;

    @Override
    public List<CarreraDTO> listar() {
        return carreraRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarreraDTO obtenerPorId(Long id) {
        return carreraRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public CarreraDTO registrar(CarreraDTO dto) {
        Carrera carrera = toEntity(dto);
        return toDTO(carreraRepository.save(carrera));
    }

    @Override
    public CarreraDTO actualizar(Long id, CarreraDTO dto) {
        Optional<Carrera> carreraOpt = carreraRepository.findById(id);
        if (carreraOpt.isEmpty())
            return null;

        Carrera carrera = carreraOpt.get();
        carrera.setNombre(dto.getNombre());
        carrera.setCodigo(dto.getCodigo());
        carrera.setDuracionSemestres(dto.getDuracionSemestres());
        carrera.setEstado(dto.getEstado());

        if (dto.getIdFacultad() != null) {
            Facultad facultad = facultadRepository.findById(dto.getIdFacultad()).orElse(null);
            carrera.setFacultad(facultad);
        }

        return toDTO(carreraRepository.save(carrera));
    }

    @Override
    public void eliminar(Long id) {
        carreraRepository.deleteById(id);
    }

    @Override
    public List<CarreraDTO> buscarPorNombre(String nombre) {
        return carreraRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private CarreraDTO toDTO(Carrera carrera) {
        CarreraDTO dto = new CarreraDTO();
        dto.setId(carrera.getId());
        dto.setNombre(carrera.getNombre());
        dto.setCodigo(carrera.getCodigo());
        dto.setDuracionSemestres(carrera.getDuracionSemestres());
        dto.setEstado(carrera.getEstado());
        if (carrera.getFacultad() != null) {
            dto.setIdFacultad(carrera.getFacultad().getId());
        }
        if (carrera.getPersonaCarreras() != null) {
            dto.setNumeroEstudiantes(carrera.getPersonaCarreras().size());
        } else {
            dto.setNumeroEstudiantes(0);
        }
        return dto;
    }

    private Carrera toEntity(CarreraDTO dto) {
        Carrera carrera = new Carrera();
        carrera.setNombre(dto.getNombre());
        carrera.setCodigo(dto.getCodigo());
        carrera.setDuracionSemestres(dto.getDuracionSemestres());
        carrera.setEstado(dto.getEstado());

        if (dto.getIdFacultad() != null) {
            Facultad facultad = facultadRepository.findById(dto.getIdFacultad()).orElse(null);
            carrera.setFacultad(facultad);
        }

        return carrera;
    }
}
