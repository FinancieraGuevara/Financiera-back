package com.financierag.financieraguevaraapi.config;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }
}

