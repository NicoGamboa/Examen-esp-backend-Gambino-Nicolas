package com.dh.catalog.Service;

import com.dh.catalog.Repository.MovieRepository;
import com.dh.catalog.Repository.SerieRepository;
import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.event.NewMovieEventConsumer;
import com.dh.catalog.event.NewSerieEventConsumer;
import com.dh.catalog.service.MapperService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalagoService {


    private final SerieRepository serieRepository;
    private final MovieRepository movieRepository;
    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;
    private final com.dh.catalog.service.MapperService mapper;

    public CatalagoService(SerieRepository serieRepository, MovieRepository movieRepository, MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, MapperService mapper) {
        this.serieRepository = serieRepository;
        this.movieRepository = movieRepository;
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
        this.mapper = mapper;
    }
    @Retry(name = "retryCatalog")
    @CircuitBreaker(name = "configCatalog",fallbackMethod = "findMovieByGenreOffline")
    public List<MovieServiceClient.MovieDto> findMovieByGenre (String genre) {
        return movieServiceClient.getMovieByGenre(genre);
    }

    public List<MovieServiceClient.MovieDto> findMovieByGenreOffline(String genre) {
        return movieRepository.findMovieByGenre(genre);
    }
    @Retry(name = "retryCatalog")
    @CircuitBreaker(name = "configCatalog",fallbackMethod = "findSerieByGenreOffline")
    public List<SerieServiceClient.SerieDTO> findSerieByGenre (String genre) {
        return serieServiceClient.getSerieByGenre(genre);
    }

    public List<SerieServiceClient.SerieDTO> findSerieByGenreOffline(String genre) {
        return serieRepository.findSerieByGenre(genre);
    }

    public MovieServiceClient.MovieDto createMovie (NewMovieEventConsumer.Data message){
        MovieServiceClient.MovieDto movie = new MovieServiceClient.MovieDto();
        movie.setId(message.getId());
        movie.setGenre(message.getGenre());
        movie.setName(message.getName());
        movie.setUrlStream(message.getUrlStream());
        return movieRepository.save(movie);
    }
    public SerieServiceClient.SerieDTO createSerie (NewSerieEventConsumer.Data message){
        SerieServiceClient.SerieDTO serie = mapper.convert(message, SerieServiceClient.SerieDTO.class);
        return serieRepository.save(serie);
    }
}
