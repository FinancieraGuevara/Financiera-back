package com.financierag.financieraguevaraapi.model.dto;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoResponseDTO {

    private int id;
    private double monto;
    private int cuotas;
    private double interes;
    private int solicitanteId;
    private String username;
    boolean isPayed;
    boolean isCompleted;
    boolean isDeuda;
    boolean isJudicialDeuda;
    private List<CronogramaResponseDTO> detallecuotas;
    int cuotasJudicialesPagadas;
    int cuotasJudicialesporpagar;
    int cuotasPagadas;
    int cuotasporpagar;

}
