package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Dispositivo;
import com.eleccioneselectronicas.model.Recinto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    List<Dispositivo> findByRecinto_IdRecinto(Long idRecinto);

    // Si necesitas obtener solo los nombres de los dispositivos:
    @Query("SELECT d.modelo FROM Dispositivo d WHERE d.recinto.idRecinto = :idRecinto")
    List<String> findNombresByRecintoId(Long idRecinto);


}
