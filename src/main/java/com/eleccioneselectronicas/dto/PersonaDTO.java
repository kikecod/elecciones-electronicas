package com.eleccioneselectronicas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private Long idPersona;
    private String ci;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private String direccion;
    private String genero;
    private LocalDate fechaAlta;
}