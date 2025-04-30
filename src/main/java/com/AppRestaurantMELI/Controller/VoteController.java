package com.AppRestaurantMELI.Controller;

import com.AppRestaurantMELI.Service.VoteService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/votes")
@Validated
public class VoteController {

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<Integer> getVoteCount(@RequestParam @NotBlank String cityName, @RequestParam @Min(0) int estimatedCost) {
        return ResponseEntity.ok(voteService.getVoteCount(cityName, estimatedCost));
    }
}