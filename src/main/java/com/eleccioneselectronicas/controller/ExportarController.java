package com.eleccioneselectronicas.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eleccioneselectronicas.service.ExportarService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/exportar")
public class ExportarController {

    @Autowired
    private ExportarService exportarService;

    @GetMapping("/estudiantes")
    public void exportarEstudiantes(@RequestParam("tipo") String tipo,
                                    @RequestParam("carrera") Long carreraId,
                                    HttpServletResponse response) throws IOException, DocumentException {
        exportarService.exportarEstudiantesPorCarrera(carreraId, tipo, response);
    }

    @GetMapping("/docentes")
    public void exportarDocentes(@RequestParam("tipo") String tipo,
                                 @RequestParam("facultad") Long facultadId,
                                 HttpServletResponse response) throws IOException, DocumentException {
        exportarService.exportarDocentesPorFacultad(facultadId, tipo, response);
    }

    @GetMapping("/personas")
    public void exportarPersonas(@RequestParam("tipo") String tipo,
                                 HttpServletResponse response) throws IOException, DocumentException {
        exportarService.exportarPersonas(tipo, response);
    }
}
