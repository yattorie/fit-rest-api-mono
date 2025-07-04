package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeightEntryRepository extends JpaRepository<WeightEntry, Long> {
    List<WeightEntry> findByUserUsernameOrderByDateAsc(String username);

    boolean existsByUserUsernameAndDate(String username, LocalDate date);
}

