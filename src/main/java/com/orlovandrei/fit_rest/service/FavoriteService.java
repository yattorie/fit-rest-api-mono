package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;

import java.util.List;

public interface FavoriteService {
    void toggleFavoriteArticle(Long articleId, String username);
    void toggleFavoriteRecipe(Long recipeId, String username);
    void toggleFavoriteWorkout(Long workoutId, String username);

    List<ArticleDto> getFavoriteArticles(String username);
    List<RecipeDto> getFavoriteRecipes(String username);
    List<WorkoutDto> getFavoriteWorkouts(String username);
}

