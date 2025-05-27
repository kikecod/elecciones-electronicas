package com.eleccioneselectronicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.persistence.FetchType;


@Entity
@Table(name = "dispositivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDispositivo;

    @Column(unique= true, nullable= false)
    private String serie;

    private String modelo;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "id_recinto")
    private Recinto recinto;

    @Column(columnDefinition= "TEXT")
    private String clavePublica;

    private LocalDate ultimaRevision;
    private Boolean estado;

}
