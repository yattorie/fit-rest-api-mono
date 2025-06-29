package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.weight.AddWeightEntryRequest;
import com.orlovandrei.fit_rest.dto.weight.WeightEntryDto;
import com.orlovandrei.fit_rest.service.WeightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class WeightControllerTest {

    @Mock
    private WeightService weightService;

    @InjectMocks
    private WeightController weightController;

    @Mock
    private UserDetails userDetails;

    @Test
    void add_success() {
        AddWeightEntryRequest req = new AddWeightEntryRequest();
        Mockito. when(userDetails.getUsername()).thenReturn("user");
        Mockito. doNothing().when(weightService).addEntry("user", req);

        ResponseEntity<Void> result = weightController.add(req, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Mockito.  verify(weightService).addEntry("user", req);
    }

    @Test
    void get_success() {
        WeightEntryDto dto = new WeightEntryDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(weightService.getHistory("user")).thenReturn(List.of(dto));

        ResponseEntity<List<WeightEntryDto>> result = weightController.get(userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().size());
    }
}