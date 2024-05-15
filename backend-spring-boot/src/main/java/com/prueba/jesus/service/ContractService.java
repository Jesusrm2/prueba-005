package com.prueba.jesus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.prueba.jesus.model.dto.AttentionWithUser;
import com.prueba.jesus.model.dto.ContractDto;
import com.prueba.jesus.model.entity.Attention;
import com.prueba.jesus.model.entity.Cash;
import com.prueba.jesus.model.entity.Contract;
import com.prueba.jesus.model.entity.Device;
import com.prueba.jesus.model.entity.Servicio;
import com.prueba.jesus.model.entity.Turn;
import com.prueba.jesus.model.entity.Usuario;
import com.prueba.jesus.repository.AttentionRspository;
import com.prueba.jesus.repository.ClientRepository;
import com.prueba.jesus.repository.ContractRepository;
import com.prueba.jesus.repository.DeviceRepository;
import com.prueba.jesus.repository.MethodPaymentRepository;
import com.prueba.jesus.repository.ServicioRepository;
import com.prueba.jesus.repository.StatusContractRepository;
import com.prueba.jesus.repository.TurnRepository;
import com.prueba.jesus.repository.UsuarioRespository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final DeviceRepository deviceRepository;
    private final AttentionRspository attentionRspository;
    private final MethodPaymentRepository methodPaymentRepository;
    private final ServicioRepository serviceRepository;
    private final StatusContractRepository statusContractRepository;
    private final ClientRepository clientRepository;
    private final TurnRepository turnRepository;

    // Los usuarios cajeros atienden los siguientes procesos:
    // - Contratar un servicio de internet donde el usuario registra sus datos
    // personales, el tipo
    // de servicio de internet a contratar y la forma de pago.
    // - Pagos
    // - Cambio del servicio contratado
    // - Cambio de forma de pago
    // - Cancelación de contrato del servicio
    // 4. CRUD de contrato.
    // - Si un cliente pide el cambio de servicio por uno de mayor o menor velocidad
    // el
    // contrato cambia de estado (POR EJEMPLO: VIG – Contrato vigente, SUS –
    // Contrato sustituido), se crea un nuevo registro con un estado de renovación
    // de
    // servicio y la fecha final del contrato se mantiene.
    // - Si el cliente pide cancelación del contrato del servicio se actualiza el
    // estado y la
    // fecha fin.
    // - Si el cliente pide cambio de forma de pago.

    @Transactional
    public ContractDto createContract(ContractDto contractDto, Integer turn) {
        Contract contract = buildContract(contractDto);
        for (Integer deviceId : contractDto.getDevices()) {
            Device device = deviceRepository.findById(deviceId)
                    .orElseThrow(() -> new RuntimeException("Device not found: " + deviceId));

            Device newDevice = Device.builder()
                    .devicename(device.getDevicename())
                    .service_serviceid(contract.getService_serviceid())
                    .build();

            deviceRepository.save(newDevice);
        }
        Contract savedContract = contractRepository.save(contract);
        attentionRspository.save(Attention.builder()
                .turn(turnRepository.findById(turn).orElseThrow())
                .client(savedContract.getClient_clientid())
                .build());
        return convertToDto(savedContract, contractDto.getDevices());
    }

    private Contract buildContract(ContractDto contractDto) {
        Servicio existingService = serviceRepository.findById(contractDto.getServiceid())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Servicio newService = Servicio.builder()
                .servicename(existingService.getServicename())
                .servicedescription(existingService.getServicedescription())
                .price(existingService.getPrice())
                .build();
        Servicio savedService = serviceRepository.save(newService);

        return Contract.builder()
                .startdate(contractDto.getStartdate())
                .enddate(contractDto.getEnddate())
                .service_serviceid(savedService)
                .methodpayment_methodpaymentid(
                        methodPaymentRepository.findById(contractDto.getMethodpaymentid()).orElseThrow())
                .statuscontract_statuscontractid(
                        statusContractRepository.findById(1).orElseThrow())
                .client_clientid(clientRepository.findById(contractDto.getClientid()).orElseThrow())
                .build();
    }

    @Transactional
    public ContractDto changeService(Integer contractId, Integer newServiceId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        Servicio existingService = contract.getService_serviceid();
        Servicio newService = serviceRepository.findById(newServiceId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        existingService.setServicename(newService.getServicename());
        existingService.setServicedescription(newService.getServicedescription());
        existingService.setPrice(newService.getPrice());
        Servicio updateService = serviceRepository.save(existingService);
        contract.setService_serviceid(updateService);
        Contract updatedContract = contractRepository.save(contract);
        return convertToDto(updatedContract, null);
    }

    @Transactional
    public ContractDto cancelContract(Integer contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        contract.setStatuscontract_statuscontractid(statusContractRepository.findById(3).orElseThrow());
        Contract updatedContract = contractRepository.save(contract);
        return convertToDto(updatedContract, null);
    }

    @Transactional
    public ContractDto changePaymentMethod(Integer contractId, Integer newMethodPayment) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        contract.setMethodpayment_methodpaymentid(methodPaymentRepository.findById(newMethodPayment).orElseThrow());
        Contract updatedContract = contractRepository.save(contract);
        return convertToDto(updatedContract, null);
    }

    // convertToDto
    private ContractDto convertToDto(Contract contract, Integer[] devices) {
        ContractDto.ContractDtoBuilder builder = ContractDto.builder()
                .startdate(contract.getStartdate())
                .enddate(contract.getEnddate())
                .serviceid(contract.getService_serviceid().getServiceid())
                .methodpaymentid(contract.getMethodpayment_methodpaymentid().getMethodpaymentid())
                .statuscontractid(contract.getStatuscontract_statuscontractid().getStatuscontractid())
                .clientid(contract.getClient_clientid().getClientid());
        if (devices != null) {
            builder.devices(devices);
        }

        return builder.build();
    }

    public List<Turn> findAll() {
        List<Turn> tunrosList = turnRepository.findAll();
        return tunrosList;
    }

    public List<Contract> findAllContracts() {
        List<Contract> contractList = contractRepository.findAll();
        return contractList;
    }

    private final UsuarioRespository userRepository;

    public List<AttentionWithUser> findAllAttentions() {
        List<Attention> attentionList = attentionRspository.findAll();
        List<AttentionWithUser> result = new ArrayList<>();
        for (Attention attention : attentionList) {
            Cash cash = attention.getTurn().getCashid();
            List<Usuario> users = userRepository.findAll();
            for (Usuario user : users) {
                Set<Cash> userCash = user.getUsercash();
                if (userCash.contains(cash)) {
                    AttentionWithUser attentionWithUser = new AttentionWithUser();
                    attentionWithUser.setAttention(attention);
                    attentionWithUser.setUser(user);
                    result.add(attentionWithUser);
                    break;
                }
            }
        }
        return result;
    }

}
