package com.prueba.jesus.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.prueba.jesus.model.entity.Device;
import com.prueba.jesus.model.entity.MethodPayment;
import com.prueba.jesus.model.entity.Rol;
import com.prueba.jesus.model.entity.Servicio;
import com.prueba.jesus.model.entity.StatusContract;
import com.prueba.jesus.model.entity.UserStatus;
import com.prueba.jesus.model.entity.Usuario;
import com.prueba.jesus.repository.DeviceRepository;
import com.prueba.jesus.repository.MethodPaymentRepository;
import com.prueba.jesus.repository.RolRepository;
import com.prueba.jesus.repository.ServicioRepository;
import com.prueba.jesus.repository.StatusContractRepository;
import com.prueba.jesus.repository.UserStatusRepository;
import com.prueba.jesus.repository.UsuarioRespository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataInitializerService {
    private final RolRepository rolRespository;
    private final UsuarioRespository usuarioRespository;
    private final UserStatusRepository userStatusRepository;
    private final ServicioRepository serviceRepository;
    private final StatusContractRepository statusContractRepository;
    private final MethodPaymentRepository methodPaymentRepository;
    private final DeviceRepository deviceRepository;

    @Transactional
    public void initData() {

        if (serviceRepository.count() == 0) {
            String[][] services = {
                    { "Servicio 1", "Combo 1", "100 Mbps", "100" },
                    { "Servicio 2", "Combo 2", "200 Mbps", "200" },
                    { "Servicio 3", "Combo 3", "300 Mbps", "300" }
            };
            for (String[] serviceData : services) {
                if (!serviceRepository.existsByServicename(serviceData[0])) {
                    saveService(serviceData[0], serviceData[1], serviceData[2], serviceData[3]);
                }
            }
        }

        if (statusContractRepository.count() == 0) {
            String[] statusContracts = { "VIG – Contrato vigente", "SUS – Contrato sustituido",
                    "CAN – Contrato cancelado" };
            for (String description : statusContracts) {
                if (!statusContractRepository.existsByDescription(description)) {
                    saveStatusContract(description);
                }
            }
        }

        if (methodPaymentRepository.count() == 0) {
            String[] methodPayments = { "Efectivo", "Tarjeta de crédito", "Transferencia" };
            for (String description : methodPayments) {
                if (!methodPaymentRepository.existsByDescription(description)) {
                    saveMethodPayment(description);
                }
            }
        }

        if (deviceRepository.count() == 0) {
            String[] devices = { "Router", "Modem", "Switch", "Access Point" };
            for (String description : devices) {
                if (!deviceRepository.existsByDevicename(description)) {
                    saveDevice(description);
                }
            }
        }

        if (!usuarioRespository.findByUsername("admin").isPresent()) {
            String[] roles = { "administradores", "gestores", "cajeros", "clientes" };
            for (int i = 0; i < roles.length; i++) {
                if (!rolRespository.existsByRolname(roles[i])) {
                    saveRol(i + 1, roles[i]);
                }
            }
            String[] userStatuses = { "activo", "inactivo", "pendiente" };
            for (String description : userStatuses) {
                if (!userStatusRepository.existsByDescription(description)) {
                    saveUserStatus(description);
                }
            }
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .email("admin")
                    .password("admin")
                    .creationdate(new Date(System.currentTimeMillis()))
                    .usercreate(1)
                    .userapproval(1)
                    .dateapproval(new Date(System.currentTimeMillis()))
                    .rol_rolid(rolRespository.findByRolname("administradores").get())
                    .status_statusid(userStatusRepository.findByDescription("activo").get())
                    .build();
            usuarioRespository.save(admin);
        }

    }

    private void saveService(String servicename, String servicedescription, String speed, String price) {
        Servicio service = Servicio.builder()
                .servicename(servicename)
                .servicedescription(servicedescription + ", Velocidad: " + speed)
                .price(price)
                .build();
        serviceRepository.save(service);
    }

    public void saveRol(Integer id, String rolname) {
        Rol rol = Rol.builder()
                .rolid(id)
                .rolname(rolname)
                .build();
        rolRespository.save(rol);
    }

    public void saveUserStatus(String description) {
        UserStatus userStatus = UserStatus.builder().description(description).build();
        userStatusRepository.save(userStatus);
    }

    public void saveStatusContract(String description) {
        StatusContract statusContract = StatusContract.builder().description(description).build();
        statusContractRepository.save(statusContract);
    }

    public void saveMethodPayment(String description) {
        MethodPayment methodPayment = MethodPayment.builder().description(description).build();
        methodPaymentRepository.save(methodPayment);
    }

    public void saveDevice(String description) {
        Device device = Device.builder().devicename(description).build();
        deviceRepository.save(device);
    }
}
