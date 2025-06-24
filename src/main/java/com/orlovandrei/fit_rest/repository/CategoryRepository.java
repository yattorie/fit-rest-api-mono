package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}

