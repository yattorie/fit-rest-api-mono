package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.dto.article.CreateArticleRequest;
import com.orlovandrei.fit_rest.dto.article.UpdateArticleRequest;
import com.orlovandrei.fit_rest.service.ArticleService;
import com.orlovandrei.fit_rest.service.MinioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private UserDetails userDetails;

    @Test
    void create_success() {
        CreateArticleRequest req = new CreateArticleRequest();
        ArticleDto dto = new ArticleDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(articleService.create(req, "user")).thenReturn(dto);

        ResponseEntity<ArticleDto> result = articleController.create(req, userDetails);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void getAll_success() {
        ArticleDto dto = new ArticleDto();
        Page<ArticleDto> page = new PageImpl<>(List.of(dto));
        Mockito.when(articleService.getAll(0, 10)).thenReturn(page);

        ResponseEntity<Page<ArticleDto>> result = articleController.getAll(0, 10);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
    }

    @Test
    void getById_success() {
        ArticleDto dto = new ArticleDto();
        Mockito.when(articleService.getById(1L)).thenReturn(dto);

        ResponseEntity<ArticleDto> result = articleController.getById(1L);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void delete_success() {
        Mockito.doNothing().when(articleService).delete(1L);

        ResponseEntity<Void> result = articleController.delete(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(articleService).delete(1L);
    }

    @Test
    void uploadImage_success() {
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(minioService.uploadArticleImage(file, 1L)).thenReturn("url");

        ResponseEntity<String> result = articleController.uploadImage(1L, file);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("url", result.getBody());
    }

    @Test
    void update_success() {
        UpdateArticleRequest req = new UpdateArticleRequest();
        ArticleDto dto = new ArticleDto();
        Mockito.when(articleService.update(2L, req)).thenReturn(dto);

        ResponseEntity<ArticleDto> result = articleController.update(2L, req);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }
}