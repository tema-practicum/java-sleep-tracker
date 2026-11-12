package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime startOfSleep;
    private final LocalDateTime endOfSleep;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime startOfSleep, LocalDateTime endOfSleep, SleepQuality sleepQuality) {
        this.startOfSleep = startOfSleep;
        this.endOfSleep = endOfSleep;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStartOfSleep() {
        return startOfSleep;
    }

    public LocalDateTime getEndOfSleep() {
        return endOfSleep;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}
