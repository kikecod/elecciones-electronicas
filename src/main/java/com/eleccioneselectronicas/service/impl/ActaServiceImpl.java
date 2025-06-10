package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.*;
import com.eleccioneselectronicas.model.*;
import com.eleccioneselectronicas.repository.*;
import com.eleccioneselectronicas.service.ActaService;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActaServiceImpl implements ActaService {

    @Autowired
    private RecintoRepository recintoRepository;
    @Autowired
    private EleccionRepository eleccionRepository;
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private EncargadoRepository encargadoRepository;
    @Autowired
    private CandidatoRepository candidatoRepository;

    @Override
    public byte[] generarActaRecintoPdf(Long idRecinto, Long idEleccion) {
        // Obtener los datos del recinto y la elección
        Recinto recinto = recintoRepository.findById(idRecinto)
                .orElseThrow(() -> new RuntimeException("Recinto no encontrado"));

        Eleccion eleccion = eleccionRepository.findById(idEleccion)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada"));

        // Crear DTO para el acta
        ActaRecintoDTO actaRecintoDTO = obtenerActaRecintoDTO(idRecinto, idEleccion);

        // Generar PDF
        try {
            return crearActaPdf(actaRecintoDTO);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error al generar el PDF del acta", e);
        }
    }

    private ActaRecintoDTO obtenerActaRecintoDTO(Long idRecinto, Long idEleccion) {
        // Obtener los dispositivos del recinto
        List<String> nombresDispositivos = obtenerNombresDispositivos(idRecinto);
        System.out.println(nombresDispositivos);

        // Obtener total de votos en ese recinto para esa elección
        String totalVotos = String.valueOf(obtenerTotalVotos(idRecinto, idEleccion));

        // Obtener los resultados por dispositivo y candidato
        List<ResultadosDispositivoDTO> resultadosDispositivos = obtenerResultadosDispositivos(idRecinto, idEleccion);

        // Obtener el total de votos por candidato
        List<TotalVotosCandidatosDTO> totalVotosCandidatos = obtenerTotalVotosCandidatos(idRecinto, idEleccion);

        // Obtener los encargados del recinto
        List<EncargadosRecintosDTO> encargadosRecintos = obtenerEncargadosRecintos(idRecinto);

        // Generar un código QR único
        String qrUnico = UUID.randomUUID().toString();

        Recinto recinto = recintoRepository.findById(idRecinto)
                .orElseThrow(() -> new RuntimeException("Recinto no encontrado"));

        Eleccion eleccion = eleccionRepository.findById(idEleccion)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada"));

        return ActaRecintoDTO.builder()
                .nombre("Acta de Escrutinio") // Default name for the acta
                .fechaGeneracion(java.time.LocalDate.now())
                .nombreEleccion(eleccion.getNombre())
                .nombreRecinto(recinto.getNombre())
                .codigoRecinto(recinto.getAula()) // Assuming 'aula' corresponds to the 'Código Recinto'
                .nombresDispositivos(nombresDispositivos)
                .totalVotos(totalVotos)
                .resultadosCandidatosDispositivos(resultadosDispositivos)
                .resumen("Resumen de Resultados del Recinto") // Default summary text
                .totalVotosCandidatos(totalVotosCandidatos)
                .actaRecintos(encargadosRecintos)
                .observaciones("") // This field can be completed by the user
                .QRUnico(qrUnico)
                .build();
    }

    private List<String> obtenerNombresDispositivos(Long idRecinto) {
        return dispositivoRepository.findNombresByRecintoId(idRecinto);
    }

    private int obtenerTotalVotos(Long idRecinto, Long idEleccion) {
        return votoRepository.countByRecinto_IdRecintoAndEleccion_Id(idRecinto, idEleccion);
    }

    private List<ResultadosDispositivoDTO> obtenerResultadosDispositivos(Long idRecinto, Long idEleccion) {
        List<Dispositivo> dispositivos = dispositivoRepository.findByRecinto_IdRecinto(idRecinto);
        List<ResultadosDispositivoDTO> resultados = new ArrayList<>();

        List<Candidato> allCandidates = candidatoRepository.findByEleccion_Id(idEleccion);

        for (Dispositivo dispositivo : dispositivos) {
            List<ResultadosCandidatoDTO> resultadosCandidatos = new ArrayList<>();
            for (Candidato candidato : allCandidates) {
                int votos = votoRepository.countByDispositivo_IdDispositivoAndPartido_IdPartido(
                        dispositivo.getIdDispositivo(), candidato.getPartido().getIdPartido());
                resultadosCandidatos.add(new ResultadosCandidatoDTO(candidato.getPartido().getNombre(), candidato.getPersona().getNombre(), votos));
            }
            resultados.add(new ResultadosDispositivoDTO(dispositivo.getModelo(), resultadosCandidatos));
        }
        return resultados;
    }

    private List<TotalVotosCandidatosDTO> obtenerTotalVotosCandidatos(Long idRecinto, Long idEleccion) {
        List<Candidato> candidatos = candidatoRepository.findByEleccion_Id(idEleccion);
        List<TotalVotosCandidatosDTO> totalVotos = new ArrayList<>();
        for (Candidato candidato : candidatos) {
            int votos = votoRepository.countByRecinto_IdRecintoAndPartido_IdPartido(idRecinto, candidato.getPartido().getIdPartido());
            totalVotos.add(new TotalVotosCandidatosDTO(candidato.getPartido().getNombre(), candidato.getPersona().getNombre(), votos));
        }
        return totalVotos;
    }

    private List<EncargadosRecintosDTO> obtenerEncargadosRecintos(Long idRecinto) {
        List<Encargado> encargados = encargadoRepository.findByRecinto_IdRecinto(idRecinto);
        List<EncargadosRecintosDTO> encargadosDTO = new ArrayList<>();
        for (Encargado encargado : encargados) {
            encargadosDTO.add(new EncargadosRecintosDTO(encargado.getPersona().getNombre(), encargado.getPersona().getCi(), encargado.getRol()));
        }
        return encargadosDTO;
    }

    private byte[] crearActaPdf(ActaRecintoDTO dto) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();

        // Fonts
        Font fontTitle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        Font fontSubtitle = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        Font fontSmall = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
        Font fontBoldSmall = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

        // --- Header Section ---
        Paragraph title = new Paragraph(dto.getNombre(), fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        Paragraph electionInfo = new Paragraph("Elección: " + dto.getNombreEleccion(), fontSubtitle);
        electionInfo.setAlignment(Element.ALIGN_CENTER);
        electionInfo.setSpacingAfter(10);
        document.add(electionInfo);

        // --- Recinto Information ---
        document.add(new Paragraph("Recinto: " + dto.getNombreRecinto(), fontNormal));
        document.add(new Paragraph("Código de Recinto: " + dto.getCodigoRecinto(), fontNormal));
        document.add(new Paragraph("Fecha de Generación: " + dto.getFechaGeneracion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(new Paragraph("Hora de Generación: " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), fontNormal));
        document.add(new Paragraph("Total de Votos Registrados: " + dto.getTotalVotos(), fontBoldSmall));
        document.add(Chunk.NEWLINE);

        // --- Votantes por Dispositivos de Escrutinio (Table 1) ---
        document.add(new Paragraph("Votos por Dispositivo y Candidato:", fontSubtitle));
        document.add(Chunk.NEWLINE);

        // Define column widths for Table 1
        // NRO + PARTIDO + CANDIDATO + (Number of Dispositivos)
        // Adjust these float values to control column width distribution
        float[] columnWidthsTable1 = new float[3 + dto.getNombresDispositivos().size()];
        columnWidthsTable1[0] = 0.5f; // NRO
        columnWidthsTable1[1] = 1.5f; // PARTIDO
        columnWidthsTable1[2] = 2f;   // CANDIDATO
        for (int i = 0; i < dto.getNombresDispositivos().size(); i++) {
            columnWidthsTable1[3 + i] = 1f; // Each device column
        }

        PdfPTable table1 = new PdfPTable(columnWidthsTable1);
        table1.setWidthPercentage(100);
        table1.setSpacingBefore(5f);
        table1.setSpacingAfter(15f);

        // Table 1 Headers
        table1.addCell(createHeaderCell("NRO", fontBoldSmall));
        table1.addCell(createHeaderCell("PARTIDO", fontBoldSmall));
        table1.addCell(createHeaderCell("CANDIDATO", fontBoldSmall));
        for (String dispositivoName : dto.getNombresDispositivos()) {
            table1.addCell(createHeaderCell(dispositivoName, fontBoldSmall));
        }

        // Table 1 Data: Get a unique list of candidates for rows
        List<ResultadosCandidatoDTO> uniqueCandidates = dto.getResultadosCandidatosDispositivos().stream()
                .flatMap(rd -> rd.getResultadosCandidatos().stream())
                .collect(Collectors.toMap(
                        rc -> rc.getNombrePartido() + rc.getNombreCandidato(), // Unique key for candidate
                        rc -> rc,
                        (existing, replacement) -> existing // Keep the existing one if duplicate
                ))
                .values()
                .stream()
                .sorted((c1, c2) -> c1.getNombreCandidato().compareTo(c2.getNombreCandidato())) // Sort for consistency
                .collect(Collectors.toList());

        int candidateCounter = 1;
        for (ResultadosCandidatoDTO candidate : uniqueCandidates) {
            table1.addCell(createContentCell(String.valueOf(candidateCounter++), fontSmall));
            table1.addCell(createContentCell(candidate.getNombrePartido(), fontSmall));
            table1.addCell(createContentCell(candidate.getNombreCandidato(), fontSmall));

            // Populate votes for each device for this specific candidate
            for (String deviceName : dto.getNombresDispositivos()) {
                int votesForThisDeviceAndCandidate = 0;
                // Find the device result for the current deviceName
                ResultadosDispositivoDTO currentDeviceResult = dto.getResultadosCandidatosDispositivos().stream()
                        .filter(rd -> rd.getNombreDispositivo().equals(deviceName))
                        .findFirst()
                        .orElse(null);

                if (currentDeviceResult != null) {
                    // Find the candidate's votes within this device's results
                    votesForThisDeviceAndCandidate = currentDeviceResult.getResultadosCandidatos().stream()
                            .filter(rc -> rc.getNombrePartido().equals(candidate.getNombrePartido()) &&
                                    rc.getNombreCandidato().equals(candidate.getNombreCandidato()))
                            .mapToInt(ResultadosCandidatoDTO::getVotos)
                            .sum();
                }
                table1.addCell(createContentCell(String.valueOf(votesForThisDeviceAndCandidate), fontSmall));
            }
        }
        document.add(table1);

        // --- Total Votos por Candidato (Table 2) ---
        document.add(new Paragraph(dto.getResumen(), fontSubtitle));
        document.add(Chunk.NEWLINE);

        PdfPTable table2 = new PdfPTable(3);
        table2.setWidthPercentage(60); // Smaller table for totals
        table2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.setSpacingBefore(5f);
        table2.setSpacingAfter(15f);

        // Table 2 Headers
        table2.addCell(createHeaderCell("PARTIDO", fontBoldSmall));
        table2.addCell(createHeaderCell("CANDIDATO", fontBoldSmall));
        table2.addCell(createHeaderCell("VOTOS", fontBoldSmall));

        // Table 2 Data
        for (TotalVotosCandidatosDTO totalVotosCand : dto.getTotalVotosCandidatos()) {
            table2.addCell(createContentCell(totalVotosCand.getNombrePartido(), fontSmall));
            table2.addCell(createContentCell(totalVotosCand.getNombreCandidato(), fontSmall));
            table2.addCell(createContentCell(String.valueOf(totalVotosCand.getTotalVotos()), fontSmall, Element.ALIGN_CENTER));
        }
        document.add(table2);

        // --- Observations ---
        document.add(new Paragraph("Observaciones:", fontSubtitle));
        document.add(new Paragraph(dto.getObservaciones() != null && !dto.getObservaciones().isEmpty() ? dto.getObservaciones() : "Ninguna observación.", fontNormal));
        document.add(Chunk.NEWLINE);

        // --- Encargados (Signatures) ---
        document.add(new Paragraph("Firmas de Encargados:", fontSubtitle));
        document.add(Chunk.NEWLINE);

        PdfPTable signaturesTable = new PdfPTable(2); // Two columns for Name/CI and Signature
        signaturesTable.setWidthPercentage(80);
        signaturesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        signaturesTable.setSpacingBefore(10f);
        signaturesTable.setSpacingAfter(20f);

        for (EncargadosRecintosDTO encargado : dto.getActaRecintos()) {
            PdfPCell nameCiCell = new PdfPCell();
            nameCiCell.addElement(new Paragraph(encargado.getRol() + ": " + encargado.getNombre(), fontNormal));
            nameCiCell.addElement(new Paragraph("CI: " + encargado.getCi(), fontSmall));
            nameCiCell.setBorder(Rectangle.NO_BORDER);
            nameCiCell.setPadding(5);
            signaturesTable.addCell(nameCiCell);

            PdfPCell signatureCell = new PdfPCell(new Phrase("Firma: ________________________", fontNormal));
            signatureCell.setBorder(Rectangle.NO_BORDER);
            signatureCell.setPadding(5);
            signatureCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            signaturesTable.addCell(signatureCell);
        }
        document.add(signaturesTable);

        // --- QR Code ---
        document.add(new Paragraph("Código de Verificación:", fontSubtitle));
        try {
            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(dto.getQRUnico(), 100, 100, null);
            Image qrImage = barcodeQRCode.getImage();
            qrImage.setAlignment(Element.ALIGN_CENTER);
            document.add(qrImage);
        } catch (BadElementException e) {
            System.err.println("Error generating QR code: " + e.getMessage());
        }

        document.close();
        return baos.toByteArray();
    }

    // Helper method to create a header cell
    private PdfPCell createHeaderCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY); // A light grey background for headers
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(BaseColor.DARK_GRAY);
        return cell;
    }

    // Helper method to create a content cell (left-aligned by default)
    private PdfPCell createContentCell(String content, Font font) {
        return createContentCell(content, font, Element.ALIGN_LEFT);
    }

    // Helper method to create a content cell with specified alignment
    private PdfPCell createContentCell(String content, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderWidth(0.2f);
        cell.setBorderColor(BaseColor.GRAY);
        return cell;
    }
}