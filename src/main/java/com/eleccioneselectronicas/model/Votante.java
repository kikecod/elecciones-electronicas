package com.eleccioneselectronicas.model;

import jakarta.persistence.*;
import lombok.*;

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




}
