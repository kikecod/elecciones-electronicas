package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asignacion_votante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionVotante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_votante")
    private Votante votante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_recinto")
    private Recinto recinto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_dispositivo")
    private Dispositivo dispositivo;
}
