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
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;

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

    @Autowired
    private JavaMailSender mailSender;


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
            PdfFont boldFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

            // Título del Certificado
            document.add(new Paragraph("CERTIFICADO DE SUFRAGIO")
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10));
            document.add(new Paragraph("Elección: " + dto.getNombreEleccion()))
                    .setFont(font)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(new Paragraph("Fecha de Emisión: " + dto.getFechaEmision())
                    .setFont(font)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // Imagen del logo (a la izquierda)
            ClassPathResource logoResource = new ClassPathResource("static/logo.png");
            if (logoResource.exists()) {
                Image logo = new Image(ImageDataFactory.create(logoResource.getFile().getPath()));
                logo.setWidth(20);  // Ajustar el tamaño del logo
                logo.setHeight(40);
                document.add(logo);
            }

            // Foto del votante (a la derecha del logo)
            String fotoPath = dto.getFoto();  // Ruta obtenida desde hashRostro
            if (fotoPath != null && !fotoPath.isEmpty()) {
                FileSystemResource fileResource = new FileSystemResource(fotoPath);
                if (fileResource.exists()) {
                    Image photo = new Image(ImageDataFactory.create(fileResource.getFile().getPath()));
                    photo.setWidth(100);
                    photo.setHeight(100);
                    photo.setFixedPosition(450, 550);  // Ubicamos la foto a la derecha en el diseño
                    document.add(photo);
                }
            }

            // Información del votante
            document.add(new Paragraph("Nombres: " + dto.getNombres() + " " + dto.getApellidos())
                    .setFont(font)
                    .setFontSize(12));
            document.add(new Paragraph("Fecha Nac.: " + dto.getFechaNacimiento())
                    .setFont(font)
                    .setFontSize(12));
            document.add(new Paragraph("Recinto: " + dto.getRecinto())
                    .setFont(font)
                    .setFontSize(12));
            document.add(new Paragraph("N° Mesa: " + dto.getDispositivo())
                    .setFont(font)
                    .setFontSize(12));
            document.add(new Paragraph("CI: " + dto.getCi())
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(15));

            // QR
            BarcodeQRCode qrCode = new BarcodeQRCode(dto.getQrUuid());
            Image qrCodeImage = new Image(qrCode.createFormXObject(ColorConstants.BLACK, pdfDoc));
            qrCodeImage.setWidth(100);
            qrCodeImage.setHeight(100);
            qrCodeImage.setFixedPosition(450, 250);  // Ubicamos el QR en la parte inferior derecha
            document.add(qrCodeImage);

            // Cerrar el documento
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void enviarCarnetPorEmail(Long idVotante) {
        Votante votante = votanteRepository.findById(idVotante)
                .orElseThrow(() -> new RuntimeException("Votante no encontrado"));

        Persona persona = personaRepository.findById(votante.getPersona().getIdPersona())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        byte[] pdfBytes = generarPdfCarnet(idVotante);
        if (pdfBytes == null) throw new RuntimeException("No se pudo generar el carnet PDF");

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

            helper.setTo(persona.getEmail());
            helper.setSubject("Tu Carnet de Sufragio");
            helper.setText("Estimado/a " + persona.getNombre() + ",\n\nAdjunto encontrarás tu carnet de sufragio en formato PDF.\n\nAtentamente,\nTribunal Electoral");

            helper.addAttachment("CarnetSufragio.pdf", new ByteArrayDataSource(pdfBytes, "application/pdf"));

            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage());
        }
    }
}