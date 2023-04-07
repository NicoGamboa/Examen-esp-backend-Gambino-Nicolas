package com.dh.catalog.event;

import com.dh.catalog.Service.CatalagoService;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.ArrayList;
import java.util.List;

public class NewSerieEventConsumer {

    private final CatalagoService catalagoService;

    public NewSerieEventConsumer(CatalagoService catalagoService) {
        this.catalagoService = catalagoService;
    }


    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void listenSerie(Data message) {
    catalagoService.createSerie(message);
    }


        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class Data{
            private String id;
            private String name;
            private String genre;
            private List<DataSeason> seasons = new ArrayList<>();
        }
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class DataSeason {

        private Integer seasonNumber;
        private List<DataChapter> chapters = new ArrayList<>();
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class DataChapter {

        private String name;
        private Integer number;
        private String urlStream;


    }
    }
