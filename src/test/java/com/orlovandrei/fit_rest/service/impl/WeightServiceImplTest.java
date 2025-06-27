package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.WeightMapper;
import com.orlovandrei.fit_rest.dto.weight.AddWeightEntryRequest;
import com.orlovandrei.fit_rest.dto.weight.WeightEntryDto;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.entity.weight.WeightEntry;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.repository.WeightEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WeightServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WeightEntryRepository weightEntryRepository;

    @Mock
    private WeightMapper weightMapper;

    @InjectMocks
    private WeightServiceImpl weightService;

    @Test
    void addEntry_success() {
        AddWeightEntryRequest request = new AddWeightEntryRequest();
        request.setDate(LocalDate.now());
        request.setValue(75.0);
        User user = User.builder().username("user").build();
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(weightEntryRepository.save(any(WeightEntry.class))).thenAnswer(inv -> inv.getArgument(0));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        weightService.addEntry("user", request);

        Assertions.assertEquals(75.0, user.getWeight());
        Mockito.verify(weightEntryRepository).save(any(WeightEntry.class));
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void addEntry_throwsUsernameNotFound() {
        AddWeightEntryRequest request = new AddWeightEntryRequest();
        Mockito.when(userRepository.findByUsername("nobody")).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> weightService.addEntry("nobody", request));
    }

    @Test
    void getHistory_success() {
        WeightEntry entry = WeightEntry.builder().date(LocalDate.now()).value(77.0).build();
        Mockito.when(weightEntryRepository.findByUserUsernameOrderByDateAsc("user")).thenReturn(List.of(entry));
        WeightEntryDto dto = new WeightEntryDto();
        Mockito.when(weightMapper.toDto(entry)).thenReturn(dto);

        List<WeightEntryDto> result = weightService.getHistory("user");
        Assertions.assertEquals(1, result.size());
        Assertions.assertSame(dto, result.get(0));
    }
}
