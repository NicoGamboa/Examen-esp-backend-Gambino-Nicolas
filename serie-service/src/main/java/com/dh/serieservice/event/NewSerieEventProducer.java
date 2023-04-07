package com.dh.serieservice.event;

import com.dh.serieservice.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewSerieEventProducer {
    private final RabbitTemplate rabbitTemplate;


    public NewSerieEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void newEventMovie(Data message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.TOPIC_NEW_SERIE,message);
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
