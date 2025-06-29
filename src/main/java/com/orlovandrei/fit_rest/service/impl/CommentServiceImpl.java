package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.comment.CommentDto;
import com.orlovandrei.fit_rest.dto.comment.CreateCommentRequest;
import com.orlovandrei.fit_rest.dto.comment.UpdateCommentRequest;
import com.orlovandrei.fit_rest.dto.mapper.CommentMapper;
import com.orlovandrei.fit_rest.entity.article.Article;
import com.orlovandrei.fit_rest.entity.article.Comment;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.CommentNotFoundException;
import com.orlovandrei.fit_rest.repository.ArticleRepository;
import com.orlovandrei.fit_rest.repository.CommentRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.service.CommentService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto create(CreateCommentRequest request, Long articleId, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(Messages.ARTICLE_NOT_FOUND_BY_ID.getMessage()));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .author(author)
                .article(article)
                .build();

        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(Messages.COMMENT_NOT_FOUND_BY_ID.getMessage() + id));
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public CommentDto update(Long id, UpdateCommentRequest request, String username) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(Messages.COMMENT_NOT_FOUND_BY_ID.getMessage() + id));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.EDIT_ONLY_YOUR_COMMENT.getMessage());
        }

        comment.setContent(request.getContent());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void delete(Long id, String username) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(Messages.COMMENT_NOT_FOUND_BY_ID.getMessage() + id));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.DELETE_ONLY_YOUR_COMMENT.getMessage());
        }

        commentRepository.delete(comment);
    }

}



