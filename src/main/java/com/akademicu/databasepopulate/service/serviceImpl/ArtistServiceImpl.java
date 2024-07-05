package com.akademicu.databasepopulate.service.serviceImpl;

import com.akademicu.databasepopulate.models.Artist;
import com.akademicu.databasepopulate.repository.ArtistRepository;
import com.akademicu.databasepopulate.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ArtistServiceImpl implements ArtistService {

    private  final ArtistRepository artistRepository;
    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public void populateArtist(int numberOfArtists) {

        String url = "https://randomuser.me/api/?results="+numberOfArtists+"&nat=gb";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String,Object>> users = (List<Map<String, Object>>) response.get("results");

        for (Map<String,Object> user: users) {
            Map<String,String> name = (Map<String, String>) user.get("name");
            Map<String,String> location = (Map<String, String>) user.get("location");
            Map<String, String> login = (Map<String, String>) user.get("login");
            Map<String, String> picture = (Map<String, String>) user.get("picture");

            Artist artist = new Artist();
            String artistName = name.get("first") + " " + name.get("last");
            artist.setName(artistName);
            artist.setLocation(location.get("postcode"));
            artist.setEmail((String) user.get("email"));
            String password = hashPassword(login.get("password"));
            artist.setPassword(password);
            artist.setUrl(picture.get("large"));

            artistRepository.save(artist);
        }

    }

    private String hashPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
