package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.WeightMapper;
import com.orlovandrei.fit_rest.dto.weight.AddWeightEntryRequest;
import com.orlovandrei.fit_rest.dto.weight.WeightEntryDto;
import com.orlovandrei.fit_rest.entity.User;
import com.orlovandrei.fit_rest.entity.WeightEntry;
import com.orlovandrei.fit_rest.exception.WeightEntryAlreadyExistsException;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.repository.WeightEntryRepository;
import com.orlovandrei.fit_rest.service.WeightService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeightServiceImpl implements WeightService {

    private final UserRepository userRepository;
    private final WeightEntryRepository weightEntryRepository;
    private final WeightMapper weightMapper;

    @Override
    public void addEntry(String username, AddWeightEntryRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage() + username));

        if (weightEntryRepository.existsByUserUsernameAndDate(username, request.getDate())) {
            throw new WeightEntryAlreadyExistsException(Messages.WEIGHT_ALREADY_MEASURED.getMessage());
        }

        WeightEntry entry = WeightEntry.builder()
                .date(request.getDate())
                .value(request.getValue())
                .user(user)
                .build();

        weightEntryRepository.save(entry);

        user.setWeight(request.getValue());
        userRepository.save(user);
    }


    @Override
    public List<WeightEntryDto> getHistory(String username) {
        return weightEntryRepository.findByUserUsernameOrderByDateAsc(username).stream()
                .map(weightMapper::toDto)
                .toList();
    }
}

