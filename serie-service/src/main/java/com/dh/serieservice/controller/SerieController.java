package com.dh.serieservice.controller;


import com.dh.serieservice.event.NewSerieEventProducer;
import com.dh.serieservice.model.Serie;
import com.dh.serieservice.service.MapperService;
import com.dh.serieservice.service.SerieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {

    private final SerieService serieService;
    private final NewSerieEventProducer newSerieEventProducer;
    private final MapperService mapperService;

    public SerieController(SerieService serieService, NewSerieEventProducer newSerieEventProducer, MapperService mapperService) {
        this.serieService = serieService;
        this.newSerieEventProducer = newSerieEventProducer;
        this.mapperService = mapperService;
    }

    @GetMapping
    public List<Serie> getAll() {
        return serieService.getAll();
    }

    @GetMapping("/{genre}")
    public List<Serie> getSerieByGenre(@PathVariable String genre) {
        return serieService.getSeriesBygGenre(genre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody Serie serie) {
        NewSerieEventProducer.Data data = mapperService.convert(serie, NewSerieEventProducer.Data.class);
        newSerieEventProducer.newEventSerie(data);
        serieService.create(serie);
        return serie.getId();
    }

}
