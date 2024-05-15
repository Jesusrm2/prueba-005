package com.prueba.jesus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.jesus.model.dto.CashDto;
import com.prueba.jesus.model.entity.Cash;
import com.prueba.jesus.model.entity.Turn;
import com.prueba.jesus.model.entity.Usuario;
import com.prueba.jesus.repository.CashRepository;
import com.prueba.jesus.repository.TurnRepository;
import com.prueba.jesus.repository.UsuarioRespository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashService {

    private final CashRepository cashRepository;

    //Create cash
    @Transactional
    public CashDto createCash(CashDto cash) {
        Cash cashEntity = Cash.builder()
                .cashdescription(cash.getCashdescription())
                .active(cash.getActive())
                .build();
        cashRepository.save(cashEntity);
        return convertToDto(cashEntity);
    }
    //Update cash
    @Transactional
    public CashDto updateCash(CashDto cashDto, Integer id) {
        Cash cash = cashRepository.findById(id).orElseThrow(() -> new RuntimeException("Cash not found"));
        cash.setCashdescription(cashDto.getCashdescription());
        cash.setActive(cashDto.getActive());
        Cash cashsave = cashRepository.save(cash);
        return convertToDto(cashsave);
    }

    public List<CashDto> findAll() {
        List<Cash> cashList = cashRepository.findAll();
        return cashList.stream().map(this::convertToDto).toList();
    }

    //Find cash by id
    public CashDto findById(Integer id) {
        Cash cash = cashRepository.findById(id).orElseThrow(() -> new RuntimeException("Cash not found"));
        return convertToDto(cash);
    }


    //Delete cash
    @Transactional
    public void deleteCash(Integer id) {
        cashRepository.deleteById(id);
    }

    //Convertir a Dto
    public CashDto convertToDto(Cash cash) {
        return CashDto.builder()
                .cashid(cash.getCashid())
                .cashdescription(cash.getCashdescription())
                .active(cash.getActive())
                .build();
    }

 // Asignar caja a usuario
private final UsuarioRespository usuarioRespository;
private final TurnRepository turnRepository;

@Transactional
public void assignCash(Integer cashId, Integer userId) {
    Usuario usuario = usuarioRespository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Cash cash = cashRepository.findById(cashId).orElseThrow(() -> new RuntimeException("Cash not found"));

    if (usuario.getUsercash().size() >= 2) {
        throw new RuntimeException("El usuario ya tiene 2 cajas asignadas");
    }
    if (usuario.getUsercash().contains(cash)) {
        throw new RuntimeException("La caja ya está asignada a este usuario");
    }
    List<Turn> turns = turnRepository.findByCashid(cash);
    if (turns.isEmpty()) {
        throw new RuntimeException("La caja no está asignada a ningún turno");
    }
    usuario.getUsercash().add(cash);
    usuarioRespository.save(usuario);
}

    
}
