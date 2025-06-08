package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.ResultadosDTO;
import com.eleccioneselectronicas.model.Candidato;
import com.eleccioneselectronicas.model.Eleccion;
import com.eleccioneselectronicas.model.Resultados;
import com.eleccioneselectronicas.repository.CandidatoRepository;
import com.eleccioneselectronicas.repository.EleccionRepository;
import com.eleccioneselectronicas.repository.ResultadosRepository;
import com.eleccioneselectronicas.repository.VotoRepository;
import com.eleccioneselectronicas.service.ResultadosService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ResultadosServiceImpl implements ResultadosService {

    @Autowired
    private ResultadosRepository resultadosRepo;
    @Autowired private EleccionRepository eleccionRepo;
    @Autowired private CandidatoRepository candidatoRepo;
    @Autowired private VotoRepository votoRepo;

    @Override
    public List<ResultadosDTO> generarResultados(Long idEleccion) {
        Eleccion eleccion = eleccionRepo.findById(idEleccion).orElseThrow();
        List<Candidato> candidatos = candidatoRepo.findByEleccion_Id(idEleccion);

        long totalVotosValidos = votoRepo.findByEleccion_Id(idEleccion).stream()
                .filter(v -> Boolean.TRUE.equals(v.getValido()))
                .count();

        List<ResultadosDTO> resultadoFinal = new ArrayList<>();

        for (Candidato c : candidatos) {
            long votosCandidato = votoRepo.findByEleccion_Id(idEleccion).stream()
                    .filter(v -> v.getValido() && v.getPartido().getIdPartido().equals(c.getPartido().getIdPartido()))
                    .count();

            double porcentaje = totalVotosValidos == 0 ? 0.0 : (votosCandidato * 100.0) / totalVotosValidos;

            Resultados r = new Resultados();
            r.setEleccion(eleccion);
            r.setCandidato(c);
            r.setTotalVotos((int) votosCandidato);
            r.setPorcentaje(porcentaje);
            r.setFechaGeneracion(LocalDate.now());
            r.setHoraGeneracion(LocalTime.now());
            r.setEsFinal(false); // puede activarse al cierre
            r.setFirma(UUID.randomUUID().toString()); // simulación

            resultadoFinal.add(toDTO(resultadosRepo.save(r)));
        }

        return resultadoFinal;
    }

    @Override
    public List<ResultadosDTO> listarPorEleccion(Long idEleccion) {
        return resultadosRepo.findByEleccion_Id(idEleccion).stream().map(this::toDTO).toList();
    }

    @Override
    public ResultadosDTO obtenerPorId(Long id) {
        return resultadosRepo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Resultado no encontrado"));
    }

    private ResultadosDTO toDTO(Resultados r) {
        ResultadosDTO dto = new ResultadosDTO();
        dto.setIdResultados(r.getIdResultados());
        dto.setIdEleccion(r.getEleccion().getId());
        dto.setIdCandidato(r.getCandidato().getId());
        dto.setTotalVotos(r.getTotalVotos());
        dto.setPorcentaje(r.getPorcentaje());
        dto.setFechaGeneracion(r.getFechaGeneracion());
        dto.setHoraGeneracion(r.getHoraGeneracion());
        dto.setEsFinal(r.getEsFinal());
        dto.setFirma(r.getFirma());
        return dto;
    }
}
