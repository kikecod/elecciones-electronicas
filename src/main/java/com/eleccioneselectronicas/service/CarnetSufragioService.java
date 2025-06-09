package com.eleccioneselectronicas.service;

public interface CarnetSufragioService {
    byte[] generarPdfCarnet(Long idVotante);
    void enviarCarnetPorEmail(Long idVotante);
}
