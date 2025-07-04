package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.entity.Article;
import com.orlovandrei.fit_rest.entity.Recipe;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.repository.ArticleRepository;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MinioServiceImplTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private MinioServiceImpl minioService;

    private void setupMinioProps() {
        ReflectionTestUtils.setField(minioService, "bucketName", "bucket");
        ReflectionTestUtils.setField(minioService, "minioUrl", "http://minio");
    }

    @Test
    void uploadRecipeImage_success() throws Exception {
        setupMinioProps();
        Recipe recipe = Recipe.builder().id(1L).build();
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.png");
        Mockito.when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3}));
        Mockito.when(multipartFile.getSize()).thenReturn(3L);
        Mockito.when(multipartFile.getContentType()).thenReturn("image/png");
        Mockito.when(minioClient.putObject(any(PutObjectArgs.class))).thenReturn(null);
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        String url = minioService.uploadRecipeImage(multipartFile, 1L);

        Assertions.assertTrue(url.contains("http://minio/bucket/recipes/1/"));
        Mockito.verify(minioClient).putObject(any(PutObjectArgs.class));
        Mockito.verify(recipeRepository).save(recipe);
    }

    @Test
    void uploadRecipeImage_throwsRecipeNotFound() {
        setupMinioProps();
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(RecipeNotFoundException.class, () -> minioService.uploadRecipeImage(multipartFile, 1L));
    }

    @Test
    void uploadRecipeImage_uploadError() throws Exception {
        setupMinioProps();
        Recipe recipe = Recipe.builder().id(1L).build();
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.png");
        Mockito.when(multipartFile.getInputStream()).thenThrow(new RuntimeException("IO error"));
        Assertions.assertThrows(RuntimeException.class, () -> minioService.uploadRecipeImage(multipartFile, 1L));
    }

    @Test
    void uploadArticleImage_success() throws Exception {
        setupMinioProps();
        Article article = Article.builder().id(2L).build();
        Mockito.when(articleRepository.findById(2L)).thenReturn(Optional.of(article));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test2.png");
        Mockito.when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{4, 5, 6}));
        Mockito.when(multipartFile.getSize()).thenReturn(3L);
        Mockito.when(multipartFile.getContentType()).thenReturn("image/png");
        Mockito.when(minioClient.putObject(any(PutObjectArgs.class))).thenReturn(null);
        Mockito.when(articleRepository.save(any(Article.class))).thenReturn(article);

        String url = minioService.uploadArticleImage(multipartFile, 2L);

        Assertions.assertTrue(url.contains("http://minio/bucket/articles/2/"));
        Mockito.verify(minioClient).putObject(any(PutObjectArgs.class));
        Mockito.verify(articleRepository).save(article);
    }

    @Test
    void uploadArticleImage_throwsArticleNotFound() {
        setupMinioProps();
        Mockito.when(articleRepository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ArticleNotFoundException.class, () -> minioService.uploadArticleImage(multipartFile, 2L));
    }

    @Test
    void uploadArticleImage_uploadError() throws Exception {
        setupMinioProps();
        Article article = Article.builder().id(2L).build();
        Mockito.when(articleRepository.findById(2L)).thenReturn(Optional.of(article));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("fail.png");
        Mockito.when(multipartFile.getInputStream()).thenThrow(new RuntimeException("IO error"));
        Assertions.assertThrows(RuntimeException.class, () -> minioService.uploadArticleImage(multipartFile, 2L));
    }
}