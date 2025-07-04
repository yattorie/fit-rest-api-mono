package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.article.CreateArticleRequest;
import com.orlovandrei.fit_rest.dto.article.UpdateArticleRequest;
import com.orlovandrei.fit_rest.service.ArticleService;
import com.orlovandrei.fit_rest.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/api/v1/articles")
@Tag(name = "Article Controller", description = "Operations related to articles")
public class ArticleController {
    
    private final ArticleService articleService;
    private final MinioService minioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new article")
    public ResponseEntity<ArticleDto> create(
            @RequestBody @Valid CreateArticleRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.create(request, userDetails.getUsername()));
    }

    @GetMapping
    @Operation(summary = "Get all articles")
    public ResponseEntity<Page<ArticleDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.getAll(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get article by id")
    public ResponseEntity<ArticleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an article")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/image")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete article image")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        minioService.deleteArticleImage(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Upload article image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String url = minioService.uploadArticleImage(file, id);
        return ResponseEntity.ok(url);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an article")
    public ResponseEntity<ArticleDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateArticleRequest request) {
        return ResponseEntity.ok(articleService.update(id, request));
    }
}

