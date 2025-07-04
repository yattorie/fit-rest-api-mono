package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.recipe.CreateRecipeRequest;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.recipe.UpdateRecipeRequest;
import com.orlovandrei.fit_rest.service.MinioService;
import com.orlovandrei.fit_rest.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipes")
@Tag(name = "Recipe Controller", description = "Operations for managing recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final MinioService minioService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Create a new recipe")
    public ResponseEntity<RecipeDto> create(@RequestBody @Valid CreateRecipeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all recipes")
    public ResponseEntity<Page<RecipeDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(recipeService.getAll(page, size));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get recipe by id")
    public ResponseEntity<RecipeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getById(id));
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Upload recipe image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String url = minioService.uploadRecipeImage(file, id);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/{id}/image")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete recipe image")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        minioService.deleteRecipeImage(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a recipe")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a recipe")
    public ResponseEntity<RecipeDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRecipeRequest request) {
        return ResponseEntity.ok(recipeService.update(id, request));
    }

}

