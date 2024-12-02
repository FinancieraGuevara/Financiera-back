package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.dto.DeudoresDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, Integer> {
    Optional<Solicitante> findByNumero(String numero);

    @Query("SELECT s FROM Solicitante s " +
            "JOIN DetallePrestamo dp ON s.id = dp.solicitante.id " +
            "JOIN dp.cronograma c " +
            "WHERE c.isdeuda = true")
    List<Solicitante> findDeudores();

    @Query("SELECT new com.financierag.financieraguevaraapi.model.dto.DeudoresDTO(s.id, s.numero, s.nombre_completo, s.tipo, SUM(c.cuota + c.mora)) " +
            "FROM Solicitante s " +
            "JOIN DetallePrestamo dp ON s.id = dp.solicitante.id " +
            "JOIN dp.cronograma c " +
            "WHERE c.isdeuda = true " +
            "GROUP BY s.id")
    List<DeudoresDTO> findDeudoresAndMoney();



}
