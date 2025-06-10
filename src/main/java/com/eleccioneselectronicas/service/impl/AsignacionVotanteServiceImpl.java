package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.model.*;
import com.eleccioneselectronicas.repository.*;
import com.eleccioneselectronicas.service.AsignacionVotanteService;
import com.eleccioneselectronicas.dto.AsignacionVotanteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AsignacionVotanteServiceImpl implements AsignacionVotanteService {

    @Autowired
    private VotanteRepository votanteRepository;

    @Autowired
    private RecintoRepository recintoRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private AsignacionVotanteRepository asignacionRepository;

    @Override
    public List<AsignacionVotanteDTO> asignarRecintosYDispositivos() {
        List<Votante> votantes = votanteRepository.findAll()
                .stream()
                .filter(v -> Boolean.TRUE.equals(v.getHabilitado()))
                .toList();

        List<Recinto> recintos = recintoRepository.findAll();
        List<Dispositivo> dispositivos = dispositivoRepository.findAll();

        Random random = new Random();
        List<AsignacionVotanteDTO> asignados = new ArrayList<>();

        for (Votante v : votantes) {
            Recinto recintoAleatorio = recintos.get(random.nextInt(recintos.size()));
            Dispositivo dispositivoAleatorio = dispositivos.get(random.nextInt(dispositivos.size()));

            AsignacionVotante asignacion = AsignacionVotante.builder()
                    .votante(v)
                    .recinto(recintoAleatorio)
                    .dispositivo(dispositivoAleatorio)
                    .build();

            asignacionRepository.save(asignacion);

            asignados.add(new AsignacionVotanteDTO(
                    v.getIdVotante(),
                    recintoAleatorio.getIdRecinto(),
                    dispositivoAleatorio.getIdDispositivo()
            ));
        }

        return asignados;
    }
}
