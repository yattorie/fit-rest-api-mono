package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.comment.CommentDto;
import com.orlovandrei.fit_rest.dto.comment.CreateCommentRequest;
import com.orlovandrei.fit_rest.dto.comment.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentDto create(CreateCommentRequest request, Long articleId, String username);
    List<CommentDto> getByArticleId(Long articleId);
    CommentDto getById(Long id);
    CommentDto update(Long id, UpdateCommentRequest request, String username);
    void delete(Long id, String username);

}


