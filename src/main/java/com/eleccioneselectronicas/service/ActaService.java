package com.eleccioneselectronicas.service;


public interface ActaService {
    byte[] generarActaRecintoPdf(Long idRecinto, Long idEleccion);
}
