package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.comment.CommentDto;
import com.orlovandrei.fit_rest.entity.article.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "authorUsername", source = "author.username")
    CommentDto toDto(Comment comment);
}

