package com.akademicu.databasepopulate.controller;


import com.akademicu.databasepopulate.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }


    @PostMapping("/api/populate-artists")
    public ResponseEntity<String> addArtistsInDb(@RequestParam int numOfArtists){

        artistService.populateArtist(numOfArtists);
        return new ResponseEntity<>("have been added "+numOfArtists+" artists in DB", HttpStatus.OK);
    }
}
