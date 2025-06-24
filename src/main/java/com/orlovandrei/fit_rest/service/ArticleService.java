package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.article.CreateArticleRequest;
import com.orlovandrei.fit_rest.dto.article.UpdateArticleRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {
    ArticleDto create(CreateArticleRequest request, String username);
    Page<ArticleDto> getAll(int page, int size);
    ArticleDto getById(Long id);
    List<ArticleDto> getByCategory(Long categoryId);
    ArticleDto update(Long id, UpdateArticleRequest request);
    @Transactional
    void delete(Long id);
}

