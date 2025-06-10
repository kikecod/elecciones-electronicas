package com.eleccioneselectronicas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ActaRecintoDTO {
    private String nombre = "Acta de escrutinio del recinto";
    private LocalDate fechaGeneracion;
    private String nombreEleccion;
    private String nombreRecinto;
    private String codigoRecinto;
    private List<String> nombresDispositivos;
    private String totalVotos;
    private List<ResultadosDispositivoDTO> resultadosCandidatosDispositivos;
    private String resumen ="Resumen de resultados del recinto";
    private List<TotalVotosCandidatosDTO> totalVotosCandidatos;
    private List<EncargadosRecintosDTO> actaRecintos;
    private String observaciones;
    private String QRUnico;
}
