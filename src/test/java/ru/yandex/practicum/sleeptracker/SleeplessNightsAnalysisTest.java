package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.SleepTestData.session;

class SleeplessNightsAnalysisTest {

    @Test
    void sleeplessReturnsZeroForEmptyList() {
        var result = new SleeplessNightsAnalysis().apply(List.of());

        assertEquals(Long.valueOf(0), result.getValue());
    }

    @Test
    void sleepStartingAtSixDoesNotCountAsNightSleep() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T06:00", "2025-10-01T08:00")
        );

        var result = new SleeplessNightsAnalysis().apply(sessions);

        assertEquals(Long.valueOf(1), result.getValue());
    }

    @Test
    void countsMissingNightAcrossMonthBoundary() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-31T23:00", "2025-11-01T08:00"),
                session("2025-11-02T23:00", "2025-11-03T08:00")
        );

        var result = new SleeplessNightsAnalysis().apply(sessions);

        assertEquals(Long.valueOf(1), result.getValue());
    }

    @Test
    void severalSessionsInOneNightDoNotCreateSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:00", "2025-10-02T02:00"),
                session("2025-10-02T03:00", "2025-10-02T08:00")
        );

        var result = new SleeplessNightsAnalysis().apply(sessions);

        assertEquals(Long.valueOf(0), result.getValue());
    }
}
