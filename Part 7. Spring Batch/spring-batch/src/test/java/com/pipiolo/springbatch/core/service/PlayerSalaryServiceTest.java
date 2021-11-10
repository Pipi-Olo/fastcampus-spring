package com.pipiolo.springbatch.core.service;

import com.pipiolo.springbatch.dto.PlayerDto;
import com.pipiolo.springbatch.dto.PlayerSalaryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Year;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerSalaryServiceTest {

    private PlayerSalaryService playerSalaryService;

    @BeforeEach
    public void setup() {
        this.playerSalaryService = new PlayerSalaryService();
    }

    @Test
    void calcSalary() {
        // Given
        MockedStatic<Year> mockYearClass = Mockito.mockStatic(Year.class);
        Year mockYear = mock(Year.class);
        when(mockYear.getValue()).thenReturn(2021);
        mockYearClass.when(Year::now).thenReturn(mockYear);

        PlayerDto mockPlayer = mock(PlayerDto.class);
        when(mockPlayer.getBirthYear()).thenReturn(1980);

        // When
        PlayerSalaryDto result = playerSalaryService.calcSalary(mockPlayer);

        // Then
        Assertions.assertEquals(result.getSalary(), (2021 - 1980) * 1000000);
    }
}