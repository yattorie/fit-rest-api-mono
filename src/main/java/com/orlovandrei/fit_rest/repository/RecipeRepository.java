package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {}

