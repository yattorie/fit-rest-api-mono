package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategoryId(Long categoryId);
}

