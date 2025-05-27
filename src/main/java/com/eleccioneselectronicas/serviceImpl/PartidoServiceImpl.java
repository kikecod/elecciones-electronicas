package com.eleccioneselectronicas.serviceImpl;

import com.eleccioneselectronicas.dto.PartidoDTO;
import com.eleccioneselectronicas.model.Partido;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.repository.PartidoRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidoServiceImpl implements PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Partido crearPartido(PartidoDTO dto) {
        Partido partido = new Partido();
        partido.setNombre(dto.getNombre());
        partido.setSigla(dto.getSigla());
        partido.setColorHex(dto.getColorHex());
        partido.setLogoUrl(dto.getLogoUrl());
        partido.setDescripcion(dto.getDescripcion());
        partido.setRepresentante(personaRepository.findById(dto.getRepresentanteId()).orElse(null));
        return partidoRepository.save(partido);
    }

    @Override
    public List<Partido> listarPartidos() {
        return partidoRepository.findAll();
    }

    @Override
    public Partido actualizarPartido(Long id, PartidoDTO dto) {
        Partido partido = partidoRepository.findById(id).orElseThrow();
        partido.setNombre(dto.getNombre());
        partido.setSigla(dto.getSigla());
        partido.setColorHex(dto.getColorHex());
        partido.setLogoUrl(dto.getLogoUrl());
        partido.setDescripcion(dto.getDescripcion());
        partido.setRepresentante(personaRepository.findById(dto.getRepresentanteId()).orElse(null));
        return partidoRepository.save(partido);
    }

    @Override
    public void eliminarPartido(Long id) {
        partidoRepository.deleteById(id);
    }
}
