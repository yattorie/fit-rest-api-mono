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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void create_success() {
        CreateCommentRequest request = new CreateCommentRequest();
        request.setContent("text");
        User user = User.builder().username("user").build();
        Article article = Article.builder().id(1L).build();
        Comment comment = Comment.builder().content("text").author(user).article(article).build();
        CommentDto dto = new CommentDto();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        Mockito.when(commentMapper.toDto(any(Comment.class))).thenReturn(dto);

        CommentDto result = commentService.create(request, 1L, "user");

        Assertions.assertNotNull(result);
        Mockito.verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void create_throwsUsernameNotFound() {
        CreateCommentRequest request = new CreateCommentRequest();
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> commentService.create(request, 1L, "user"));
    }

    @Test
    void create_throwsArticleNotFoundException() {
        CreateCommentRequest request = new CreateCommentRequest();
        User user = User.builder().username("user").build();
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ArticleNotFoundException.class, () -> commentService.create(request, 1L, "user"));
    }

    @Test
    void getByArticleId() {
        Mockito.when(commentRepository.findByArticleId(2L)).thenReturn(List.of(new Comment()));
        Mockito.when(commentMapper.toDto(any(Comment.class))).thenReturn(new CommentDto());
        List<CommentDto> result = commentService.getByArticleId(2L);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getById_success() {
        Comment comment = Comment.builder().id(2L).build();
        Mockito.when(commentRepository.findById(2L)).thenReturn(Optional.of(comment));
        Mockito.when(commentMapper.toDto(any(Comment.class))).thenReturn(new CommentDto());
        CommentDto result = commentService.getById(2L);
        Assertions.assertNotNull(result);
    }

    @Test
    void getById_throwsCommentNotFound() {
        Mockito.when(commentRepository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.getById(2L));
    }

    @Test
    void update_success() {
        User user = User.builder().username("user").build();
        Comment comment = Comment.builder().id(3L).author(user).build();
        UpdateCommentRequest request = new UpdateCommentRequest();
        request.setContent("new");
        Mockito.when(commentRepository.findById(3L)).thenReturn(Optional.of(comment));
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        Mockito.when(commentMapper.toDto(any(Comment.class))).thenReturn(new CommentDto());
        CommentDto result = commentService.update(3L, request, "user");
        Assertions.assertNotNull(result);
    }

    @Test
    void update_forbidden() {
        User user = User.builder().username("other").build();
        Comment comment = Comment.builder().id(4L).author(user).build();
        UpdateCommentRequest request = new UpdateCommentRequest();
        request.setContent("content");
        Mockito.when(commentRepository.findById(4L)).thenReturn(Optional.of(comment));
        Assertions.assertThrows(AccessDeniedException.class,
                () -> commentService.update(4L, request, "notowner"));
    }

    @Test
    void delete_success() {
        User user = User.builder().username("me").build();
        Comment comment = Comment.builder().id(5L).author(user).build();
        Mockito.when(commentRepository.findById(5L)).thenReturn(Optional.of(comment));
        commentService.delete(5L, "me");
        Mockito.verify(commentRepository).delete(comment);
    }

    @Test
    void delete_forbidden() {
        User user = User.builder().username("someone").build();
        Comment comment = Comment.builder().id(6L).author(user).build();
        Mockito.when(commentRepository.findById(6L)).thenReturn(Optional.of(comment));
        Assertions.assertThrows(AccessDeniedException.class,
                () -> commentService.delete(6L, "notowner"));
    }
}