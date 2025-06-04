package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.service.EstudianteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(EstudianteController.class)
class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstudianteService estudianteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearEstudiante_conTodosLosCampos_debeRetornarCreado() throws Exception {
        EstudianteDTO dto = new EstudianteDTO();
        // Datos de Persona
        dto.setNombre("Juan");
        dto.setApellido_paterno("Perez");
        dto.setApellido_materno("Gomez");
        dto.setEmail("juan.perez@gmail.com");
        dto.setDireccion("Calle Falsa 123");
        dto.setTelefono("123456789");
        dto.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        dto.setFechaAlta(LocalDate.of(2022, 1, 10));
        dto.setCi("12345678");
        // Datos de Estudiante
        dto.setMatricula("A12345");
        dto.setFechaIngreso(LocalDate.of(2022, 1, 10));
        dto.setSemestreActual(1);
        dto.setEstado("Activo");

        Mockito.when(estudianteService.crearEstudiante(any(EstudianteDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/estudiantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido_paterno").value("Perez"))
                .andExpect(jsonPath("$.apellido_materno").value("Gomez"))
                .andExpect(jsonPath("$.email").value("juan.perez@gmail.com"))
                .andExpect(jsonPath("$.direccion").value("Calle Falsa 123"))
                .andExpect(jsonPath("$.telefono").value("123456789"))
                .andExpect(jsonPath("$.fechaNacimiento").value("2000-01-01"))
                .andExpect(jsonPath("$.fechaAlta").value("2022-01-10"))
                .andExpect(jsonPath("$.ci").value("12345678"))
                .andExpect(jsonPath("$.matricula").value("A12345"))
                .andExpect(jsonPath("$.fechaIngreso").value("2022-01-10"))
                .andExpect(jsonPath("$.semestreActual").value(1))
                .andExpect(jsonPath("$.estado").value("Activo"));
    }
}