package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;


public class SleeplessNightsAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0L);
        }

        LocalDateTime start = sessions.getFirst().getStartOfSleep();
        LocalDateTime end = sessions.getLast().getEndOfSleep();

        LocalDate firstNight = start.toLocalDate();

        if (start.getHour() >= 12) {
            firstNight = firstNight.plusDays(1);
        }

        long count = Stream.iterate(
                        firstNight,
                        date -> date.atStartOfDay().isBefore(end),
                        date -> date.plusDays(1))
                .filter(date -> sessions.stream().noneMatch(session ->
                        session.getStartOfSleep().isBefore(date.atTime(6, 0))
                                && session.getEndOfSleep().isAfter(date.atStartOfDay())))
                .count();

        return new SleepAnalysisResult<>("Количество бессонных ночей", count);
    }
}
