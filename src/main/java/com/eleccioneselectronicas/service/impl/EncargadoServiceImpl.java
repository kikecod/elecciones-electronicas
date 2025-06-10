package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.AsignacionEncargadosDTO;
import com.eleccioneselectronicas.dto.EncargadoInfoDTO;
import com.eleccioneselectronicas.model.Eleccion;
import com.eleccioneselectronicas.model.Encargado;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.model.Recinto;
import com.eleccioneselectronicas.repository.EleccionRepository;
import com.eleccioneselectronicas.repository.EncargadoRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.repository.RecintoRepository;
import com.eleccioneselectronicas.service.EncargadoService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class EncargadoServiceImpl implements EncargadoService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private RecintoRepository recintoRepository;

    @Autowired
    private EncargadoRepository encargadoRepository;

    @Autowired
    private EleccionRepository eleccionRepository;


@Override
public void asignarEncargadosARecinto(AsignacionEncargadosDTO dto) {
    Recinto recinto = recintoRepository.findById(dto.getRecintoId())
        .orElseThrow(() -> new RuntimeException("Recinto no encontrado"));

    Eleccion eleccion = eleccionRepository.findById(dto.getEleccionId())
        .orElseThrow(() -> new RuntimeException("Elección no encontrada"));

    Persona docente = personaRepository.findById(dto.getDocenteId())
        .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

    encargadoRepository.save(new Encargado(null, docente, recinto, "DOCENTE", eleccion));

    List<Long> estudiantes = dto.getEstudiantesIds();
    if (estudiantes.size() != 2) {
        throw new RuntimeException("Debe asignar exactamente 2 estudiantes");
    }

    for (Long estudianteId : estudiantes) {
        Persona estudiante = personaRepository.findById(estudianteId)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        encargadoRepository.save(new Encargado(null, estudiante, recinto, "ESTUDIANTE", eleccion));
    }
}


    @Override
    public EncargadoInfoDTO buscarEncargadoPorCi(String ci) {
        Persona persona = personaRepository.findByCi(ci)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada con CI: " + ci));

        Encargado encargado = encargadoRepository.findByPersona_IdPersona(persona.getIdPersona())
            .orElseThrow(() -> new RuntimeException("La persona no es encargada"));

        String nombreCompleto = persona.getNombre() + " " + persona.getApellido_paterno() + " " + persona.getApellido_materno();
        String nombreRecinto = encargado.getRecinto().getNombre();

        return new EncargadoInfoDTO(ci, nombreCompleto, nombreRecinto);
    }

@Override
public byte[] generarPdfEncargadosPorEleccion(Long idEleccion) {
    List<Encargado> encargados = encargadoRepository.findAllByEleccion_Id(idEleccion);
    Eleccion eleccion = eleccionRepository.findById(idEleccion)
        .orElseThrow(() -> new RuntimeException("Elección no encontrada"));

    try (PDDocument doc = new PDDocument()) {
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream stream = new PDPageContentStream(doc, page);
        PDFont font = PDType1Font.HELVETICA;
        float y = 750;

        stream.beginText();
        stream.setFont(font, 14);
        stream.newLineAtOffset(50, y);
        stream.showText("Encargados de la Elección: " + eleccion.getNombre());
        stream.newLineAtOffset(0, -20);

        for (Encargado e : encargados) {
            Persona p = e.getPersona();
            String linea = String.format("- %s %s %s | CI: %s | Rol: %s | Recinto: %s",
                p.getNombre(), p.getApellido_paterno(), p.getApellido_materno(), p.getCi(),
                e.getRol(), e.getRecinto().getNombre());

            stream.showText(linea);
            stream.newLineAtOffset(0, -18);
        }

        stream.endText();
        stream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        return baos.toByteArray();

    } catch (IOException ex) {
        throw new RuntimeException("Error al generar el PDF", ex);
    }
}


}
