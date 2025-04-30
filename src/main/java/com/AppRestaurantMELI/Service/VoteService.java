package com.AppRestaurantMELI.Service;

import com.AppRestaurantMELI.Exception.ResourceNotFoundException;
import com.AppRestaurantMELI.Client.FoodOutletClient;
import com.AppRestaurantMELI.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@Service
public class VoteService {
    private FoodOutletClient foodOutletClient;

    @Autowired
    public VoteService(FoodOutletClient foodOutletClient){
        this.foodOutletClient = foodOutletClient;
    }

    public int getVoteCount(String cityName, int estimatedCost) {
        if (cityName == null || cityName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la ciudad no puede estar vacío.");
        }
        if (estimatedCost < 0) {
            throw new IllegalArgumentException("El costo estimado debe ser un valor positivo.");
        }

        ApiResponse respuesta;
        int totalVotes=0;
        int page=1;

        do {
            respuesta = foodOutletClient.getFoodOutlets(cityName, estimatedCost, page);

            Optional.ofNullable(respuesta)
                    .map(ApiResponse::data)
                    .filter(data -> !data.isEmpty())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontraron datos"));

            totalVotes += respuesta.data().stream()
                    .mapToInt(outlet -> outlet.user_rating().votes())
                    .sum();

            page++;

        } while (page <= respuesta.total_pages());

        return totalVotes == 0 ? -1 : totalVotes;
    }
}


