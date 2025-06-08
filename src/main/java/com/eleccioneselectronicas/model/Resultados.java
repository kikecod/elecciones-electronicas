package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "resultados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resultados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultados;

    @ManyToOne
    @JoinColumn(name = "id_eleccion", nullable = false)
    private Eleccion eleccion;

    @ManyToOne
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato candidato;

    private Integer totalVotos;
    private Double porcentaje;

    private LocalDate fechaGeneracion;
    private LocalTime horaGeneracion;

    private Boolean esFinal;

    private String firma; // Puede ser hash o firma digital

    // Getters y setters
}