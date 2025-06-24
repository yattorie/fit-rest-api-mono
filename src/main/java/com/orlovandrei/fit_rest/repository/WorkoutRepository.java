package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {}
