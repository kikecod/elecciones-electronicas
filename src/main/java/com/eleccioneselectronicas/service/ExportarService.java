package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.repository.DocenteRepository;
import com.eleccioneselectronicas.repository.EstudianteRepository;
import com.eleccioneselectronicas.repository.PersonaCarreraRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.util.CsvExporter;
import com.eleccioneselectronicas.util.PdfExporter;
import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExportarService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private PersonaCarreraRepository personaCarreraRepository;

    public void exportarEstudiantesPorCarrera(Long carreraId, String tipo, HttpServletResponse response) throws IOException, DocumentException {
        List<Persona> estudiantes = personaRepository.findEstudiantesByCarreraId(carreraId);

        String nombreArchivo = "estudiantes_carrera_" + carreraId;
        if ("csv".equalsIgnoreCase(tipo)) {
            CsvExporter.exportarEstudiantes(estudiantes, response, nombreArchivo);
        } else if ("pdf".equalsIgnoreCase(tipo)) {
            PdfExporter.exportarEstudiantes(estudiantes, response, nombreArchivo);
        } else {
            throw new IllegalArgumentException("Tipo de exportación no soportado: " + tipo);
        }
    }

    public void exportarDocentesPorFacultad(Long facultadId, String tipo, HttpServletResponse response) throws IOException, DocumentException {
        List<Persona> docentes = personaRepository.findDocentesByFacultadId(facultadId);

        String nombreArchivo = "docentes_facultad_" + facultadId;
        if ("csv".equalsIgnoreCase(tipo)) {
            CsvExporter.exportarDocentes(docentes, response, nombreArchivo);
        } else if ("pdf".equalsIgnoreCase(tipo)) {
            PdfExporter.exportarDocentes(docentes, response, nombreArchivo);
        } else {
            throw new IllegalArgumentException("Tipo de exportación no soportado: " + tipo);
        }
    }

    public void exportarPersonas(String tipo, HttpServletResponse response) throws IOException, DocumentException {
        List<Persona> personas = personaRepository.findAll();
        String nombreArchivo = "personas";
        if ("csv".equalsIgnoreCase(tipo)) {
            CsvExporter.exportarPersonas(personas, response, nombreArchivo);
        } else if ("pdf".equalsIgnoreCase(tipo)) {
            PdfExporter.exportarPersonas(personas, response, nombreArchivo);
        } else {
            throw new IllegalArgumentException("Tipo de exportación no soportado: " + tipo);
        }
    }

}
