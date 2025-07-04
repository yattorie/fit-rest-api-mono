package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favorites")
@Tag(name = "Favorite Controller", description = "Operations for managing user favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/articles/{id}")
    @Operation(summary = "Toggle favorite article")
    public ResponseEntity<?> toggleArticle(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        favoriteService.toggleFavoriteArticle(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recipes/{id}")
    @Operation(summary = "Toggle favorite recipe")
    public ResponseEntity<?> toggleRecipe(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        favoriteService.toggleFavoriteRecipe(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/workouts/{id}")
    @Operation(summary = "Toggle favorite workout")
    public ResponseEntity<?> toggleWorkout(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        favoriteService.toggleFavoriteWorkout(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/articles")
    @Operation(summary = "Get favorite articles")
    public ResponseEntity<List<ArticleDto>> getFavArticles(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoriteService.getFavoriteArticles(userDetails.getUsername()));
    }

    @GetMapping("/recipes")
    @Operation(summary = "Get favorite recipes")
    public ResponseEntity<List<RecipeDto>> getFavRecipes(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoriteService.getFavoriteRecipes(userDetails.getUsername()));
    }

    @GetMapping("/workouts")
    @Operation(summary = "Get favorite workouts")
    public ResponseEntity<List<WorkoutDto>> getFavWorkouts(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoriteService.getFavoriteWorkouts(userDetails.getUsername()));
    }
}

