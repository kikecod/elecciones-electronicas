package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "voto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_votante")
    private Votante votante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_eleccion")
    private Eleccion eleccion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_partido")
    private Partido partido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_recinto")
    private Recinto recinto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_dispositivo")
    private Dispositivo dispositivo;

    private LocalDateTime emitidoTs;
    private String hashPrev;
    private String hashActual;
    private String folioRecibo;
    private Boolean valido;

    // Getters y setters
}
