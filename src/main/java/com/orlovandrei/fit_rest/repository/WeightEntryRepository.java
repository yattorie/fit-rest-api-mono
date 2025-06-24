package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.weight.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeightEntryRepository extends JpaRepository<WeightEntry, Long> {
    List<WeightEntry> findByUserUsernameOrderByDateAsc(String username);
}

