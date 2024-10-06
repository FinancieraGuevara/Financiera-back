package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePrestamoRespository extends JpaRepository<DetallePrestamo, Integer> {
    List<DetallePrestamo> findBySolicitanteId(int solicitanteId);

    @Query("SELECT dp FROM DetallePrestamo dp WHERE dp.solicitante.id = :solicitanteId ORDER BY dp.fechaInicio DESC, dp.detailId DESC")
    List<DetallePrestamo> findLatestBySolicitanteId(@Param("solicitanteId") int solicitanteId);
}
