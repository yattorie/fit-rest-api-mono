package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.service.FavoriteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class FavoriteControllerTest {

    @Mock
    private FavoriteService favoriteService;

    @InjectMocks
    private FavoriteController favoriteController;

    @Mock
    private UserDetails userDetails;

    @Test
    void toggleArticle_success() {
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.doNothing().when(favoriteService).toggleFavoriteArticle(1L, "user");

        ResponseEntity<?> result = favoriteController.toggleArticle(1L, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Mockito.verify(favoriteService).toggleFavoriteArticle(1L, "user");
    }

    @Test
    void toggleRecipe_success() {
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.doNothing().when(favoriteService).toggleFavoriteRecipe(1L, "user");

        ResponseEntity<?> result = favoriteController.toggleRecipe(1L, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Mockito.verify(favoriteService).toggleFavoriteRecipe(1L, "user");
    }

    @Test
    void toggleWorkout_success() {
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.doNothing().when(favoriteService).toggleFavoriteWorkout(1L, "user");

        ResponseEntity<?> result = favoriteController.toggleWorkout(1L, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Mockito.verify(favoriteService).toggleFavoriteWorkout(1L, "user");
    }

    @Test
    void getFavArticles_success() {
        ArticleDto dto = new ArticleDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(favoriteService.getFavoriteArticles("user")).thenReturn(List.of(dto));

        ResponseEntity<List<ArticleDto>> result = favoriteController.getFavArticles(userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().size());
    }

    @Test
    void getFavRecipes_success() {
        RecipeDto dto = new RecipeDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(favoriteService.getFavoriteRecipes("user")).thenReturn(List.of(dto));

        ResponseEntity<List<RecipeDto>> result = favoriteController.getFavRecipes(userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().size());
    }

    @Test
    void getFavWorkouts_success() {
        WorkoutDto dto = new WorkoutDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(favoriteService.getFavoriteWorkouts("user")).thenReturn(List.of(dto));

        ResponseEntity<List<WorkoutDto>> result = favoriteController.getFavWorkouts(userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().size());
    }
}