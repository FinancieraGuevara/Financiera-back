package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    List<Prestamo> findByDetallePrestamoSolicitanteId(Integer solicitanteId);
    List<Prestamo> findByIsPayedFalseAndIsDeudaFalse();
    List<Prestamo> findByIsPayedTrue();
    List<Prestamo> findByIsJudicialDeudaTrue();
    List<Prestamo> findByIsCompletedTrue();
    List<Prestamo> findByDetallePrestamo_Cronograma_IsdeudaTrue();
}
