package com.eleccioneselectronicas.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarreraDTO {

    private Long id;
    private Long idFacultad;
    private String nombre;
    private String codigo;
    private Integer duracionSemestres;
    private Boolean estado;
    private Integer numeroEstudiantes;
}
