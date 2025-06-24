package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.article.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
}
