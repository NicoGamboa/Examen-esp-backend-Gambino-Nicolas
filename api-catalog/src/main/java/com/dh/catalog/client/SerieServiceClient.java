package com.dh.catalog.client;

import com.dh.catalog.config.CustomLoadBalancerConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "api-serie")
@LoadBalancerClient(name = "api-serie", configuration = CustomLoadBalancerConfiguration.class)
public interface SerieServiceClient {

    @GetMapping("/api/v1/serie/{genre}")
    List<SerieDTO> getSerieByGenre(@PathVariable(value = "genre") String genre);
    @Getter
    @Setter
    class SerieDTO{
        private String id;
        private String name;
        private String genre;
        private List<SeasonDTO> seasons = new ArrayList<>();
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    class SeasonDTO {

        private Integer seasonNumber;
        private List<ChapterDTO> chapters = new ArrayList<>();
    }
        @AllArgsConstructor
        @NoArgsConstructor
        @Setter
        @Getter
        class ChapterDTO {

            private String name;
            private Integer number;
            private String urlStream;


        }}

