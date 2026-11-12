package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.util.List;

final class SleepTestData {

    private SleepTestData() {
    }

    static SleepingSession session(
            String start,
            String end,
            SleepQuality quality
    ) {
        return new SleepingSession(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end),
                quality
        );
    }

    static SleepingSession session(String start, String end) {
        return session(start, end, SleepQuality.NORMAL);
    }

    static List<SleepingSession> exampleSessions() {
        return List.of(
                session("2025-10-01T23:30", "2025-10-02T01:30",
                        SleepQuality.GOOD),
                session("2025-10-02T14:00", "2025-10-02T15:00",
                        SleepQuality.BAD),
                session("2025-10-03T01:00", "2025-10-03T02:31",
                        SleepQuality.BAD)
        );
    }

}
