package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "votante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Votante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVotante;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_eleccion", nullable = false)
    private Eleccion eleccion;

    @ManyToOne
    @JoinColumn(name = "id_recinto", nullable = false)
    private Recinto recinto;

    @Column(nullable = false, unique = true)
    private String qrUuid;

    private String hashHuella;
    private String hashFirma;
    private String hashRostro;

    private Boolean habilitado;

    private LocalDateTime fechaHabilitacion;




}
