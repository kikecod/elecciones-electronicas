package com.eleccioneselectronicas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "recinto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recinto {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRecinto;

    private String nombre;
    private String campus;
    private String edificio;
    private String aula;
    private Integer capacidad;
    private String estado;
}
