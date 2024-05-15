package com.prueba.jesus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.jesus.model.dto.TurnDto;
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
public class TurnService {

    private final TurnRepository turnRepository;
    private final CashRepository cashRepository;
    private final UsuarioRespository usuarioRespository;

    @Transactional
    public TurnDto createTurn(TurnDto turn, Integer idgestor) {
        Usuario gestor = usuarioRespository.findById(idgestor)
                .orElseThrow(() -> new RuntimeException("Gestor not found"));
        Turn turnEntity = Turn.builder()
                .description(turn.getDescription())
                .date(turn.getDate())
                .usergestorid(gestor.getUserid())
                .build();
        turnRepository.save(turnEntity);
        return convertToDto(turnEntity);

    }

    @Transactional
    public TurnDto updateTurn(TurnDto turnDto, Integer id) {
        Turn turn = turnRepository.findById(id).orElseThrow(() -> new RuntimeException("Turn not found"));
        turn.setDescription(turnDto.getDescription());
        turn.setDate(turnDto.getDate());
        Turn turnsave = turnRepository.save(turn);
        return convertToDto(turnsave);
    }
    public List<Turn> findAll() {
        List<Turn> turnList = turnRepository.findAll();
        return turnList;
    }
    public Turn findById(Integer id) {
        Turn turn = turnRepository.findById(id).orElseThrow(() -> new RuntimeException("Turn not found"));
        return turn;
    }
    public TurnDto convertToDto(Turn turn) {
        TurnDto.TurnDtoBuilder builder = TurnDto.builder()
                .turnid(turn.getTurnid())
                .description(turn.getDescription())
                .usergestorid(turn.getUsergestorid())
                .date(turn.getDate());
        if (turn.getCashid() != null) {
            builder.cash_cashid(turn.getCashid().getCashid());
        }
        return builder.build();
    }
    @Transactional
    public void deleteTurn(Integer id) {
        turnRepository.deleteById(id);
    }

    @Transactional
    public TurnDto assignCashToTurn(Integer idcash, Integer idturn) {
        Cash cash = cashRepository.findById(idcash).orElseThrow(() -> new RuntimeException("Cash not found"));
        Turn turn = turnRepository.findById(idturn).orElseThrow(() -> new RuntimeException("Turn not found"));
        if (turn.getCashid() != null && turn.getCashid().getCashid().equals(cash.getCashid())) {
            throw new RuntimeException("No se puede asignar la caja al turno, ya está asignada");
        }
        turn.setCashid(cash);
        turnRepository.save(turn);
        return convertToDto(turn);
    }

}
