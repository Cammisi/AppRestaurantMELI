package com.AppRestaurantMELI.Client;

import com.AppRestaurantMELI.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class FoodOutletClient {
    private WebClient webClient;

    @Autowired
    public FoodOutletClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://jsonmock.hackerrank.com/api/food_outlets").build();
    }

    public ApiResponse getFoodOutlets(String cityName, int estimatedCost, int page){
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("city", cityName)
                            .queryParam("estimated_cost", estimatedCost)
                            .queryParam("page", page)
                            .build())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> Mono.error(new RuntimeException("Error en la API externa: " + response.statusCode()))
                    )
                    .bodyToMono(ApiResponse.class) // Si llega algo, response siempre es distinto de null
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error de respuesta HTTP: " + e.getStatusCode(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error al llamar a la API externa", e);
        }
    }
}