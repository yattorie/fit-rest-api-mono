package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.comment.CommentDto;
import com.orlovandrei.fit_rest.dto.comment.CreateCommentRequest;
import com.orlovandrei.fit_rest.dto.comment.UpdateCommentRequest;
import com.orlovandrei.fit_rest.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@Tag(name = "Comment Controller", description = "Operations for managing article comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}/comments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Create a new comment")
    public ResponseEntity<CommentDto> create(
            @PathVariable Long articleId,
            @RequestBody @Valid CreateCommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.create(request, articleId, userDetails.getUsername()));
    }

    @GetMapping("/{articleId}/comments")
    @Operation(summary = "Get comments by article")
    public ResponseEntity<List<CommentDto>> getByArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(commentService.getByArticleId(articleId));
    }

    @GetMapping("/comments/{id}")
    @Operation(summary = "Get comment by id")
    public ResponseEntity<CommentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PutMapping("/comments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update a comment")
    public ResponseEntity<CommentDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(commentService.update(id, request, userDetails.getUsername()));
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Delete a comment")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}


