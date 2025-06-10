package com.eleccioneselectronicas.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCarnetPorEmail(String destinatario, byte[] pdf, String nombreArchivo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject("Carnet de Sufragio - Sistema Electoral");
        helper.setText("Adjunto encontrarás tu carnet de sufragio en formato PDF.", false);
        helper.addAttachment(nombreArchivo, new ByteArrayResource(pdf));

        mailSender.send(mensaje);
    }
}