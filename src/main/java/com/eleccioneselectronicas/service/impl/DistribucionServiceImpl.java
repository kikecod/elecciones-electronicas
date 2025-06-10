package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.DistribucionRecintoDTO;
import com.eleccioneselectronicas.model.AsignacionVotante;
import com.eleccioneselectronicas.repository.AsignacionVotanteRepository;
import com.eleccioneselectronicas.service.DistribucionService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistribucionServiceImpl implements DistribucionService {

    @Autowired
    private AsignacionVotanteRepository asignacionVotanteRepository;

    @Override
    public byte[] generarDistribucionPdf(Long idEleccion) {
        // Obtener los datos de distribución de votantes para la elección
        List<AsignacionVotante> asignaciones = asignacionVotanteRepository.findByVotante_Eleccion_Id(idEleccion);

        // Mapear las asignaciones a DTOs
        List<DistribucionRecintoDTO> distribucionRecintoDTOs = asignaciones.stream()
                .map(asignacion -> new DistribucionRecintoDTO(
                        asignacion.getVotante().getPersona().getCi(),
                        asignacion.getVotante().getPersona().getNombre(),
                        asignacion.getVotante().getPersona().getApellido_paterno() + " " + asignacion.getVotante().getPersona().getApellido_materno(),
                        asignacion.getRecinto().getNombre(),
                        asignacion.getRecinto().getCampus(),
                        asignacion.getRecinto().getEdificio(),
                        asignacion.getRecinto().getAula(),
                        asignacion.getDispositivo().getModelo()
                ))
                .collect(Collectors.toList());

        // Generar PDF
        try {
            return crearDistribucionPdf(distribucionRecintoDTOs);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error al generar el PDF de distribución", e);
        }
    }

    private byte[] crearDistribucionPdf(List<DistribucionRecintoDTO> distribucionRecintoDTOs) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();

        // Fuentes
        Font fontTitle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        Font fontBoldSmall = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

        // Título
        Paragraph title = new Paragraph("Distribución de Votantes por Recinto y Dispositivo", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Crear la tabla con las columnas definidas
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        // Encabezados de las columnas
        table.addCell(createHeaderCell("CI", fontBoldSmall));
        table.addCell(createHeaderCell("Nombre", fontBoldSmall));
        table.addCell(createHeaderCell("Apellidos", fontBoldSmall));
        table.addCell(createHeaderCell("Recinto", fontBoldSmall));
        table.addCell(createHeaderCell("Campus", fontBoldSmall));
        table.addCell(createHeaderCell("Edificio", fontBoldSmall));
        table.addCell(createHeaderCell("Aula", fontBoldSmall));
        table.addCell(createHeaderCell("Modelo Dispositivo", fontBoldSmall));

        // Añadir los datos a la tabla
        for (DistribucionRecintoDTO dto : distribucionRecintoDTOs) {
            table.addCell(createContentCell(dto.getCi(), fontNormal));
            table.addCell(createContentCell(dto.getNombrePersona(), fontNormal));
            table.addCell(createContentCell(dto.getApellidoPersona(), fontNormal));
            table.addCell(createContentCell(dto.getNombreRecinto(), fontNormal));
            table.addCell(createContentCell(dto.getCampus(), fontNormal));
            table.addCell(createContentCell(dto.getEdificio(), fontNormal));
            table.addCell(createContentCell(dto.getAula(), fontNormal));
            table.addCell(createContentCell(dto.getModelodispositivo(), fontNormal));
        }

        // Añadir la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();
        return baos.toByteArray();
    }

    // Helper method to create header cells for the table
    private PdfPCell createHeaderCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        return cell;
    }

    // Helper method to create content cells
    private PdfPCell createContentCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }
}