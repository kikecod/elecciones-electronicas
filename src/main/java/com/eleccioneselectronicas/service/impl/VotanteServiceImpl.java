package com.eleccioneselectronicas.service.impl;

import com.eleccioneselectronicas.dto.VotanteDTO;
import com.eleccioneselectronicas.model.Persona;
import com.eleccioneselectronicas.model.Votante;
import com.eleccioneselectronicas.repository.EleccionRepository;
import com.eleccioneselectronicas.repository.PersonaRepository;
import com.eleccioneselectronicas.repository.RecintoRepository;
import com.eleccioneselectronicas.repository.VotanteRepository;
import com.eleccioneselectronicas.service.VotanteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class VotanteServiceImpl implements VotanteService {

    @Autowired
    private VotanteRepository repository;

    @Autowired
    private PersonaRepository personaRepo;

    @Autowired
    private EleccionRepository eleccionRepo;

    @Autowired
    private RecintoRepository recintoRepo;

    private static final String IMAGES_DIRECTORY = "/Users/enriquefernandez/Documents/5tosemestre/web backend/proyecto/votantes/";


    @Override
    public List<VotanteDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public VotanteDTO obtenerPorId(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Votante no encontrado"));
    }
    @Override
    public VotanteDTO crear(VotanteDTO dto) {
        Votante votante = new Votante();

        votante.setPersona(personaRepo.findById(dto.getIdPersona())
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada")));

        votante.setEleccion(eleccionRepo.findById(dto.getIdEleccion())
                .orElseThrow(() -> new EntityNotFoundException("Elección no encontrada")));

        votante.setRecinto(recintoRepo.findById(dto.getIdRecinto())
                .orElseThrow(() -> new EntityNotFoundException("Recinto no encontrado")));

        votante.setQrUuid(UUID.randomUUID().toString());

        // Guardar la imagen en la carpeta y obtener la ruta
        String imagePath = saveImage(dto.getHashRostro());
        votante.setHashRostro(imagePath);  // Almacenar la ruta de la imagen

        votante.setHashHuella(null);  // Desactivado
        votante.setHashFirma(null);   // Desactivado

        votante.setHabilitado(true);
        votante.setFechaHabilitacion(LocalDateTime.now());

        Votante guardado = repository.save(votante);
        return toDto(guardado);
    }

    @Override
    public VotanteDTO registrarConImagen(VotanteDTO dto) {
        Votante votante = new Votante();

        votante.setPersona(personaRepo.findById(dto.getIdPersona())
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada")));

        votante.setEleccion(eleccionRepo.findById(dto.getIdEleccion())
                .orElseThrow(() -> new EntityNotFoundException("Elección no encontrada")));

        votante.setRecinto(recintoRepo.findById(dto.getIdRecinto())
                .orElseThrow(() -> new EntityNotFoundException("Recinto no encontrado")));

        votante.setQrUuid(UUID.randomUUID().toString());

        // Guardar la imagen en la carpeta y obtener la ruta
        String imagePath = saveImage(dto.getHashRostro());
        votante.setHashRostro(imagePath);  // Almacenar la ruta de la imagen

        votante.setHashHuella(null);  // Desactivado
        votante.setHashFirma(null);   // Desactivado

        votante.setHabilitado(true);
        votante.setFechaHabilitacion(LocalDateTime.now());

        Votante guardado = repository.save(votante);
        return toDto(guardado);
    }
    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
    @Override
    public VotanteDTO actualizar(Long id, VotanteDTO dto) {
        Votante votante = repository.findById(id).orElseThrow();
        votante.setQrUuid(dto.getQrUuid());
        votante.setHashHuella(dto.getHashHuella());
        votante.setHashFirma(dto.getHashFirma());
        votante.setHashRostro(dto.getHashRostro());
        votante.setHabilitado(dto.getHabilitado());
        votante.setFechaHabilitacion(dto.getFechaHabilitacion());
        return toDto(repository.save(votante));
    }

    // Función para guardar la imagen en una carpeta y devolver la ruta
    // Función para guardar la imagen en una carpeta y devolver la ruta
    private String saveImage(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            throw new IllegalArgumentException("Imagen base64 no puede ser nula o vacía");
        }

        // Obtener los bytes de la imagen desde la cadena base64
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Generar un nombre único para la imagen
        String imageName = UUID.randomUUID().toString() + ".jpg";
        Path imagePath = Paths.get(IMAGES_DIRECTORY, imageName);

        // Verificar si la carpeta existe antes de intentar crearla
        try {
            File dir = new File(IMAGES_DIRECTORY);
            if (!dir.exists()) {
                System.out.println("Creando la carpeta de imágenes: " + IMAGES_DIRECTORY);
                Files.createDirectories(Paths.get(IMAGES_DIRECTORY));  // Crear la carpeta si no existe
            } else {
                System.out.println("La carpeta ya existe: " + IMAGES_DIRECTORY);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear la carpeta de imágenes", e);
        }

        // Verificar la ruta completa para la imagen
        System.out.println("Guardando la imagen en: " + imagePath.toString());

        // Guardar la imagen como un archivo en el servidor
        try (FileOutputStream fos = new FileOutputStream(imagePath.toFile())) {
            fos.write(imageBytes);
            System.out.println("Imagen guardada correctamente en: " + imagePath.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }

        // Devolver la ruta de la imagen guardada
        return imagePath.toString();
    }
    @Override
    public VotanteDTO buscarPorCI(String ci) {
        Persona persona = personaRepo.findByCi(ci).stream().findFirst().orElseThrow();
        Votante votante = repository.findAll().stream()
                .filter(v -> v.getPersona().getIdPersona().equals(persona.getIdPersona()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Votante no encontrado"));
        return toDto(votante);
    }

    private VotanteDTO toDto(Votante v) {
        VotanteDTO dto = new VotanteDTO();
        dto.setIdVotante(v.getIdVotante());
        dto.setIdPersona(v.getPersona().getIdPersona());
        dto.setIdEleccion(v.getEleccion().getId());
        dto.setIdRecinto(v.getRecinto().getIdRecinto());
        dto.setQrUuid(v.getQrUuid());
        dto.setHashHuella(v.getHashHuella());
        dto.setHashFirma(v.getHashFirma());
        dto.setHashRostro(v.getHashRostro());  // Aquí devolveremos la ruta
        dto.setHabilitado(v.getHabilitado());
        dto.setFechaHabilitacion(v.getFechaHabilitacion());
        return dto;
    }
}
