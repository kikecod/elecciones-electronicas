package com.eleccioneselectronicas.util;
import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.model.Persona;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public class PdfExporter {
    
    public static void exportarEstudiantes(List<Persona> estudiantes, HttpServletResponse response, String nombreArchivo) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".pdf\"");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("Lista de Estudiantes", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
        escribirCabeceraEstudiantes(table);
        escribirDatosEstudiantes(table, estudiantes);
        document.add(table);
        document.close();
    }

    public static void exportarDocentes(List<Persona> docentes, HttpServletResponse response, String nombreArchivo) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".pdf\"");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("Lista de Docentes", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
        escribirCabeceraDocentes(table);
        escribirDatosDocentes(table, docentes);
        document.add(table);
        document.close();
    }

    public static void exportarPersonas(List<Persona> personas, HttpServletResponse response, String nombreArchivo) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + ".pdf\"");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("Lista de Personas", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
        escribirCabeceraPersonas(table);
        escribirDatosPersonas(table, personas);
        document.add(table);
        document.close();
    }

    private static void escribirCabeceraEstudiantes(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);
        
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Nombre", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Apellido", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Carrera", font));
        table.addCell(cell);
    }

    private static void escribirDatosEstudiantes(PdfPTable table, List<Persona> estudiantes) {
        for (Persona e : estudiantes) {
            table.addCell(String.valueOf(e.getCi()));
            table.addCell(e.getNombre());
            table.addCell(e.getApellido_paterno());
            table.addCell(e.getApellido_materno());
        }
    }

    private static void escribirCabeceraDocentes(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);
        
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Nombre", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Apellido", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Facultad", font));
        table.addCell(cell);
    }

    private static void escribirDatosDocentes(PdfPTable table, List<Persona> docentes) {
        for (Persona d : docentes) {
            table.addCell(String.valueOf(d.getCi()));
            table.addCell(d.getNombre());
            table.addCell(d.getApellido_paterno());
            table.addCell(d.getApellido_materno());
        }
    }

    private static void escribirCabeceraPersonas(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);
        
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Nombre", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Apellido", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("CI", font));
        table.addCell(cell);
    }

    private static void escribirDatosPersonas(PdfPTable table, List<Persona> personas) {
        for (Persona p : personas) {
            table.addCell(String.valueOf(p.getCi()));
            table.addCell(p.getNombre());
            table.addCell(p.getApellido_paterno());
            table.addCell(p.getCi());
        }
    }

    


}
