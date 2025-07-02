package com.orlovandrei.fit_rest.entity.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "instructions", columnDefinition = "TEXT")
    String instructions;

    @Column(name = "cook_time_minutes", nullable = false)
    Integer cookTimeMinutes;

    @Column(name = "calories", nullable = false)
    Integer calories;

    @Column(name = "protein", nullable = false)
    Double protein;

    @Column(name = "fat", nullable = false)
    Double fat;

    @Column(name = "carbs", nullable = false)
    Double carbs;
}

