import com.eleccioneselectronicas.dto.*;
import com.eleccioneselectronicas.model.Candidato;
import com.eleccioneselectronicas.model.Dispositivo;
import com.eleccioneselectronicas.model.Eleccion;
import com.eleccioneselectronicas.model.Recinto;
import com.eleccioneselectronicas.repository.DispositivoRepository;
import com.eleccioneselectronicas.repository.EleccionRepository;
import com.eleccioneselectronicas.repository.RecintoRepository;
import com.eleccioneselectronicas.repository.VotoRepository;
import com.eleccioneselectronicas.service.ActaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        return crearActaPdf(actaRecintoDTO);
    }

    private ActaRecintoDTO obtenerActaRecintoDTO(Long idRecinto, Long idEleccion) {
        // Obtener los dispositivos del recinto
        List<String> dispositivos = obtenerNombresDispositivos(idRecinto);

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

        return ActaRecintoDTO.builder()
                .fechaGeneracion(java.time.LocalDate.now())
                .nombreEleccion(eleccion.getNombre())
                .nombreRecinto(recinto.getNombre())
                .codigoRecinto(recinto.getCodigo())
                .nombresDispositivos(dispositivos)
                .totalVotos(totalVotos)
                .resultadosCandidatosDispositivos(resultadosDispositivos)
                .totalVotosCandidatos(totalVotosCandidatos)
                .actaRecintos(encargadosRecintos)
                .observaciones("") // Este campo puede ser completado por el usuario
                .QRUnico(qrUnico)
                .build();
    }

    private List<String> obtenerNombresDispositivos(Long idRecinto) {
        return dispositivoRepository.findNombresByRecintoId(idRecinto);
    }

    private int obtenerTotalVotos(Long idRecinto, Long idEleccion) {
        return votoRepository.countByRecintoIdAndEleccionId(idRecinto, idEleccion);
    }

    private List<ResultadosDispositivoDTO> obtenerResultadosDispositivos(Long idRecinto, Long idEleccion) {
        // Lógica para obtener los resultados de los dispositivos
        List<Dispositivo> dispositivos = dispositivoRepository.findByRecintoId(idRecinto);
        List<ResultadosDispositivoDTO> resultados = new ArrayList<>();
        for (Dispositivo dispositivo : dispositivos) {
            // Obtener los resultados por dispositivo
            List<ResultadosCandidatoDTO> resultadosCandidatos = obtenerResultadosCandidatos(dispositivo.getId(), idEleccion);
            resultados.add(new ResultadosDispositivoDTO(dispositivo.getNombre(), resultadosCandidatos));
        }
        return resultados;
    }

    private List<ResultadosCandidatoDTO> obtenerResultadosCandidatos(Long idDispositivo, Long idEleccion) {
        // Obtener los resultados de los candidatos para el dispositivo
        List<Candidato> candidatos = candidatoRepository.findByEleccionId(idEleccion);
        List<ResultadosCandidatoDTO> resultados = new ArrayList<>();
        for (Candidato candidato : candidatos) {
            // Obtener votos por dispositivo para cada candidato
            int votos = votoRepository.countByDispositivoIdAndCandidatoId(idDispositivo, candidato.getId());
            resultados.add(new ResultadosCandidatoDTO(candidato.getPartido().getNombre(), candidato.getNombre(), votos));
        }
        return resultados;
    }

    private List<TotalVotosCandidatosDTO> obtenerTotalVotosCandidatos(Long idRecinto, Long idEleccion) {
        // Obtener los votos totales por candidato para el recinto
        List<Candidato> candidatos = candidatoRepository.findByEleccionId(idEleccion);
        List<TotalVotosCandidatosDTO> totalVotos = new ArrayList<>();
        for (Candidato candidato : candidatos) {
            int votos = votoRepository.countByRecintoIdAndCandidatoId(idRecinto, candidato.getId());
            totalVotos.add(new TotalVotosCandidatosDTO(candidato.getPartido().getNombre(), candidato.getNombre(), votos));
        }
        return totalVotos;
    }

    private List<EncargadosRecintosDTO> obtenerEncargadosRecintos(Long idRecinto) {
        // Obtener los encargados del recinto
        List<Encargado> encargados = encargadoRepository.findByRecintoId(idRecinto);
        List<EncargadosRecintosDTO> encargadosDTO = new ArrayList<>();
        for (Encargado encargado : encargados) {
            encargadosDTO.add(new EncargadosRecintosDTO(encargado.getPersona().getNombre(), encargado.getPersona().getCi()));
        }
        return encargadosDTO;
    }

    private byte[] crearActaPdf(ActaRecintoDTO dto) {
        // Generación del PDF utilizando los datos del DTO
        // Código para generar el PDF con iText, usando la estructura similar a la que ya implementaste
    }
}