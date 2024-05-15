package com.prueba.jesus.config;

import org.springframework.stereotype.Component;

import com.prueba.jesus.service.DataInitializerService;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final DataInitializerService dataInitializerService;    
    
    public DataInitializer(DataInitializerService dataInitializerService) {
        this.dataInitializerService = dataInitializerService;
    }
    @PostConstruct
    public void initData() {
        dataInitializerService.initData();

    }

}
