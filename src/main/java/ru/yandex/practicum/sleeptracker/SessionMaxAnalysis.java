package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;


public class SessionMaxAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {

        return new SleepAnalysisResult<>("Максимальная длительность сессии",
                sessions.stream()
                        .mapToLong(session -> Duration.between(
                                session.getStartOfSleep(),
                                session.getEndOfSleep()).toMinutes())
                        .max()
                        .orElse(0L));
    }
}
