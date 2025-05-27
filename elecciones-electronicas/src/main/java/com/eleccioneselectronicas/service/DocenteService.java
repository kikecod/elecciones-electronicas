package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.DocenteDTO;
import java.util.List;

public interface DocenteService {
    List<DocenteDTO> listar();
    DocenteDTO obtenerPorId(Long id);
    DocenteDTO registrar(DocenteDTO dto);
    DocenteDTO actualizar(Long id, DocenteDTO dto);
    void eliminar(Long id);
    List<DocenteDTO> buscarPorNombre(String nombre);
}
