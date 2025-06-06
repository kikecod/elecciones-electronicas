package com.eleccioneselectronicas.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EstudianteTest {

    @Test
    void testConstructorYGettersSetters() {
        Estudiante estudiante = Estudiante.builder()
                .matricula("A12345")
                .fechaIngreso(LocalDate.of(2022, 1, 10))
                .semestreActual(3)
                .estado("Activo")
                .build();

        assertEquals("A12345", estudiante.getMatricula());
        assertEquals(LocalDate.of(2022, 1, 10), estudiante.getFechaIngreso());
        assertEquals(3, estudiante.getSemestreActual());
        assertEquals("Activo", estudiante.getEstado());

        estudiante.setEstado("Inactivo");
        assertEquals("Inactivo", estudiante.getEstado());
    }
}


