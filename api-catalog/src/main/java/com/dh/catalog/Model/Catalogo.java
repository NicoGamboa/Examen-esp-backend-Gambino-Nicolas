package com.dh.catalog.Model;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalogo {

    private List<MovieServiceClient.MovieDto> movieDtos;
    private List<SerieServiceClient.SerieDTO> serieDTOS;



}
