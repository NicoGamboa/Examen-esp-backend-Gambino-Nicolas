package com.dh.catalog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import com.dh.catalog.Model.Catalogo;
import com.dh.catalog.Service.CatalagoService;
import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.event.NewMovieEventConsumer;
import com.dh.catalog.event.NewSerieEventConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.dh.catalog.controller.CatalogController;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CatalogController catalogController;

    @Autowired
    private CatalagoService catalagoService;

    @Test
    public void testFindMovieAndSerieByGenreOnline() {
        String genre = "test";

        // Crear una película
        NewMovieEventConsumer.Data newMovie = new NewMovieEventConsumer.Data();
        newMovie.setGenre(genre);
        newMovie.setId(1L);
        newMovie.setName("Test Movie");
        newMovie.setUrlStream("http://test.com/stream");
        catalagoService.createMovie(newMovie);

        // Crear una serie
       NewSerieEventConsumer.Data newSerie = new NewSerieEventConsumer.Data();
        newSerie.setGenre(genre);
        newSerie.setId("1");
        newSerie.setName("Test Serie");
        newSerie.getSeasons().add(new SerieServiceClient.SeasonDTO(1));
        newSerie.getSeasons().get(0).getChapters().add(new NewSerieEventConsumer.DataChapter("Test Chapter", 1, "http://test.com/stream"));
        catalagoService.createSerie(newSerie);

        // Realizar una solicitud GET al controlador Catalog para obtener las películas y series del género creado
        given().port(port).param("genre", genre).when().get("/catalog").then().statusCode(200)
                .body("movies.size()", equalTo(1), "movies[0].name", equalTo(newMovie.getName()),
                        "series.size()", equalTo(1), "series[0].name", equalTo(newSerie.getName()));

    }

    @Test
    public void testFindMovieAndSerieByGenreOffline() throws InterruptedException {
        String genre = "test";

        // Crear una película
        NewMovieEventConsumer.Data newMovie = new NewMovieEventConsumer.Data();
        newMovie.setGenre(genre);
        newMovie.setId(1L);
        newMovie.setName("Test Movie");
        newMovie.setUrlStream("http://test.com/stream");
        catalagoService.createMovie(newMovie);

        // Crear una serie
        NewSerieEventConsumer.Data newSerie = new NewSerieEventConsumer.Data();
        newSerie.setGenre(genre);
        newSerie.setId("1");
        newSerie.setName("Test Serie");
        newSerie.getSeasons().add(new SerieServiceClient.SeasonDTO(1));
        newSerie.getSeasons().get(0).getChapters().add(new NewSerieEventConsumer.DataChapter("Test Chapter", 1, "http://test.com/stream"));
        catalagoService.createSerie(newSerie);

        List<Catalogo> catalog = (List<Catalogo>) catalogController.getMovieSerieGenreOfline(genre);


        // Verificar que se obtienen los resultados esperados
        assertEquals(1, catalog.size());
        assertEquals(newMovie.getName(), catalog.get(0).getMovieDtos().get(0).getName());
        assertEquals(newSerie.getName(), catalog.get(0).getSerieDTOS().get(0).getName());
    }

