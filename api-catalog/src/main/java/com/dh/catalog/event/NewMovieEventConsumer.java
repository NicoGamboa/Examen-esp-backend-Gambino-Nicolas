package com.dh.catalog.event;

import com.dh.catalog.Service.CatalagoService;
import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class NewMovieEventConsumer {
    private final CatalagoService catalagoService;
    private final MovieServiceClient movieServiceClient;

    public NewMovieEventConsumer(CatalagoService catalagoService, MovieServiceClient movieServiceClient) {
        this.catalagoService = catalagoService;
        this.movieServiceClient = movieServiceClient;
    }


    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void listenMovie(Data message) {
    catalagoService.createMovie(message);
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private Long id;

        private String name;

        private String genre;

        private String urlStream;
    }
}

