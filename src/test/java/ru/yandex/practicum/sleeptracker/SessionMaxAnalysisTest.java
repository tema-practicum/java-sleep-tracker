package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.SleepTestData.exampleSessions;

class SessionMaxAnalysisTest {

    @Test
    void maxReturnsLongestDuration() {
        var result = new SessionMaxAnalysis().apply(exampleSessions());

        assertEquals(Long.valueOf(120), result.getValue());
    }

    @Test
    void maxReturnsZeroForEmptyList() {
        var result = new SessionMaxAnalysis().apply(List.of());

        assertEquals(Long.valueOf(0), result.getValue());
    }
}
