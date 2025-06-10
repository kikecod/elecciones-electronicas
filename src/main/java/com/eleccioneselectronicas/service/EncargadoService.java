package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.AsignacionEncargadosDTO;
import com.eleccioneselectronicas.dto.EncargadoInfoDTO;

public interface EncargadoService {
    void asignarEncargadosARecinto(AsignacionEncargadosDTO dto);
    EncargadoInfoDTO buscarEncargadoPorCi(String ci);
    byte[] generarPdfEncargadosPorEleccion(Long idEleccion);

}
