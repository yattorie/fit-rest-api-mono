package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.article.CreateArticleRequest;
import com.orlovandrei.fit_rest.dto.article.UpdateArticleRequest;
import com.orlovandrei.fit_rest.dto.mapper.ArticleMapper;
import com.orlovandrei.fit_rest.entity.article.Article;
import com.orlovandrei.fit_rest.entity.category.Category;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.CategoryNotFoundException;
import com.orlovandrei.fit_rest.repository.ArticleRepository;
import com.orlovandrei.fit_rest.repository.CategoryRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void create_success() {
        CreateArticleRequest request = new CreateArticleRequest();
        request.setTitle("title");
        request.setContent("content");
        request.setCategoryId(1L);

        User user = User.builder().username("user").build();
        Category category = Category.builder().id(1L).build();
        Article article = Article.builder().title("title").content("content").author(user).category(category).createdAt(LocalDateTime.now()).build();
        ArticleDto dto = new ArticleDto();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Mockito.when(articleRepository.save(any(Article.class))).thenReturn(article);
        Mockito.when(articleMapper.toDto(any(Article.class))).thenReturn(dto);

        ArticleDto result = articleService.create(request, "user");

        Assertions.assertNotNull(result);
        Mockito.verify(articleRepository).save(any(Article.class));
    }

    @Test
    void create_throwsUsernameNotFound() {
        CreateArticleRequest request = new CreateArticleRequest();
        request.setCategoryId(1L);

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> articleService.create(request, "user"));
    }

    @Test
    void create_throwsCategoryNotFoundException() {
        CreateArticleRequest request = new CreateArticleRequest();
        request.setCategoryId(1L);

        User user = User.builder().username("user").build();
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(CategoryNotFoundException.class, () -> articleService.create(request, "user"));
    }

    @Test
    void getAll() {
        Page<Article> page = new PageImpl<>(List.of(Article.builder().build()));
        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(articleMapper.toDto(any(Article.class))).thenReturn(new ArticleDto());

        Page<ArticleDto> result = articleService.getAll(0, 10);

        Assertions.assertEquals(1, result.getTotalElements());
    }

    @Test
    void getByCategory() {
        Mockito.when(articleRepository.findByCategoryId(2L)).thenReturn(List.of(Article.builder().build()));
        Mockito.when(articleMapper.toDto(any(Article.class))).thenReturn(new ArticleDto());

        List<ArticleDto> result = articleService.getByCategory(2L);

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void update_success() {
        Article article = Article.builder()
                .id(5L)
                .title("oldTitle")
                .category(Category.builder().id(1L).build())
                .build();
        UpdateArticleRequest request = new UpdateArticleRequest();
        request.setTitle("title");
        request.setContent("content");
        request.setCategoryId(2L);
        Category category = Category.builder().id(2L).build();

        Mockito.when(articleRepository.findById(5L)).thenReturn(Optional.of(article));
        Mockito.when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        Mockito.when(articleRepository.save(any(Article.class))).thenReturn(article);
        Mockito.when(articleMapper.toDto(any(Article.class))).thenReturn(new ArticleDto());

        ArticleDto result = articleService.update(5L, request);

        Assertions.assertNotNull(result);
        Mockito.verify(articleRepository).save(any(Article.class));
    }

    @Test
    void getById_success() {
        Article article = Article.builder().id(42L).build();
        Mockito.when(articleRepository.findById(42L)).thenReturn(Optional.of(article));
        Mockito.when(articleMapper.toDto(any(Article.class))).thenReturn(new ArticleDto());

        ArticleDto result = articleService.getById(42L);

        Assertions.assertNotNull(result);
    }

    @Test
    void getById_throwsArticleNotFound() {
        Mockito.when(articleRepository.findById(42L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ArticleNotFoundException.class, () -> articleService.getById(42L));
    }

    @Test
    void delete_success() {
        Mockito.when(articleRepository.existsById(5L)).thenReturn(true);

        articleService.delete(5L);

        Mockito.verify(articleRepository).deleteById(5L);
    }

    @Test
    void delete_throwsArticleNotFound() {
        Mockito.when(articleRepository.existsById(5L)).thenReturn(false);

        Assertions.assertThrows(ArticleNotFoundException.class, () -> articleService.delete(5L));
    }
}