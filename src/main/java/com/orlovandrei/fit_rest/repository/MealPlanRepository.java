package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.meal.MealPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    Page<MealPlan> findByUserUsername(String username, Pageable pageable);
}
