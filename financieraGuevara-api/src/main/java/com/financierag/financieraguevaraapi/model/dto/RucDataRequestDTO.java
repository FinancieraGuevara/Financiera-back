package com.financierag.financieraguevaraapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RucDataRequestDTO {
    private String direccion;
    private String direccionCompleta;
    private String ruc;
    private String nombreORazonSocial;
    private String estado;
    private String condicion;
    private String departamento;
    private String provincia;
    private String distrito;
    private String ubigeoSunat;
    private String[] ubigeo;
    private Boolean esAgenteDeRetencion;
    private Boolean esAgenteDePercepcion;
    private String esAgenteDePercepcionCombustible;
    private Boolean esBuenContribuyente;
}
