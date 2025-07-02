package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.article.CreateArticleRequest;
import com.orlovandrei.fit_rest.dto.article.UpdateArticleRequest;
import com.orlovandrei.fit_rest.dto.mapper.ArticleMapper;
import com.orlovandrei.fit_rest.entity.article.Article;
import com.orlovandrei.fit_rest.entity.category.Category;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.exception.ArticleAlreadyExistsException;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.CategoryNotFoundException;
import com.orlovandrei.fit_rest.repository.ArticleRepository;
import com.orlovandrei.fit_rest.repository.CategoryRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.service.ArticleService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ArticleDto create(CreateArticleRequest request, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(Messages.CATEGORY_NOT_FOUND_BY_ID.getMessage() + request.getCategoryId()));

        if (articleRepository.existsByTitle(request.getTitle())) {
            throw new ArticleAlreadyExistsException(Messages.ARTICLE_ALREADY_EXISTS.getMessage() + request.getTitle());
        }

        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .author(author)
                .category(category)
                .build();


        articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAll(pageable).map(articleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getByCategory(Long categoryId) {
        return articleRepository.findByCategoryId(categoryId).stream()
                .map(articleMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ArticleDto update(Long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(Messages.ARTICLE_NOT_FOUND_BY_ID.getMessage() + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(Messages.CATEGORY_NOT_FOUND_BY_ID.getMessage() + request.getCategoryId()));

        if (!article.getTitle().equals(request.getTitle()) && articleRepository.existsByTitleAndIdNot(request.getTitle(), id)) {
            throw new ArticleAlreadyExistsException(Messages.ARTICLE_ALREADY_EXISTS.getMessage() + request.getTitle());
        }

        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setCategory(category);

        return articleMapper.toDto(articleRepository.save(article));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "ArticleService::getById", key = "#id")
    public ArticleDto getById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(Messages.ARTICLE_NOT_FOUND_BY_ID.getMessage() + id));
        return articleMapper.toDto(article);
    }

    @Override
    @Transactional
    @CacheEvict(value = "ArticleService::getById", key = "#id")
    public void delete(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException(Messages.ARTICLE_NOT_FOUND_BY_ID.getMessage() + id);
        }
        articleRepository.deleteById(id);
    }
}


