package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.PartidoDTO;
import com.eleccioneselectronicas.model.Partido;
import com.eleccioneselectronicas.service.PartidoService;
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

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(consumes = { "multipart/form-data" })
    public Partido crearPartido(
            @RequestPart("partido") String partidoJson,
            @RequestPart("logo") MultipartFile logo) throws IOException {

        PartidoDTO dto = objectMapper.readValue(partidoJson, PartidoDTO.class);

        if (logo.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("El logo no puede superar los 5MB");
        }

        String contentType = logo.getContentType();
        if (!("image/png".equals(contentType) || "image/jpeg".equals(contentType) || "image/jpg".equals(contentType))) {
            throw new IllegalArgumentException("Solo se permiten imágenes PNG o JPG/JPEG");
        }

        String uploadDir = "uploads/partidos_logo/";
        String fileName = dto.getNombre() + "_" + logo.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(logo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        dto.setLogoUrl(filePath.toString());

        return partidoService.crearPartido(dto);
    }

    @GetMapping
    public List<Partido> listarPartidos() {
        return partidoService.listarPartidos();
    }

    @PutMapping("/{id}")
    public Partido actualizarPartido(@PathVariable Long id, @RequestBody PartidoDTO dto) {
        return partidoService.actualizarPartido(id, dto);
    }

    @PutMapping(value = "/actualizar_logo/{id}", consumes = { "multipart/form-data" })
    public Partido actualizarLogo(
            @PathVariable Long id,
            @RequestPart("logo") MultipartFile logo) throws IOException {

        if (logo.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("El logo no puede superar los 5MB");
        }

        String contentType = logo.getContentType();
        if (!("image/png".equals(contentType) || "image/jpeg".equals(contentType) || "image/jpg".equals(contentType))) {
            throw new IllegalArgumentException("Solo se permiten imágenes PNG o JPG/JPEG");
        }

        String uploadDir = "uploads/partidos_logo/actualizados/";
        String fileName = "Partido_ID" + "_" + id + "_" + logo.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(logo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return partidoService.actualizarLogo(id, filePath.toString());
    }

    @DeleteMapping("/{id}")
    public void eliminarPartido(@PathVariable Long id) {
        partidoService.eliminarPartido(id);
    }
}
