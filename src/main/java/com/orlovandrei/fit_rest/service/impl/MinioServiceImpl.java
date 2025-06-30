package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.entity.article.Article;
import com.orlovandrei.fit_rest.entity.recipe.Recipe;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.repository.ArticleRepository;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import com.orlovandrei.fit_rest.service.MinioService;
import com.orlovandrei.fit_rest.util.Messages;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    private final MinioClient minioClient;
    private final RecipeRepository recipeRepository;
    private final ArticleRepository articleRepository;

    @Override
    public String uploadRecipeImage(MultipartFile file, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + recipeId));

        String filename = "recipes/" + recipeId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String fileUrl = minioUrl + "/" + bucketName + "/" + filename;
            recipe.setImageUrl(fileUrl);
            recipeRepository.save(recipe);

            return fileUrl;

        } catch (Exception e) {
            throw new RuntimeException(Messages.IMAGE_UPLOAD_FAILED.getMessage(), e);
        }
    }

    @Override
    public String uploadArticleImage(MultipartFile file, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(Messages.ARTICLE_NOT_FOUND_BY_ID.getMessage() + articleId));

        String filename = "articles/" + articleId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String fileUrl = minioUrl + "/" + bucketName + "/" + filename;
            article.setImageUrl(fileUrl);
            articleRepository.save(article);

            return fileUrl;

        } catch (Exception e) {
            throw new RuntimeException(Messages.IMAGE_UPLOAD_FAILED.getMessage(), e);
        }
    }

    @Override
    public void deleteArticleImage(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(Messages.ARTICLE_NOT_FOUND_BY_ID.getMessage() + articleId));

        deleteImageFromMinio(article.getImageUrl());
        article.setImageUrl(null);
        articleRepository.save(article);
    }

    @Override
    public void deleteRecipeImage(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + recipeId));

        deleteImageFromMinio(recipe.getImageUrl());
        recipe.setImageUrl(null);
        recipeRepository.save(recipe);
    }

    private void deleteImageFromMinio(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }

        try {
            String baseUrl = minioUrl + "/" + bucketName + "/";
            String objectName = imageUrl.replace(baseUrl, "");

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(Messages.IMAGE_DELETE_FAILED.getMessage(), e);
        }
    }

}

