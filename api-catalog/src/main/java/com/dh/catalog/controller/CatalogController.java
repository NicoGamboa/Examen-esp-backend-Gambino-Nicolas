package com.dh.catalog.controller;

import com.dh.catalog.Model.Catalogo;
import com.dh.catalog.Service.CatalagoService;
import com.dh.catalog.client.MovieServiceClient;

import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.event.NewMovieEventConsumer;
import com.dh.catalog.event.NewSerieEventConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieServiceClient movieServiceClient;
	private final SerieServiceClient serieServiceClient;
	private final CatalagoService catalagoService;


	public CatalogController(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, CatalagoService catalagoService) {
		this.movieServiceClient = movieServiceClient;
		this.serieServiceClient = serieServiceClient;
		this.catalagoService = catalagoService;

	}

	@GetMapping("online/{genre}")
	ResponseEntity<Catalogo> getMovieSerieGenreOnline(@PathVariable String genre) {
		Catalogo catalogo=new Catalogo();
		catalogo.setMovieDtos(catalagoService.findMovieByGenre(genre));
		catalogo.setSerieDTOS(catalagoService.findSerieByGenre(genre));
		return ResponseEntity.ok(catalogo);
	}
	@GetMapping("ofline/{genre}")
	ResponseEntity<Catalogo> getMovieSerieGenreOfline(@PathVariable String genre) {
		Catalogo catalogo=new Catalogo();
		catalogo.setMovieDtos(catalagoService.findMovieByGenreOffline(genre));
		catalogo.setSerieDTOS(catalagoService.findSerieByGenreOffline(genre));
		return ResponseEntity.ok(catalogo);
	}

}
