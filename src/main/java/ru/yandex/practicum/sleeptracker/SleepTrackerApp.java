package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    private static final List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> analyses =
            List.of(
                    new SessionCountAnalysis(),
                    new SessionMinAnalysis(),
                    new SessionMaxAnalysis(),
                    new SessionAverageAnalysis(),
                    new SessionBadAnalysis(),
                    new SleeplessNightsAnalysis(),
                    new ChronotypeAnalysis()
            );

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу");
            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            Path path = Path.of(args[0]);
            List<String> lines = Files.readAllLines(path);
            List<SleepingSession> sessions = lines.stream()
                    .map(session -> {
                        String[] parts = session.split(";");
                        LocalDateTime start = formatter.parse(parts[0].trim(), LocalDateTime::from);
                        LocalDateTime end = formatter.parse(parts[1].trim(), LocalDateTime::from);
                        SleepQuality sleepQuality = SleepQuality.valueOf(parts[2].trim());
                        return new SleepingSession(start, end, sleepQuality);
                    })
                    .toList();
            analyses.forEach(analysis -> {
                SleepAnalysisResult<?> result = analysis.apply(sessions);
                System.out.println(result.getDescription() + ": " + result.getValue());
            });
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл: " + e.getMessage());
        }
    }
}