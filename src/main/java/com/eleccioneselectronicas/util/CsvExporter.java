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

    public static void exportarEstudiantes(List<Persona> estudiantes, HttpServletResponse response, String nombreArchivo) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".csv\"");
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()));
        writer.writeNext(new String[]{"ID", "Nombre", "Apellido", "Carrera"});
        for (Persona e : estudiantes) {
            writer.writeNext(new String[]{
                    String.valueOf(e.getCi()),
                    e.getNombre(),
                    e.getApellido_paterno(),
            });
        }
        writer.close();
    }

    public static void exportarDocentes(List<Persona> docentes, HttpServletResponse response, String nombreArchivo) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".csv\"");
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()));
        writer.writeNext(new String[]{"ID", "Nombre", "Apellido", "Facultad"});
        for (Persona d : docentes) {
            writer.writeNext(new String[]{
                    String.valueOf(d.getCi()),
                    d.getNombre(),
                    d.getApellido_paterno(),

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
                    String.valueOf(p.getIdPersona()),
                    p.getNombre(),
                    p.getApellido_paterno(),
                    p.getCi()
            });
        }
        writer.close();
    }
}
