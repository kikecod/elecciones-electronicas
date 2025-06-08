package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Estudiante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EstudianteRepositoryIT {

    @Autowired
    private EstudianteRepository repository;

    @Test
    void existsByCi_debeRetornarTrueSiExiste() {
        Estudiante estudiante = Estudiante.builder()
                .nombre("Juan")
                .apellido_paterno("Perez")
                .apellido_materno("Gomez")
                .email("acac@gmail.com")
                .direccion("gfgeqgqwgfawf")
                .telefono("123456789")
                .fechaNacimiento(LocalDate.of(2000, 1, 1))
                .fechaAlta(LocalDate.of(2022, 1, 10))
                .matricula("A12345")
                .fechaIngreso(LocalDate.of(2022, 1, 10))
                .semestreActual(1)
                .estado("Activo")
                .ci("12345678")
                .build();

        repository.save(estudiante);

        Boolean existe = repository.existsByCi("12345678");
        assertThat(existe).isTrue();
    }

    @Test
    void existsByCi_debeRetornarFalseSiNoExiste() {
        Boolean existe = repository.existsByCi("99999999");
        assertThat(existe).isFalse();
    }
}