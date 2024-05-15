package com.prueba.jesus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prueba.jesus.model.dto.ClientDto;
import com.prueba.jesus.model.entity.Client;
import com.prueba.jesus.repository.ClientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ClientService {


    private final ClientRepository clientRepository;


    public ClientDto getClient(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente con id " + id));
        return convertirAClientDto(client);
    }

    public List<ClientDto> getClients() {
        return clientRepository.findAll().stream().map(this::convertirAClientDto).collect(Collectors.toList());
    }

    @Transactional
    public ClientDto createClient(ClientDto clientDto) {

        if (clientRepository.findByIdentification(clientDto.getIdentification()).isPresent()) {
            throw new RuntimeException("La identificación " + clientDto.getIdentification() + " ya existe.");
        }

        Client client = Client.builder()
                .name(clientDto.getName())
                .lastname(clientDto.getLastname())
                .identification(clientDto.getIdentification())
                .email(clientDto.getEmail())
                .phonenumber(clientDto.getPhonenumber())
                .address(clientDto.getAddress())
                .referenceaddress(clientDto.getReferenceaddress())
                .build();
        Client clientsave = clientRepository.save(client);
        return convertirAClientDto(clientsave);
    }


    @Transactional
    public ClientDto updateClient(Integer id, ClientDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente con id " + id));
        if (clientRepository.findByIdentification(clientDto.getIdentification()).isPresent()) {
            throw new RuntimeException("La identificación " + clientDto.getIdentification() + " ya existe.");
        }
        client.setName(clientDto.getName());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setPhonenumber(clientDto.getPhonenumber());
        client.setAddress(clientDto.getAddress());
        client.setReferenceaddress(clientDto.getReferenceaddress());

        Client clientsave = clientRepository.save(client);
        return convertirAClientDto(clientsave);
    }

    @Transactional
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }



    public ClientDto convertirAClientDto(Client client) {
        return ClientDto.builder()
                .clientid(client.getClientid())
                .name(client.getName())
                .lastname(client.getLastname())
                .identification(client.getIdentification())
                .email(client.getEmail())
                .phonenumber(client.getPhonenumber())
                .address(client.getAddress())
                .referenceaddress(client.getReferenceaddress())
                .build();
    }

    

    

}
