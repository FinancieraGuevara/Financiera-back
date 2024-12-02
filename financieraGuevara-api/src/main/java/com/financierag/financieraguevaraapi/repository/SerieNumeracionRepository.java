package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.entity.SerieNumeracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerieNumeracionRepository extends JpaRepository<SerieNumeracion, Integer> {
    List<SerieNumeracion> findByBoletaIsNotNull();
    List<SerieNumeracion> findByFacturaIsNotNull();
}
