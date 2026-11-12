package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;


public class SessionBadAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {

        return new SleepAnalysisResult<>("Количество плохих сессий сна",
                sessions.stream().filter(session -> session.getSleepQuality() == SleepQuality.BAD).count());
    }
}
