package com.eleccioneselectronicas.util;

import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.model.Persona;
import com.opencsv.CSVWriter;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CsvExporter {

    public static void exportarEstudiantes(List<Estudiante> estudiantes, HttpServletResponse response, String nombreArchivo) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".csv\"");
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()));
        writer.writeNext(new String[]{"ID", "Nombre", "Apellido", "Carrera"});
        for (Estudiante e : estudiantes) {
            writer.writeNext(new String[]{
                    String.valueOf(e.getId()),
                    e.getPersona().getNombre(),
                    e.getPersona().getApellido(),
                    e.getCarrera().getNombre()
            });
        }
        writer.close();
    }

    public static void exportarDocentes(List<Docente> docentes, HttpServletResponse response, String nombreArchivo) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".csv\"");
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()));
        writer.writeNext(new String[]{"ID", "Nombre", "Apellido", "Facultad"});
        for (Docente d : docentes) {
            writer.writeNext(new String[]{
                    String.valueOf(d.getId()),
                    d.getPersona().getNombre(),
                    d.getPersona().getApellido(),
                    d.getFacultad().getNombre()
            });
        }
        writer.close();
    }

    public static void exportarPersonas(List<Persona> personas, HttpServletResponse response, String nombreArchivo) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".csv\"");
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()));
        writer.writeNext(new String[]{"ID", "Nombre", "Apellido", "CI"});
        for (Persona p : personas) {
            writer.writeNext(new String[]{
                    String.valueOf(p.getId()),
                    p.getNombre(),
                    p.getApellido(),
                    p.getCi()
            });
        }
        writer.close();
    }
}
