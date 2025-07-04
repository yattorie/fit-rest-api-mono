package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.category.CategoryDto;
import com.orlovandrei.fit_rest.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto dto);
}

