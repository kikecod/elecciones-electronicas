package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.CandidatoDTO;
import com.eleccioneselectronicas.service.CandidatoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<CandidatoDTO> listar() {
        return candidatoService.listar();
    }

    @GetMapping("/{id}")
    public CandidatoDTO obtenerPorId(@PathVariable Long id) {
        return candidatoService.obtenerPorId(id);
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public CandidatoDTO registrar(
            @RequestPart("candidato") String candidatoJson,
            @RequestPart("planPropuestaFile") MultipartFile planPropuestaFile) throws IOException {

        CandidatoDTO dto = objectMapper.readValue(candidatoJson, CandidatoDTO.class);

        String uploadDir = "uploads/planes/";
        String fileName = UUID.randomUUID() + "_" + planPropuestaFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(planPropuestaFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        dto.setPlanPropuesta(filePath.toString());
        return candidatoService.registrar(dto);
    }

    @PutMapping(value = "/actualizar_Propuesta/{id}",consumes = { "multipart/form-data" })
    public CandidatoDTO actualziarPropuesta(
            @PathVariable Long id,
            @RequestPart("planPropuestaFile") MultipartFile planPropuestaFile) throws IOException {

        String uploadDir = "uploads/planes/actualizados/";
        String fileName = UUID.randomUUID() + "_" + planPropuestaFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(planPropuestaFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return candidatoService.actualizarPropuesta(id, filePath.toString());
    }

    @PutMapping("/{id}")
    public CandidatoDTO actualizar(@PathVariable Long id, @RequestBody CandidatoDTO dto) {
        return candidatoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        candidatoService.eliminar(id);
    }

    @GetMapping("/buscar/{cargoPostulado}")
    public List<CandidatoDTO> buscarPorCargo(@PathVariable String cargoPostulado) {
        return candidatoService.buscarPorCargoPostulado(cargoPostulado);
    }
}
