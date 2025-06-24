package com.orlovandrei.fit_rest.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String uploadRecipeImage(MultipartFile file, Long recipeId);
    String uploadArticleImage(MultipartFile file, Long articleId);

}

