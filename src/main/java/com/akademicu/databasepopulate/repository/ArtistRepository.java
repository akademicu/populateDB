package com.akademicu.databasepopulate.repository;

import com.akademicu.databasepopulate.models.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ArtistRepository extends CrudRepository<Artist, Long> {
    @Query(value = "SELECT id FROM artist", nativeQuery = true)
    List<Long> getAllArtistIds();
}
