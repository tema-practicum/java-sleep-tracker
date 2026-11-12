package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;


public class SessionMinAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {

        return new SleepAnalysisResult<>("Минимальная длительность сессии",
                sessions.stream()
                        .mapToLong(session -> Duration.between(
                                session.getStartOfSleep(),
                                session.getEndOfSleep()).toMinutes())
                        .min()
                        .orElse(0L));
    }
}
