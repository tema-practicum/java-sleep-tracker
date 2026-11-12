package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;


public class SessionAverageAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {

    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sessions) {

        return new SleepAnalysisResult<>("Средняя длительность сессии",
                sessions.stream()
                        .mapToLong(session -> Duration.between(
                                session.getStartOfSleep(),
                                session.getEndOfSleep()).toMinutes())
                        .average()
                        .orElse(0));
    }
}
