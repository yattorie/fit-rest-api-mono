package com.orlovandrei.fit_rest.entity.user;

import com.orlovandrei.fit_rest.entity.article.Article;
import com.orlovandrei.fit_rest.entity.recipe.Recipe;
import com.orlovandrei.fit_rest.entity.workout.Workout;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "weight")
    Double weight;

    @Column(name = "height")
    Double height;

    @Column(name = "birth_date")
    LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @ManyToMany
    @JoinTable(name = "user_favorite_articles")
    Set<Article> favoriteArticles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_favorite_recipes")
    Set<Recipe> favoriteRecipes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_favorite_workouts")
    Set<Workout> favoriteWorkouts = new HashSet<>();

}
