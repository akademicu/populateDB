package com.akademicu.databasepopulate.service.serviceImpl;

import com.akademicu.databasepopulate.models.Artist;
import com.akademicu.databasepopulate.models.Tattoo;
import com.akademicu.databasepopulate.repository.ArtistRepository;
import com.akademicu.databasepopulate.repository.TattooRepository;
import com.akademicu.databasepopulate.service.TattooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class TattooServiceImpl implements TattooService {

    private final TattooRepository tattooRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public TattooServiceImpl(TattooRepository tattooRepository, ArtistRepository artistRepository) {
        this.tattooRepository = tattooRepository;
        this.artistRepository = artistRepository;
    }

    @Value("${pexels.api.key}")
    private String pexelsApiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addTattoosForAllArtists() {
        List<Long> artistIds = new ArrayList<>(artistRepository.getAllArtistIds());
        String url = "https://api.pexels.com/v1/search?query=tattoo&per_page=80";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", pexelsApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        List<Map<String, Object>> photos = (List<Map<String, Object>>) response.getBody().get("photos");

        Iterator iterator = photos.iterator();

        for(Long i: artistIds){
            for (int j=0;j<2;j++){
                if (iterator.hasNext()){
                    Map<String, Object> ob = (Map<String, Object>) iterator.next();
                    Map<String, Object> src = (Map<String, Object>) ob.get("src");
                    String photoUrl = (String) src.get("medium");
                    String price = "$50";
                    Artist artist = artistRepository.findById(i).orElseThrow(()->new RuntimeException("No ID"));
                    Tattoo tattoo = new Tattoo();
                    tattoo.setArtist(artist);
                    tattoo.setDesign(photoUrl);
                    tattoo.setHoursWorked("15h");
                    tattoo.setPrice(price);
                    tattoo.setTimePosted(Instant.now());
                    tattooRepository.save(tattoo);
                }else break;
            }
        }
    }

}
