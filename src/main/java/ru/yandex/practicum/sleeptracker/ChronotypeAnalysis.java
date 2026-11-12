package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ChronotypeAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult<Chronotype>> {
    @Override
    public SleepAnalysisResult<Chronotype> apply(
            List<SleepingSession> sessions
    ) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Хронотип", Chronotype.DOVE);
        }

        LocalDateTime start = sessions.get(0).getStartOfSleep();
        LocalDateTime end = sessions.get(sessions.size() - 1).getEndOfSleep();

        LocalDate firstNight = start.toLocalDate();

        if (start.getHour() >= 12) {
            firstNight = firstNight.plusDays(1);
        }

        List<Chronotype> types = Stream.iterate(
                        firstNight,
                        date -> date.atStartOfDay().isBefore(end),
                        date -> date.plusDays(1))
                .flatMap(date -> {
                    List<SleepingSession> night = sessions.stream()
                            .filter(s ->
                                    s.getStartOfSleep().isBefore(date.atTime(6, 0))
                                            && s.getEndOfSleep()
                                            .isAfter(date.atStartOfDay()))
                            .toList();

                    if (night.isEmpty()) {
                        return Stream.<Chronotype>empty();
                    }

                    LocalDateTime bedtime = night.get(0).getStartOfSleep();
                    LocalDateTime wakeUp =
                            night.get(night.size() - 1).getEndOfSleep();

                    Chronotype type = Chronotype.DOVE;

                    if (bedtime.isAfter(date.minusDays(1).atTime(23, 0))
                            && wakeUp.isAfter(date.atTime(9, 0))) {
                        type = Chronotype.OWL;
                    } else if (bedtime.isBefore(date.minusDays(1).atTime(22, 0))
                            && wakeUp.isBefore(date.atTime(7, 0))) {
                        type = Chronotype.LARK;
                    }

                    return Stream.of(type);
                })
                .toList();

        int owls = Collections.frequency(types, Chronotype.OWL);
        int larks = Collections.frequency(types, Chronotype.LARK);
        int doves = Collections.frequency(types, Chronotype.DOVE);

        Chronotype result = Chronotype.DOVE;

        if (owls > larks && owls > doves) {
            result = Chronotype.OWL;
        } else if (larks > owls && larks > doves) {
            result = Chronotype.LARK;
        }

        return new SleepAnalysisResult<>("Хронотип", result);
    }
}
