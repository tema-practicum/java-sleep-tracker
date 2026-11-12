package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.SleepTestData.exampleSessions;

class SessionMinAnalysisTest {

    @Test
    void minReturnsShortestDuration() {
        var result = new SessionMinAnalysis().apply(exampleSessions());

        assertEquals(Long.valueOf(60), result.getValue());
    }

    @Test
    void minReturnsZeroForEmptyList() {
        var result = new SessionMinAnalysis().apply(List.of());

        assertEquals(Long.valueOf(0), result.getValue());
    }
}
