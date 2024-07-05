package com.akademicu.databasepopulate.controller;

import com.akademicu.databasepopulate.service.TattooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TattooController {

    private final TattooService tattooService;

    @Autowired
    public TattooController(TattooService tattooService) {
        this.tattooService = tattooService;
    }

    @PostMapping("/api/populate-tattoo")
    public ResponseEntity<String> addTattoosForArtists(){
        tattooService.addTattoosForAllArtists();
        return new ResponseEntity<>("check DB", HttpStatus.OK);

    }
}
