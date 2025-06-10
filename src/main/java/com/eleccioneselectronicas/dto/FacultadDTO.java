package com.eleccioneselectronicas.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultadDTO {

    private Long id;
    private String nombre;
    private String codigo;
    private LocalDate fechaCreacion;
    private Long idDecano;
    private Boolean estado;
    private String nombreDecano;
    private String ciDecano;
    private List<CarreraDTO> carreras;
}
