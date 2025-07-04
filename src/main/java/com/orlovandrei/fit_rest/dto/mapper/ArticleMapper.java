package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.article.ArticleDto;
import com.orlovandrei.fit_rest.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target = "authorUsername", source = "author.username")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "imageUrl",       source = "imageUrl")
    ArticleDto toDto(Article article);

}

