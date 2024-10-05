package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePrestamoRespository extends JpaRepository<DetallePrestamo, Integer> {
    DetallePrestamo findBySolicitanteId(int solicitanteId);
}
