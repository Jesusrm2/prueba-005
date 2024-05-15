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

import com.prueba.jesus.model.dto.TurnDto;
import com.prueba.jesus.model.entity.Turn;
import com.prueba.jesus.service.TurnService;

@RestController
@RequestMapping("/api/v1")

public class TurnController {

    @Autowired
    private TurnService turnService;

    @GetMapping("turn/{id}")
    public ResponseEntity<?> getTurn(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(turnService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("turns")
    public ResponseEntity<?> getTurns() {
        try {
            List<Turn> turnList = turnService.findAll();
            if (turnList.isEmpty() || turnList == null) {
                return ResponseEntity.ok("No turns found");
            }
            return ResponseEntity.ok(turnList);
          
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    //Create
    @PostMapping("turn/gestor/{idgestor}")
    public ResponseEntity<?> createTurn(@Validated  @RequestBody  TurnDto turn, @PathVariable Integer idgestor) {
        try {
            TurnDto turnDto = turnService.createTurn(turn, idgestor);
            return ResponseEntity.created(null).body(turnDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Update
    @PutMapping("turn/{id}")
    public ResponseEntity<?> updateTurn(@PathVariable Integer id, @Validated @RequestBody  TurnDto turn) {
        try {
            TurnDto turnDto = turnService.updateTurn(turn, id);
            return ResponseEntity.ok().body(turnDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    //dELETE
    @DeleteMapping("turn/{id}")
    public ResponseEntity<?> deleteTurn(@PathVariable Integer id) {
        try {
            turnService.deleteTurn(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Asignar caja a turn
    @PostMapping("turn/{idturn}/cash/{idcash}")
    public ResponseEntity<?> assignCashToTurn(@PathVariable Integer idcash, @PathVariable Integer idturn) {
        try {
            TurnDto turnDto = turnService.assignCashToTurn(idcash, idturn);
            return ResponseEntity.ok().body(turnDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
