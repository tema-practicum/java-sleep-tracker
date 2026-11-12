package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.SleepTestData.exampleSessions;

class SessionCountAnalysisTest {

    @Test
    void countReturnsNumberOfSessions() {
        var result = new SessionCountAnalysis().apply(exampleSessions());

        assertEquals(Integer.valueOf(3), result.getValue());
    }

    @Test
    void countReturnsZeroForEmptyList() {
        var result = new SessionCountAnalysis().apply(List.of());

        assertEquals(Integer.valueOf(0), result.getValue());
    }
}
