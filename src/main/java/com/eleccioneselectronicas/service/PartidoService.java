package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.PartidoDTO;
import com.eleccioneselectronicas.model.Partido;
import java.util.List;

public interface PartidoService {
    Partido crearPartido(PartidoDTO dto);
    List<Partido> listarPartidos();
    Partido actualizarPartido(Long id, PartidoDTO dto);
    void eliminarPartido(Long id);
    Partido actualizarLogo(Long id, String string);
    List<Partido> obtenerPartidoporIdEleccion(Long idEleccion);
}
