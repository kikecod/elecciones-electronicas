package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.VotoDTO;
import com.eleccioneselectronicas.model.Votante;
import com.eleccioneselectronicas.model.Voto;
import com.eleccioneselectronicas.repository.*;
import com.eleccioneselectronicas.service.VotoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class VotoServiceImpl implements VotoService {

    @Autowired
    private VotoRepository votoRepo;
    @Autowired private VotanteRepository votanteRepo;
    @Autowired private EleccionRepository eleccionRepo;
    @Autowired private PartidoRepository partidoRepo;
    @Autowired private RecintoRepository recintoRepo;
    @Autowired private DispositivoRepository dispositivoRepo;

    @Override
    public VotoDTO emitirVoto(VotoDTO dto) {
        if (votoRepo.existsByVotante_IdVotanteAndEleccion_Id(dto.getIdVotante(), dto.getIdEleccion())) {
            throw new IllegalArgumentException("Este votante ya emitió su voto en esta elección.");
        }

        Votante votante = votanteRepo.findById(dto.getIdVotante()).orElseThrow();
        if (!Boolean.TRUE.equals(votante.getHabilitado())) {
            throw new IllegalStateException("Votante no habilitado para votar.");
        }

        Voto voto = new Voto();
        voto.setVotante(votante);
        voto.setEleccion(eleccionRepo.findById(dto.getIdEleccion()).orElseThrow());
        voto.setPartido(partidoRepo.findById(dto.getIdPartido()).orElseThrow());
        voto.setRecinto(recintoRepo.findById(dto.getIdRecinto()).orElseThrow());
        voto.setDispositivo(dispositivoRepo.findById(dto.getIdDispositivo()).orElseThrow());
        voto.setEmitidoTs(LocalDateTime.now());

        voto.setHashPrev(dto.getHashPrev()); // se puede encadenar si usas blockchain
        voto.setHashActual(generateHash(voto));
        voto.setFolioRecibo(UUID.randomUUID().toString());
        voto.setValido(true); // lógica adicional puede modificar esto

        return toDto(votoRepo.save(voto));
    }

    @Override
    public List<VotoDTO> listarTodos() {
        return votoRepo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public VotoDTO obtenerPorId(Long id) {
        return votoRepo.findById(id).map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Voto no encontrado"));
    }

    private VotoDTO toDto(Voto v) {
        VotoDTO dto = new VotoDTO();
        dto.setIdVoto(v.getIdVoto());
        dto.setIdVotante(v.getVotante().getIdVotante());
        dto.setIdEleccion(v.getEleccion().getId());
        dto.setIdPartido(v.getPartido().getIdPartido());
        dto.setIdRecinto(v.getRecinto().getIdRecinto());
        dto.setIdDispositivo(v.getDispositivo().getIdDispositivo());
        dto.setEmitidoTs(v.getEmitidoTs());
        dto.setHashPrev(v.getHashPrev());
        dto.setHashActual(v.getHashActual());
        dto.setFolioRecibo(v.getFolioRecibo());
        dto.setValido(v.getValido());
        return dto;
    }

    private String generateHash(Voto voto) {
        String base = voto.getVotante() + "|" + voto.getEleccion() + "|" + voto.getPartido() + "|" + LocalDateTime.now();
        return Base64.getEncoder().encodeToString(base.getBytes()); // simplificado
    }
}
