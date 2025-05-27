package com.eleccioneselectronicas.serviceImpl;

import com.eleccioneselectronicas.dto.DispositivoDTO;
import com.eleccioneselectronicas.model.Dispositivo;
import com.eleccioneselectronicas.model.Recinto;
import com.eleccioneselectronicas.repository.DispositivoRepository;
import com.eleccioneselectronicas.repository.RecintoRepository;
import com.eleccioneselectronicas.service.DispositivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DispositivoServiceImpl implements DispositivoService {

    private final DispositivoRepository dispositivoRepository;
    private final RecintoRepository recintoRepository;

    @Override
    public DispositivoDTO crear(DispositivoDTO dto) {
        Recinto recinto = recintoRepository.findById(dto.getIdRecinto())
        .orElseThrow(() -> new RuntimeException("Recinto no encontrado"));
        Dispositivo dispositivo = toEntity(dto);
        dispositivo.setRecinto(recinto);
        return toDTO(dispositivoRepository.save(dispositivo));
    }

    @Override
    public DispositivoDTO obtenerPorId(Long id) {
        return dispositivoRepository.findById(id).map(this::toDTO).orElseThrow();
    }

    @Override
    public List<DispositivoDTO> listarTodos() {
        return dispositivoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DispositivoDTO actualizar(Long id, DispositivoDTO dto) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElseThrow();
        dispositivo.setSerie(dto.getSerie());
        dispositivo.setModelo(dto.getModelo());
        dispositivo.setClavePublica(dto.getClavePublica());
        dispositivo.setUltimaRevision(dto.getUltimaRevision());
        dispositivo.setEstado(dto.getEstado());

        if (!dispositivo.getRecinto().getIdRecinto().equals(dto.getIdRecinto())) {
            Recinto recinto = recintoRepository.findById(dto.getIdRecinto()).orElseThrow();
            dispositivo.setRecinto(recinto);
        }

        return toDTO(dispositivoRepository.save(dispositivo));
    }

    @Override
    public void eliminar(Long id) {
        dispositivoRepository.deleteById(id);
    }

    private DispositivoDTO toDTO(Dispositivo dispositivo) {
        return DispositivoDTO.builder()
                .id(dispositivo.getIdDispositivo())
                .serie(dispositivo.getSerie())
                .modelo(dispositivo.getModelo())
                .idRecinto(dispositivo.getRecinto().getIdRecinto())
                .clavePublica(dispositivo.getClavePublica())
                .ultimaRevision(dispositivo.getUltimaRevision())
                .estado(dispositivo.getEstado())
                .build();
    }

    private Dispositivo toEntity(DispositivoDTO dto) {
        return Dispositivo.builder()
                .idDispositivo(dto.getId())
                .serie(dto.getSerie())
                .modelo(dto.getModelo())
                .clavePublica(dto.getClavePublica())
                .ultimaRevision(dto.getUltimaRevision())
                .estado(dto.getEstado())
                .build();
    }
}
