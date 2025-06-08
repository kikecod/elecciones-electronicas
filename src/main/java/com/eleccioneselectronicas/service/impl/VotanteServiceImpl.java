package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.VotanteDTO;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.model.Votante;
import com.eleccioneselectronicas.repository.EleccionRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.repository.RecintoRepository;
import com.eleccioneselectronicas.repository.VotanteRepository;
import com.eleccioneselectronicas.service.VotanteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VotanteServiceImpl implements VotanteService {

    @Autowired
    private VotanteRepository repository;

    @Autowired
    private PersonaRepository personaRepo;

    @Autowired
    private EleccionRepository eleccionRepo;

    @Autowired
    private RecintoRepository recintoRepo;

    @Override
    public List<VotanteDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public VotanteDTO obtenerPorId(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Votante no encontrado"));
    }

    @Override
public VotanteDTO crear(VotanteDTO dto) {
    Votante votante = new Votante();

    votante.setPersona(personaRepo.findById(dto.getIdPersona())
            .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada")));

    votante.setEleccion(eleccionRepo.findById(dto.getIdEleccion())
            .orElseThrow(() -> new EntityNotFoundException("Elección no encontrada")));

    votante.setRecinto(recintoRepo.findById(dto.getIdRecinto())
            .orElseThrow(() -> new EntityNotFoundException("Recinto no encontrado")));

    votante.setQrUuid(UUID.randomUUID().toString()); // GENERADO AUTOMÁTICAMENTE
    votante.setHashRostro(dto.getHashRostro());      // Imagen base64

    votante.setHashHuella(null);                     // Desactivado
    votante.setHashFirma(null);                      // Desactivado

    votante.setHabilitado(true);
    votante.setFechaHabilitacion(LocalDateTime.now());

    Votante guardado = repository.save(votante);
    return toDto(guardado);
}

    @Override
    public VotanteDTO actualizar(Long id, VotanteDTO dto) {
        Votante votante = repository.findById(id).orElseThrow();
        votante.setQrUuid(dto.getQrUuid());
        votante.setHashHuella(dto.getHashHuella());
        votante.setHashFirma(dto.getHashFirma());
        votante.setHashRostro(dto.getHashRostro());
        votante.setHabilitado(dto.getHabilitado());
        votante.setFechaHabilitacion(dto.getFechaHabilitacion());
        return toDto(repository.save(votante));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public VotanteDTO registrarConImagen(VotanteDTO dto) {
        Votante votante = new Votante();
        votante.setPersona(personaRepo.findById(dto.getIdPersona()).orElseThrow());
        votante.setEleccion(eleccionRepo.findById(dto.getIdEleccion()).orElseThrow());
        votante.setRecinto(recintoRepo.findById(dto.getIdRecinto()).orElseThrow());
        votante.setQrUuid(UUID.randomUUID().toString());
        votante.setHashRostro(dto.getHashRostro());
        votante.setHabilitado(true);
        votante.setFechaHabilitacion(LocalDateTime.now());

        votante.setHashFirma(null); // Desactivados
        votante.setHashHuella(null);

        Votante guardado = repository.save(votante);
        return toDto(guardado);
    }

    @Override
    public VotanteDTO buscarPorCI(String ci) {
        Persona persona = personaRepo.findByCi(ci)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));
        Votante votante = repository.findAll().stream()
                .filter(v -> v.getPersona().getIdPersona().equals(persona.getIdPersona()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Votante no encontrado"));
        return toDto(votante);
    }

    private VotanteDTO toDto(Votante v) {
        VotanteDTO dto = new VotanteDTO();
        dto.setIdVotante(v.getIdVotante());
        dto.setIdPersona(v.getPersona().getIdPersona());
        dto.setIdEleccion(v.getEleccion().getId());
        dto.setIdRecinto(v.getRecinto().getIdRecinto());
        dto.setQrUuid(v.getQrUuid());
        dto.setHashHuella(v.getHashHuella());
        dto.setHashFirma(v.getHashFirma());
        dto.setHashRostro(v.getHashRostro());
        dto.setHabilitado(v.getHabilitado());
        dto.setFechaHabilitacion(v.getFechaHabilitacion());
        return dto;
    }
}
