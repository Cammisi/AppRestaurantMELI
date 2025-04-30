package com.AppRestaurantMELI.dto;

public record FoodOutlet(String name,
                         String city,
                         int estimated_cost,
                         UserRating user_rating) {
}