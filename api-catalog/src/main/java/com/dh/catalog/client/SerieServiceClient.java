package com.dh.catalog.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "api-margins")
public interface SerieServiceClient {

}
