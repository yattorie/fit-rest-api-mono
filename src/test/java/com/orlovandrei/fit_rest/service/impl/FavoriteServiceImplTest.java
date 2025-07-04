package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.mapper.ArticleMapper;
import com.orlovandrei.fit_rest.dto.mapper.RecipeMapper;
import com.orlovandrei.fit_rest.dto.mapper.WorkoutMapper;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.entity.Article;
import com.orlovandrei.fit_rest.entity.Recipe;
import com.orlovandrei.fit_rest.entity.User;
import com.orlovandrei.fit_rest.entity.Workout;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.exception.WorkoutNotFoundException;
import com.orlovandrei.fit_rest.repository.ArticleRepository;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.repository.WorkoutRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private ArticleMapper articleMapper;
    @Mock
    private RecipeMapper recipeMapper;
    @Mock
    private WorkoutMapper workoutMapper;
    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Test
    void toggleFavoriteArticle_addsAndRemoves() {
        User user = User.builder().username("u").favoriteArticles(new HashSet<>()).build();
        Article article = Article.builder().id(1L).build();

        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        favoriteService.toggleFavoriteArticle(1L, "u");
        Assertions.assertTrue(user.getFavoriteArticles().contains(article));

        user.getFavoriteArticles().add(article);
        favoriteService.toggleFavoriteArticle(1L, "u");
        Assertions.assertFalse(user.getFavoriteArticles().contains(article));
    }

    @Test
    void toggleFavoriteArticle_throwsUsernameNotFound() {
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> favoriteService.toggleFavoriteArticle(1L, "u"));
    }

    @Test
    void toggleFavoriteArticle_articleNotFound() {
        User user = User.builder().username("u").favoriteArticles(new HashSet<>()).build();
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ArticleNotFoundException.class, () -> favoriteService.toggleFavoriteArticle(1L, "u"));
    }

    @Test
    void toggleFavoriteRecipe_addsAndRemoves() {
        User user = User.builder().username("u").favoriteRecipes(new HashSet<>()).build();
        Recipe recipe = Recipe.builder().id(2L).build();

        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(recipeRepository.findById(2L)).thenReturn(Optional.of(recipe));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        favoriteService.toggleFavoriteRecipe(2L, "u");
        Assertions.assertTrue(user.getFavoriteRecipes().contains(recipe));

        user.getFavoriteRecipes().add(recipe);
        favoriteService.toggleFavoriteRecipe(2L, "u");
        Assertions.assertFalse(user.getFavoriteRecipes().contains(recipe));
    }

    @Test
    void toggleFavoriteRecipe_throwsRecipeNotFound() {
        User user = User.builder().username("u").favoriteRecipes(new HashSet<>()).build();
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(recipeRepository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(RecipeNotFoundException.class, () -> favoriteService.toggleFavoriteRecipe(2L, "u"));
    }

    @Test
    void toggleFavoriteWorkout_addsAndRemoves() {
        User user = User.builder().username("u").favoriteWorkouts(new HashSet<>()).build();
        Workout workout = Workout.builder().id(3L).build();

        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(workoutRepository.findById(3L)).thenReturn(Optional.of(workout));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        favoriteService.toggleFavoriteWorkout(3L, "u");
        Assertions.assertTrue(user.getFavoriteWorkouts().contains(workout));

        user.getFavoriteWorkouts().add(workout);
        favoriteService.toggleFavoriteWorkout(3L, "u");
        Assertions.assertFalse(user.getFavoriteWorkouts().contains(workout));
    }

    @Test
    void toggleFavoriteWorkout_throwsWorkoutNotFound() {
        User user = User.builder().username("u").favoriteWorkouts(new HashSet<>()).build();
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(workoutRepository.findById(3L)).thenReturn(Optional.empty());
        Assertions.assertThrows(WorkoutNotFoundException.class, () -> favoriteService.toggleFavoriteWorkout(3L, "u"));
    }

    @Test
    void getFavoriteArticles() {
        Article article = Article.builder().id(1L).build();
        User user = User.builder().username("u").favoriteArticles(Set.of(article)).build();
        ArticleDto dto = new ArticleDto();
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(articleMapper.toDto(article)).thenReturn(dto);

        List<ArticleDto> result = favoriteService.getFavoriteArticles("u");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getFavoriteRecipes() {
        Recipe recipe = Recipe.builder().id(2L).build();
        User user = User.builder().username("u").favoriteRecipes(Set.of(recipe)).build();
        RecipeDto dto = new RecipeDto();
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(recipeMapper.toDto(recipe)).thenReturn(dto);

        List<RecipeDto> result = favoriteService.getFavoriteRecipes("u");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getFavoriteWorkouts() {
        Workout workout = Workout.builder().id(3L).build();
        User user = User.builder().username("u").favoriteWorkouts(Set.of(workout)).build();
        WorkoutDto dto = new WorkoutDto();
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));
        Mockito.when(workoutMapper.toDto(workout)).thenReturn(dto);

        List<WorkoutDto> result = favoriteService.getFavoriteWorkouts("u");
        Assertions.assertEquals(1, result.size());
    }
}