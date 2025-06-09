package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.CarnetSufragioDTO;
import com.eleccioneselectronicas.model.Dispositivo;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.model.Recinto;
import com.eleccioneselectronicas.model.Votante;
import com.eleccioneselectronicas.repository.DispositivoRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.repository.RecintoRepository;
import com.eleccioneselectronicas.repository.VotanteRepository;
import com.eleccioneselectronicas.service.CarnetSufragioService;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CarnetSufragioServiceImpl implements CarnetSufragioService {

    @Autowired
    private VotanteRepository votanteRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private RecintoRepository recintoRepository;
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Override
    public byte[] generarPdfCarnet(Long idVotante) {
        // 1. Obtener los datos del votante, persona, recinto y dispositivo
        Votante votante = votanteRepository.findById(idVotante).orElseThrow(() -> new RuntimeException("Votante no encontrado"));
        Persona persona = personaRepository.findById(votante.getPersona().getIdPersona()).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        Recinto recinto = recintoRepository.findById(votante.getRecinto().getIdRecinto()).orElseThrow(() -> new RuntimeException("Recinto no encontrado"));
        Dispositivo dispositivo = dispositivoRepository.findById(votante.getRecinto().getIdRecinto()).orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));

        // 2. Mapear la información a un DTO
        CarnetSufragioDTO dto = new CarnetSufragioDTO();
        dto.setNombreEleccion(votante.getEleccion().getNombre());  // Asumiendo que Votante tiene una relación con Elección
        dto.setFechaEmision(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        dto.setNombres(persona.getNombre());
        dto.setApellidos(persona.getApellido_paterno() + " " + persona.getApellido_materno());
        dto.setFechaNacimiento(persona.getFechaNacimiento().toString());
        dto.setEmail(persona.getEmail());
        dto.setRecinto(recinto.getNombre());
        dto.setDispositivo(dispositivo.getModelo());
        dto.setQrUuid(votante.getQrUuid());
        dto.setFoto(votante.getHashRostro());  // Suponiendo que la foto está almacenada de esta manera
        dto.setCi(persona.getCi());

        // 3. Generar el PDF con la información del DTO
        byte[] pdf = generarPdf(dto);

        return pdf;
    }

    private byte[] generarPdf(CarnetSufragioDTO dto) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Crear un documento PDF
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(byteArrayOutputStream));
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);

            // Establecer fuentes
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);

            // Título
            document.add(new Paragraph("CERTIFICADO DE SUFRAGIO")
                    .setFont(font)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // Imagen del logo (opcional, se puede reemplazar por el logo real)
            Image logo = new Image(ImageDataFactory.create(getClass().getClassLoader().getResource("static/logo.png").getPath())); // Cambia la ruta del logo
            logo.setWidth(100);
            logo.setHeight(50);
            document.add(logo);

            // Foto del votante (sustituir con la ruta de la foto real)
            Image photo = new Image(ImageDataFactory.create(dto.getFoto()));
            photo.setWidth(50);
            photo.setHeight(50);
            document.add(photo);

            // Información del votante
            document.add(new Paragraph("Nombres: " + dto.getNombres() + " " + dto.getApellidos()));
            document.add(new Paragraph("Fecha Nac.: " + dto.getFechaNacimiento()));
            document.add(new Paragraph("Recinto: " + dto.getRecinto()));
            document.add(new Paragraph("N° Mesa: " + dto.getDispositivo()));
            document.add(new Paragraph("CI: " + dto.getCi()));

            // QR
            BarcodeQRCode qrCode = new BarcodeQRCode(dto.getQrUuid());
            Image qrCodeImage = new Image(qrCode.createFormXObject(ColorConstants.BLACK, pdfDoc));
            qrCodeImage.setWidth(80);
            qrCodeImage.setHeight(80);
            document.add(qrCodeImage);

            // Cerrar el documento
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}