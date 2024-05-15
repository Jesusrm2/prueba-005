package com.prueba.jesus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer>{
    boolean existsByDevicename(String devicename);


}
