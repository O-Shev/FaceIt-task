package com.leshkins.faceittask.api;

import com.leshkins.faceittask.dto.ArbeitnowJobBoardApiDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ArbeitnowApi {

    private final RestClient client;

    public ArbeitnowApi() {
        this.client = RestClient.builder()
                .baseUrl("https://www.arbeitnow.com/api")
                .build();
    }


    public ArbeitnowJobBoardApiDTO jobBoardApi(int page){
        return client.get()
                .uri("/job-board-api/?page={?}", page)
                .retrieve()
                .body(ArbeitnowJobBoardApiDTO.class);
    }

}
