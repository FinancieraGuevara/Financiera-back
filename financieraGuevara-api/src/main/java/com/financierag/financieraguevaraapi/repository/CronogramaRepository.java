package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronogramaRepository extends JpaRepository<Cuota, Integer> {
}
