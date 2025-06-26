package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.weight.AddWeightEntryRequest;
import com.orlovandrei.fit_rest.dto.weight.WeightEntryDto;
import com.orlovandrei.fit_rest.service.WeightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weight")
@Tag(name = "Weight Controller", description = "Operations for managing weight tracking")
public class WeightController {

    private final WeightService weightService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Add weight entry")
    public ResponseEntity<Void> add(
            @RequestBody @Valid AddWeightEntryRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        weightService.addEntry(userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get weight history")
    public ResponseEntity<List<WeightEntryDto>> get(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(weightService.getHistory(userDetails.getUsername()));
    }
}
