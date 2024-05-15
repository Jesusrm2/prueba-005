package com.prueba.jesus.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.jesus.model.dto.CashDto;
import com.prueba.jesus.service.CashService;

@RestController
@RequestMapping("/api/v1")
public class CashController {

    @Autowired
    private CashService cashService;


    //Get one cash
    @GetMapping("cash/{id}")
    public ResponseEntity<?> getCash(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(cashService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Get all cash
    @GetMapping("cashs")
    public ResponseEntity<?> getAllCash() {
        try {
            List<CashDto> cashList = cashService.findAll();
            if (cashList.isEmpty() || cashList == null) {
                return ResponseEntity.ok("No cash found");
            }
            return ResponseEntity.ok(cashList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Create
    @PostMapping("cash")
    public ResponseEntity<?> createCash(@Validated @RequestBody CashDto cash) {
        try {
            CashDto cashDto = cashService.createCash(cash);
            return ResponseEntity.created(null).body(cashDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Update
    @PostMapping("cash/{id}")
    public ResponseEntity<?> updateCash(@PathVariable Integer id,@Validated @RequestBody CashDto cash) {
        try {
            CashDto cashDto = cashService.updateCash(cash, id);
            return ResponseEntity.created(null).body(cashDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Delete
    @DeleteMapping("cash/{id}")
    public ResponseEntity<?> deleteCash(@PathVariable Integer id) {
        try {
            cashService.deleteCash(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Assign cash to user
    @PostMapping("cash/{id}/user/{iduser}")
    public ResponseEntity<?> assignCashToUser(@PathVariable Integer id, @PathVariable Integer iduser) {
        try {
            cashService.assignCash(id, iduser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    

}
