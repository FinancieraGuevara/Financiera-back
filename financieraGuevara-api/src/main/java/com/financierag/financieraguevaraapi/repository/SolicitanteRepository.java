package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, Integer> {
}
