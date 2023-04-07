package com.dh.catalog.Repository;

import com.dh.catalog.client.MovieServiceClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<MovieServiceClient.MovieDto,String> {
    List<MovieServiceClient.MovieDto> findMovieByGenre(String genre);
}
