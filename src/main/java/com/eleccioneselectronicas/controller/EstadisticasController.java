package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.model.Docente;
import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.repository.VotanteRepository;
import com.eleccioneselectronicas.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    @Autowired
    private VotanteRepository votanteRepository;

    @Autowired
    private VotoRepository votoRepository;

    @GetMapping("/participacion/porcentaje")
    public ResponseEntity<Map<String, Object>> obtenerPorcentajeParticipacion() {
        long totalHabilitados = votanteRepository.count();
        long totalVotaron = votoRepository.count();

        double porcentaje = totalHabilitados == 0 ? 0 : (totalVotaron * 100.0) / totalHabilitados;

        Map<String, Object> response = new HashMap<>();
        response.put("total_habilitados", totalHabilitados);
        response.put("total_votaron", totalVotaron);
        response.put("porcentaje", Math.round(porcentaje * 10.0) / 10.0);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/votos/eleccion/{id}")
    public ResponseEntity<Long> obtenerTotalVotosPorEleccion(@PathVariable Long id) {
        long total = votoRepository.countByEleccionId(id);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/ganador/eleccion/{id}")
    public ResponseEntity<Map<String, Object>> obtenerGanador(@PathVariable Long id) {
        // Obtener votos por partido de estudiantes
        List<Object[]> votosEstudiantes = votoRepository.totalVotosPorPartidoEnEleccionYRol(id, Estudiante.class);
        // Obtener votos por partido de docentes
        List<Object[]> votosDocentes = votoRepository.totalVotosPorPartidoEnEleccionYRol(id, Docente.class);

        Map<String, Long> votosEst = new HashMap<>();
        Map<String, Long> votosDoc = new HashMap<>();

        long totalEst = 0;
        long totalDoc = 0;

        for (Object[] fila : votosEstudiantes) {
            String partido = (String) fila[0];
            Long cantidad = (Long) fila[1];
            votosEst.put(partido, cantidad);
            totalEst += cantidad;
        }

        for (Object[] fila : votosDocentes) {
            String partido = (String) fila[0];
            Long cantidad = (Long) fila[1];
            votosDoc.put(partido, cantidad);
            totalDoc += cantidad;
        }

        Map<String, Double> porcentajeTotalPorPartido = new HashMap<>();
        Set<String> todosLosPartidos = new HashSet<>();
        todosLosPartidos.addAll(votosEst.keySet());
        todosLosPartidos.addAll(votosDoc.keySet());

        for (String partido : todosLosPartidos) {
            double porcEst = totalEst > 0 ? (votosEst.getOrDefault(partido, 0L) * 100.0 / totalEst) : 0;
            double porcDoc = totalDoc > 0 ? (votosDoc.getOrDefault(partido, 0L) * 100.0 / totalDoc) : 0;
            double combinado = (porcEst + porcDoc) / 2.0;
            porcentajeTotalPorPartido.put(partido, combinado);
        }

        String partidoGanador = null;
        double maxPorcentaje = -1;
        long totalGanador = 0;

        for (String partido : porcentajeTotalPorPartido.keySet()) {
            double combinado = porcentajeTotalPorPartido.get(partido);
            if (combinado > maxPorcentaje) {
                maxPorcentaje = combinado;
                partidoGanador = partido;
                totalGanador = votosEst.getOrDefault(partido, 0L) + votosDoc.getOrDefault(partido, 0L);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("partido", partidoGanador);
        response.put("total_votos", totalGanador);
        response.put("porcentaje", Math.round(maxPorcentaje * 10.0) / 10.0);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/votos/partido")
    public ResponseEntity<List<Map<String, Object>>> obtenerVotosPorPartido() {
        List<Object[]> datos = votoRepository.totalVotosPorPartido();

        long totalVotos = votoRepository.count();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] fila : datos) {
            String partido = (String) fila[0];
            long votos = (long) fila[1];
            double porcentaje = totalVotos == 0 ? 0 : (votos * 100.0) / totalVotos;

            Map<String, Object> partidoData = new HashMap<>();
            partidoData.put("partido", partido);
            partidoData.put("votos", votos);
            partidoData.put("porcentaje", Math.round(porcentaje * 10.0) / 10.0);
            response.add(partidoData);
        }

        return ResponseEntity.ok(response);
    }
}
