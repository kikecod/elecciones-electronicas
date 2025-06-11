package com.eleccioneselectronicas.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carrera")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrera")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_facultad", referencedColumnName = "id_facultad")
    private Facultad facultad;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(name = "duracion_semestres", nullable = false)
    private Integer duracionSemestres;

    @Column(nullable = false)
    private Boolean estado;

    @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<PersonaCarrera> personaCarreras;
}
