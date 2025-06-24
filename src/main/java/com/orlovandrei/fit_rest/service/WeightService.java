package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.weight.AddWeightEntryRequest;
import com.orlovandrei.fit_rest.dto.weight.WeightEntryDto;

import java.util.List;

public interface WeightService {
    void addEntry(String username, AddWeightEntryRequest request);
    List<WeightEntryDto> getHistory(String username);
}

