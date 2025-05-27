package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.CandidatoDTO;
import com.eleccioneselectronicas.model.Candidato;
import com.eleccioneselectronicas.model.Eleccion;
import com.eleccioneselectronicas.model.Partido;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.repository.CandidatoRepository;
import com.eleccioneselectronicas.repository.EleccionRepository;
import com.eleccioneselectronicas.repository.PartidoRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidatoServiceImpl implements CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private EleccionRepository eleccionRepository;

    @Override
    public List<CandidatoDTO> listar() {
        return candidatoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CandidatoDTO obtenerPorId(Long id) {
        return candidatoRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public CandidatoDTO registrar(CandidatoDTO dto) {
        Candidato candidato = toEntity(dto);
        return toDTO(candidatoRepository.save(candidato));
    }

    @Override
    public CandidatoDTO actualizar(Long id, CandidatoDTO dto) {
        Optional<Candidato> candidatoOpt = candidatoRepository.findById(id);
        if (candidatoOpt.isEmpty())
            return null;

        Candidato candidato = candidatoOpt.get();

        candidato.setCargoPostulado(dto.getCargoPostulado());
        candidato.setNumeroLista(dto.getNumeroLista());
        candidato.setEsCabezaLista(dto.getEsCabezaLista());
        candidato.setLema(dto.getLema());
        candidato.setPlanPropuesta(dto.getPlanPropuesta());
        candidato.setFechaPostulacion(dto.getFechaPostulacion());
        candidato.setEstado(dto.getEstado());

        if (dto.getIdPersona() != null) {
            Persona persona = personaRepository.findById(dto.getIdPersona()).orElse(null);
            candidato.setPersona(persona);
        }

        if (dto.getIdPartido() != null) {
            Partido partido = partidoRepository.findById(dto.getIdPartido()).orElse(null);
            candidato.setPartido(partido);
        }

        if (dto.getIdEleccion() != null) {
            Eleccion eleccion = eleccionRepository.findById(dto.getIdEleccion()).orElse(null);
            candidato.setEleccion(eleccion);
        }

        return toDTO(candidatoRepository.save(candidato));
    }

    @Override
    public void eliminar(Long id) {
        candidatoRepository.deleteById(id);
    }

    @Override
    public List<CandidatoDTO> buscarPorCargoPostulado(String cargoPostulado) {
        return candidatoRepository.findByCargoPostuladoContainingIgnoreCase(cargoPostulado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ========== Conversores DTO/Entidad ==========

    private CandidatoDTO toDTO(Candidato candidato) {
        CandidatoDTO dto = new CandidatoDTO();
        dto.setId(candidato.getId());
        dto.setIdPersona(candidato.getPersona() != null ? candidato.getPersona().getId() : null);
        dto.setIdPartido(candidato.getPartido() != null ? candidato.getPartido().getId() : null);
        dto.setIdEleccion(candidato.getEleccion() != null ? candidato.getEleccion().getId() : null);
        dto.setCargoPostulado(candidato.getCargoPostulado());
        dto.setNumeroLista(candidato.getNumeroLista());
        dto.setEsCabezaLista(candidato.getEsCabezaLista());
        dto.setLema(candidato.getLema());
        dto.setPlanPropuesta(candidato.getPlanPropuesta());
        dto.setFechaPostulacion(candidato.getFechaPostulacion());
        dto.setEstado(candidato.getEstado());
        return dto;
    }

    private Candidato toEntity(CandidatoDTO dto) {
        Candidato candidato = new Candidato();

        if (dto.getIdPersona() != null) {
            Persona persona = personaRepository.findById(dto.getIdPersona()).orElse(null);
            candidato.setPersona(persona);
        }

        if (dto.getIdPartido() != null) {
            Partido partido = partidoRepository.findById(dto.getIdPartido()).orElse(null);
            candidato.setPartido(partido);
        }

        if (dto.getIdEleccion() != null) {
            Eleccion eleccion = eleccionRepository.findById(dto.getIdEleccion()).orElse(null);
            candidato.setEleccion(eleccion);
        }

        candidato.setCargoPostulado(dto.getCargoPostulado());
        candidato.setNumeroLista(dto.getNumeroLista());
        candidato.setEsCabezaLista(dto.getEsCabezaLista());
        candidato.setLema(dto.getLema());
        candidato.setPlanPropuesta(dto.getPlanPropuesta());
        candidato.setFechaPostulacion(dto.getFechaPostulacion());
        candidato.setEstado(dto.getEstado());

        return candidato;
    }
}
