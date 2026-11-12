package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.SleepTestData.exampleSessions;
import static ru.yandex.practicum.sleeptracker.SleepTestData.session;

class SessionBadAnalysisTest {

    @Test
    void badCountCountsOnlyBadSessions() {
        var result = new SessionBadAnalysis().apply(exampleSessions());

        assertEquals(Long.valueOf(2), result.getValue());
    }

    @Test
    void badCountReturnsZeroWhenNoBadSessions() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T22:00", "2025-10-02T06:00",
                        SleepQuality.GOOD),
                session("2025-10-02T23:00", "2025-10-03T06:00",
                        SleepQuality.NORMAL)
        );

        var result = new SessionBadAnalysis().apply(sessions);

        assertEquals(Long.valueOf(0), result.getValue());
    }
}
