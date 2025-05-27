package com.eleccioneselectronicas.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteDTO implements Serializable{
    
    private Long id;
    @NotBlank(message = "El CI es obligatorio")
    @Size(min = 3, max = 20, message = "El CI debe tener entre 3 y 20 caracteres")
    private String ci;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 15, message = "El nombre debe tener entre 3 y 15 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 3, max = 15, message = "El apellido paterno debe tener entre 3 y 15 caracteres")
    private String apellido_paterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(min = 3, max = 15, message = "El apellido materno debe tener entre 3 y 15 caracteres")
    private String apellido_materno;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 7, max = 20, message = "El teléfono debe tener entre 7 y 20 caracteres")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 3, max = 35, message = "La dirección debe tener entre 3 y 35 caracteres")
    private String direccion;

    //@Size(min = 3, max = 10, message = "El género debe tener entre 3 y 10 caracteres")
    private String genero;

    @NotNull(message = "La fecha de alta es obligatoria")
    @PastOrPresent(message = "La fecha de alta debe ser anterior o igual a la fecha actual")
    private LocalDate fechaAlta;

    @NotBlank(message = "La matrícula es obligatoria")
    @Size(min = 3, max = 20, message = "La matrícula debe tener entre 3 y 20 caracteres")
    private String matricula;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate fechaIngreso;

    @NotNull(message = "El semestre actual es obligatorio")
    @Min(value = 1, message = "El semestre actual debe ser mayor o igual a 1")
    private Integer semestreActual;

    @NotBlank(message = "El campo activo es obligatorio")
    private String estado;
}
