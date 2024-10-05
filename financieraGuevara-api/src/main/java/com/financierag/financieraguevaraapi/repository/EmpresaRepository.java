package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Solicitante, Integer> {
}
