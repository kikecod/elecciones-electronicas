package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.*; // Import your DTOs
import com.eleccioneselectronicas.model.AsignacionVotante;
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
    @Autowired private DispositivoRepository dispositivoRepo; // Assuming this is correct
    @Autowired private AsignacionVotanteRepository asignacionVotanteRepo;

    // Change the input DTO to EmitirVotoRequestDTO
    @Override
    public VotoDTO emitirVoto(EmitirVotoDTO dto) { // Changed VotoDTO to EmitirVotoRequestDTO for input
        if (votoRepo.existsByVotante_IdVotanteAndEleccion_Id(dto.getIdVotante(), dto.getIdEleccion())) {
            throw new IllegalArgumentException("Este votante ya emitió su voto en esta elección.");
        }

        Votante votante = votanteRepo.findById(dto.getIdVotante())
                .orElseThrow(() -> new EntityNotFoundException("Votante no encontrado"));

        if (!Boolean.TRUE.equals(votante.getHabilitado())) {
            throw new IllegalStateException("Votante no habilitado para votar.");
        }

        AsignacionVotante asignacion = asignacionVotanteRepo.findByVotante_IdVotante(dto.getIdVotante());
        if (asignacion == null) {
            throw new EntityNotFoundException("Asignación de votante no encontrada para el votante con ID: " + dto.getIdVotante());
        }

        Voto voto = new Voto();
        voto.setVotante(votante);
        voto.setEleccion(eleccionRepo.findById(dto.getIdEleccion()).orElseThrow(() -> new EntityNotFoundException("Elección no encontrada")));
        voto.setPartido(partidoRepo.findById(dto.getIdPartido()).orElseThrow(() -> new EntityNotFoundException("Partido no encontrado")));
        voto.setRecinto(recintoRepo.findById(asignacion.getRecinto().getIdRecinto())
                .orElseThrow(() -> new EntityNotFoundException("Recinto no encontrado")));
        voto.setDispositivo(dispositivoRepo.findById(asignacion.getDispositivo().getIdDispositivo())
                .orElseThrow(() -> new EntityNotFoundException("Dispositivo no encontrado")));
        voto.setEmitidoTs(LocalDateTime.now());

        // In a real blockchain-like scenario, hashPrev would link to the previous vote's hash
        // For simplicity, we'll leave it null for now or set it based on your chain logic
        voto.setHashPrev(null); // Or fetch the last hash from the blockchain/database if applicable
        voto.setHashActual(generateHash(voto));
        voto.setFolioRecibo(UUID.randomUUID().toString());
        voto.setValido(true);

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

    @Override
    public UbicacionVotacionDTO obtenerUbicacionPorCI(String ci) {
        Votante votante = votanteRepo.findByPersona_Ci(ci)
                .orElseThrow(() -> new IllegalArgumentException("Votante no encontrado"));

        Voto voto = votoRepo.findTopByVotante_IdVotanteOrderByEmitidoTsDesc(votante.getIdVotante())
                .orElseThrow(() -> new IllegalArgumentException("No hay registro de voto para este votante"));

        return new UbicacionVotacionDTO(
                voto.getRecinto().getNombre(),
                voto.getDispositivo().getSerie()
        );
    }

    @Override
    public ValidacionUbicacionResponseDTO validarUbicacion(String ci, Long idRecinto, ValidacionUbicacionRequestDTO dto) {
        Votante votante = votanteRepo.findByPersona_Ci(ci)
                .orElseThrow(() -> new IllegalArgumentException("Votante no encontrado"));

        Voto voto = votoRepo.findTopByVotante_IdVotanteOrderByEmitidoTsDesc(votante.getIdVotante())
                .orElseThrow(() -> new IllegalArgumentException("No hay registro de voto para este votante"));

        boolean valido = voto.getRecinto().getIdRecinto().equals(idRecinto)
                && voto.getDispositivo().getIdDispositivo().equals(dto.getIdDispositivo());

        return new ValidacionUbicacionResponseDTO(
                valido,
                valido ? "Ubicación correcta para emitir voto." : "Ubicación incorrecta."
        );
    }

    // This method converts a Voto entity to a VotoDTO (which now contains all fields)
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
        String base = voto.getVotante().getIdVotante() + "|" + voto.getEleccion().getId() + "|" + voto.getPartido().getIdPartido() + "|" + voto.getEmitidoTs();
        return Base64.getEncoder().encodeToString(base.getBytes());
    }
}