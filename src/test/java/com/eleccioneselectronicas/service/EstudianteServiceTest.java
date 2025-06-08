package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.repository.EstudianteRepository;
import com.eleccioneselectronicas.service.impl.EstudianteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceImplTest {

    @Mock
    private EstudianteRepository repo;

    @InjectMocks
    private EstudianteServiceImpl service;

    @Test
    void obtenerEstudiantePorId_devuelveDTO() {
        Estudiante estudiante = Estudiante.builder()
                .idPersona(1L)
                .nombre("Juan")
                .matricula("A123")
                .fechaIngreso(LocalDate.of(2022, 1, 10))
                .semestreActual(3)
                .estado("Activo")
                .build();

        when(repo.findById(1L)).thenReturn(Optional.of(estudiante));

        EstudianteDTO dto = service.obtenerEstudiantePorId(1L);

        assertThat(dto.getNombre()).isEqualTo("Juan");
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMatricula()).isEqualTo("A123");
    }
}

