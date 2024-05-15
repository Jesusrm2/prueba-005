package com.prueba.jesus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.jesus.model.dto.AttentionWithUser;
import com.prueba.jesus.model.dto.ContractDto;
import com.prueba.jesus.model.entity.Attention;
import com.prueba.jesus.model.entity.Contract;
import com.prueba.jesus.model.entity.Turn;
import com.prueba.jesus.service.ContractService;

@RestController
@RequestMapping("/api/v1")
public class ContractController {

    @Autowired
    private ContractService contractService;

    //ListTurnosPara contrato
    @GetMapping("contract/turns")
    public ResponseEntity<?> getTurns() {
        try {
            List<Turn> turnList = contractService.findAll();
            if (turnList.isEmpty() || turnList == null) {
                return ResponseEntity.ok("No turns found");
            }
            return ResponseEntity.ok(turnList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Create
    @PostMapping("contract/{idturn}")
    public ResponseEntity<?> createContract(@Validated @RequestBody ContractDto contract, @PathVariable Integer idturn) {
        try {
            ContractDto contractDto = contractService.createContract(contract, idturn);
            return ResponseEntity.created(null).body(contractDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //cambioservicio
    @PostMapping("contract/change/{idcontract}/service{idservice}")
    public ResponseEntity<?> changeService( @PathVariable Integer idcontract, @PathVariable Integer idservice) {
        try {
            ContractDto contractDto = contractService.changeService(idcontract, idservice);
            return ResponseEntity.created(null).body(contractDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //cambioforma de pago
    @PostMapping("contract/change/{idcontract}/paymentmethod/{idpaymentmethod}")
    public ResponseEntity<?> changePaymentMethod( @PathVariable Integer idcontract, @PathVariable Integer idpaymentmethod) {
        try {
            ContractDto contractDto = contractService.changePaymentMethod(idcontract, idpaymentmethod);
            return ResponseEntity.created(null).body(contractDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //cancelar contrato
    @PostMapping("contract/cancel/{idcontract}")
    public ResponseEntity<?> cancelContract( @PathVariable Integer idcontract) {
        try {
            ContractDto contractDto = contractService.cancelContract(idcontract);
            return ResponseEntity.created(null).body(contractDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Listar todos los contratos
    @GetMapping("contracts")
    public ResponseEntity<?> getContracts() {
        try {
            List<Contract> contractList = contractService.findAllContracts();
            if (contractList.isEmpty() || contractList == null) {
                return ResponseEntity.ok("No contracts found");
            }
            return ResponseEntity.ok(contractList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    //Listar todos los atendidos
    @GetMapping("attentions")
    public ResponseEntity<?> getAttentions() {
        try {
            List<AttentionWithUser> attentionList = contractService.findAllAttentions();
            if (attentionList.isEmpty() || attentionList == null) {
                return ResponseEntity.ok("No attentions found");
            }
            return ResponseEntity.ok(attentionList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
