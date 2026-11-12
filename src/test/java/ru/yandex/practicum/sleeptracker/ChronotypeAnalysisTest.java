package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.SleepTestData.session;

class ChronotypeAnalysisTest {

    @Test
    void lateSleepIncludingAfterMidnightReturnsOwl() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:30", "2025-10-02T10:00"),
                session("2025-10-03T01:00", "2025-10-03T10:00")
        );

        var result = new ChronotypeAnalysis().apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    void earlySleepReturnsLark() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T21:00", "2025-10-02T06:00")
        );

        var result = new ChronotypeAnalysis().apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    void exactTimeBoundariesReturnDove() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:00", "2025-10-02T09:00"),
                session("2025-10-02T22:00", "2025-10-03T07:00")
        );

        var result = new ChronotypeAnalysis().apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    void equalNumberOfOwlsAndLarksReturnsDove() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:30", "2025-10-02T10:00"),
                session("2025-10-02T21:00", "2025-10-03T06:00")
        );

        var result = new ChronotypeAnalysis().apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    void ignoresDaytimeSleepAndSleeplessNights() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:30", "2025-10-02T10:00"),
                session("2025-10-02T14:00", "2025-10-02T15:00"),
                session("2025-10-04T14:00", "2025-10-04T15:00")
        );

        var result = new ChronotypeAnalysis().apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    void fragmentedNightGivesOneVote() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:30", "2025-10-02T02:00"),
                session("2025-10-02T03:00", "2025-10-02T10:00"),
                session("2025-10-02T21:00", "2025-10-03T06:00")
        );

        var result = new ChronotypeAnalysis().apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    void chronotypeReturnsDoveForEmptyList() {
        var result = new ChronotypeAnalysis().apply(List.of());

        assertEquals(Chronotype.DOVE, result.getValue());
    }
}
