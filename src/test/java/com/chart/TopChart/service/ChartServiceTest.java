package com.chart.TopChart.service;

import com.chart.TopChart.data.dto.ChartStats;
import com.chart.TopChart.data.model.Position;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChartServiceTest {

    @Test
    void shouldCalculateHighestClimb() {
        Position first = createPosition(5, 20);
        Position second = createPosition(10, 14);

        ChartStats result = ChartService.calculateStats(
                Arrays.asList(first, second),
                Arrays.asList(3L, 2L)
        );

        assertEquals(15, result.getHighestClimb());
    }

    @Test
    void shouldCalculateBiggestFall() {
        Position first = createPosition(18, 4);
        Position second = createPosition(25, 20);

        ChartStats result = ChartService.calculateStats(
                Arrays.asList(first, second),
                Arrays.asList(3L, 2L)
        );

        assertEquals(14, result.getBiggestFall());
    }

    @Test
    void shouldCountNewEntries() {
        Position position = createPosition(12, null);

        ChartStats result = ChartService.calculateStats(
                Collections.singletonList(position),
                Collections.singletonList(1L)
        );

        assertEquals(1, result.getNewEntries());
        assertEquals(0, result.getReEntries());
    }

    @Test
    void shouldCountReEntries() {
        Position position = createPosition(15, null);

        ChartStats result = ChartService.calculateStats(
                Collections.singletonList(position),
                Collections.singletonList(4L)
        );

        assertEquals(0, result.getNewEntries());
        assertEquals(1, result.getReEntries());
    }

    @Test
    void shouldReturnZerosForEmptyChart() {
        ChartStats result = ChartService.calculateStats(
                Collections.emptyList(),
                Collections.emptyList()
        );

        assertEquals(0, result.getHighestClimb());
        assertEquals(0, result.getBiggestFall());
        assertEquals(0, result.getNewEntries());
        assertEquals(0, result.getReEntries());
    }

    private Position createPosition(int currentPosition, Integer lastWeek) {
        Position position = new Position();
        position.setPosition(currentPosition);
        position.setLastWeek(lastWeek);
        return position;
    }
}