package com.prueba.jesus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.jesus.model.dto.ClientDto;
import com.prueba.jesus.service.ClientService;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("clients")
    public ResponseEntity<?> getClients() {
        try {
            List<ClientDto> clientList = clientService.getClients();
            if (clientList.isEmpty() || clientList == null) {
                return ResponseEntity.ok("No clients found");
            }
            return ResponseEntity.ok(clientList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }

    @GetMapping("client/{id}")
    public ResponseEntity<?> getClient(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(clientService.getClient(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //create
    @PostMapping("client")
    public ResponseEntity<?> createClient(@Validated @RequestBody ClientDto client) {
        try {
            ClientDto clientDto = clientService.createClient(client);
            return ResponseEntity.created(null).body(clientDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PutMapping("client/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id, @Validated @RequestBody ClientDto client) {
        try {
            ClientDto clientDto = clientService.updateClient(id, client);
            return ResponseEntity.ok().body(clientDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("client/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable  Integer id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



}
