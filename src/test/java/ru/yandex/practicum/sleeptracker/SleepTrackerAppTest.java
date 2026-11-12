package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerAppTest {

    private SleepingSession session(
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

    private SleepingSession session(String start, String end) {
        return session(start, end, SleepQuality.NORMAL);
    }

    private List<SleepingSession> exampleSessions() {
        return List.of(
                session("2025-10-01T23:30", "2025-10-02T01:30",
                        SleepQuality.GOOD),
                session("2025-10-02T14:00", "2025-10-02T15:00",
                        SleepQuality.BAD),
                session("2025-10-03T01:00", "2025-10-03T02:31",
                        SleepQuality.BAD)
        );
    }

    @Test
    void countReturnsNumberOfSessions() {
        var result = new SessionCountAnalysis().apply(exampleSessions());

        assertEquals(Integer.valueOf(3), result.getValue());
    }

    @Test
    void countReturnsZeroForEmptyList() {
        var result = new SessionCountAnalysis().apply(List.of());

        assertEquals(Integer.valueOf(0), result.getValue());
    }

    @Test
    void minReturnsShortestDuration() {
        var result = new SessionMinAnalysis().apply(exampleSessions());

        assertEquals(Long.valueOf(60), result.getValue());
    }

    @Test
    void minReturnsZeroForEmptyList() {
        var result = new SessionMinAnalysis().apply(List.of());

        assertEquals(Long.valueOf(0), result.getValue());
    }

    @Test
    void maxReturnsLongestDuration() {
        var result = new SessionMaxAnalysis().apply(exampleSessions());

        assertEquals(Long.valueOf(120), result.getValue());
    }

    @Test
    void maxReturnsZeroForEmptyList() {
        var result = new SessionMaxAnalysis().apply(List.of());

        assertEquals(Long.valueOf(0), result.getValue());
    }

    @Test
    void averageReturnsMeanDuration() {
        var result = new SessionAverageAnalysis().apply(exampleSessions());

        assertEquals(271.0 / 3, result.getValue(), 0.000001);
    }

    @Test
    void averageReturnsZeroForEmptyList() {
        var result = new SessionAverageAnalysis().apply(List.of());

        assertEquals(0.0, result.getValue(), 0.000001);
    }

    @Test
    void badCountCountsOnlyBadSessions() {
        var result = new SessionBadAnalysis().apply(exampleSessions());

        assertEquals(Long.valueOf(2), result.getValue());
    }

    @Test
    void badCountReturnsZeroWhenNoBadSessions() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T22:00", "2025-10-02T06:00",
                        SleepQuality.GOOD),
                session("2025-10-02T23:00", "2025-10-03T06:00",
                        SleepQuality.NORMAL)
        );

        var result = new SessionBadAnalysis().apply(sessions);

        assertEquals(Long.valueOf(0), result.getValue());
    }

    @Test
    void sleeplessReturnsZeroForEmptyList() {
        var result = new SleeplessNightsAnalysis().apply(List.of());

        assertEquals(Long.valueOf(0), result.getValue());
    }

    @Test
    void sleepStartingAtSixDoesNotCountAsNightSleep() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T06:00", "2025-10-01T08:00")
        );

        var result = new SleeplessNightsAnalysis().apply(sessions);

        assertEquals(Long.valueOf(1), result.getValue());
    }

    @Test
    void countsMissingNightAcrossMonthBoundary() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-31T23:00", "2025-11-01T08:00"),
                session("2025-11-02T23:00", "2025-11-03T08:00")
        );

        var result = new SleeplessNightsAnalysis().apply(sessions);

        assertEquals(Long.valueOf(1), result.getValue());
    }

    @Test
    void severalSessionsInOneNightDoNotCreateSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:00", "2025-10-02T02:00"),
                session("2025-10-02T03:00", "2025-10-02T08:00")
        );

        var result = new SleeplessNightsAnalysis().apply(sessions);

        assertEquals(Long.valueOf(0), result.getValue());
    }

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