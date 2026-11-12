package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.SleepTestData.exampleSessions;

class SessionAverageAnalysisTest {

    @Test
    void averageReturnsMeanDuration() {
        var result = new SessionAverageAnalysis().apply(exampleSessions());

        assertEquals(271.0 / 3, result.getValue(), 0.000001);
    }

    @Test
    void averageReturnsZeroForEmptyList() {
        var result = new SessionAverageAnalysis().apply(List.of());

        assertEquals(0.0, result.getValue(), 0.000001);
    }
}
