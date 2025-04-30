package com.AppRestaurantMELI.dto;

import java.util.List;

public record ApiResponse(int page,
                          int per_page,
                          int total,
                          int total_pages,
                          List<FoodOutlet> data) {
}