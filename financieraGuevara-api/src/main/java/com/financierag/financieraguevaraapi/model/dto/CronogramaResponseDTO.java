package com.financierag.financieraguevaraapi.model.dto;

import com.financierag.financieraguevaraapi.model.entity.SerieNumeracion;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CronogramaResponseDTO {

    private int id;

    int nmrcuota;

    double cuota;
    double interes;
    double capitalamortizado;
    double saldofinal;
    LocalDate fechaPago;
    LocalDate fechadeCancelamiento;
    boolean ispayed;
    boolean isdeuda;
    boolean isjudicial;
    double mora=0;
    long totaldiasmora;
    double totalmora;
    SerieNumeracion serieNumeracion;
}
