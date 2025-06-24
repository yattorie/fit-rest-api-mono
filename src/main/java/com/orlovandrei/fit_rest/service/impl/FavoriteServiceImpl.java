package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.mapper.ArticleMapper;
import com.orlovandrei.fit_rest.dto.mapper.RecipeMapper;
import com.orlovandrei.fit_rest.dto.mapper.WorkoutMapper;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.entity.article.Article;
import com.orlovandrei.fit_rest.entity.recipe.Recipe;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.entity.workout.Workout;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.exception.WorkoutNotFoundException;
import com.orlovandrei.fit_rest.repository.ArticleRepository;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.repository.WorkoutRepository;
import com.orlovandrei.fit_rest.service.FavoriteService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final RecipeRepository recipeRepository;
    private final WorkoutRepository workoutRepository;
    private final ArticleMapper articleMapper;
    private final RecipeMapper recipeMapper;
    private final WorkoutMapper workoutMapper;

    @Override
    public void toggleFavoriteArticle(Long articleId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(Messages.ARTICLE_NOT_FOUND_BY_ID.getMessage() + articleId));

        if (user.getFavoriteArticles().contains(article)) {
            user.getFavoriteArticles().remove(article);
        } else {
            user.getFavoriteArticles().add(article);
        }
        userRepository.save(user);
    }

    @Override
    public void toggleFavoriteRecipe(Long recipeId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + recipeId));

        if (user.getFavoriteRecipes().contains(recipe)) {
            user.getFavoriteRecipes().remove(recipe);
        } else {
            user.getFavoriteRecipes().add(recipe);
        }
        userRepository.save(user);
    }

    @Override
    public void toggleFavoriteWorkout(Long workoutId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(Messages.WORKOUT_NOT_FOUND_BY_ID.getMessage() + workoutId));

        if (user.getFavoriteWorkouts().contains(workout)) {
            user.getFavoriteWorkouts().remove(workout);
        } else {
            user.getFavoriteWorkouts().add(workout);
        }
        userRepository.save(user);
    }

    @Override
    public List<ArticleDto> getFavoriteArticles(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));
        return user.getFavoriteArticles().stream()
                .map(articleMapper::toDto).toList();
    }

    @Override
    public List<RecipeDto> getFavoriteRecipes(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));
        return user.getFavoriteRecipes().stream()
                .map(recipeMapper::toDto).toList();
    }

    @Override
    public List<WorkoutDto> getFavoriteWorkouts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));
        return user.getFavoriteWorkouts().stream()
                .map(workoutMapper::toDto).toList();
    }
}