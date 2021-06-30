package org.service;

import org.model.Calendar;
import org.model.Iso8601TimeInterval;
import org.repository.ICalendarRepository;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class DefaultMeetingService implements IMeetingService {
    private final ICalendarRepository calendarRepository;

    public DefaultMeetingService(@Nonnull ICalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Nonnull
    @Override
    public List<LocalDateTime> findAvailableTime(@Nonnull Set<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Iso8601TimeInterval periodToSearch) {
        if (periodToSearch.getStart().isAfter(periodToSearch.getEnd())) {
            throw new IllegalArgumentException("Start after end - " + periodToSearch.getStart() + " < " + periodToSearch.getEnd());
        }

        List<Calendar> calendars = getCalendars(calendarIds);
        List<LocalDateTime> availableTimes = new ArrayList<>();
        for (LocalDateTime start = periodToSearch.getStart(); start.isBefore(periodToSearch.getEnd().plus(duration)); start = start.plusMinutes(1)) {
            LocalDateTime finalStart = start;
            boolean fitsToAllCalendars = calendars.stream().allMatch(calendar -> calendar.isPeriodAvailable(finalStart, finalStart.plus(duration)));
            if (fitsToAllCalendars) {
                availableTimes.add(finalStart);
            }
        }

        return availableTimes;
    }

    @Nonnull
    @Override
    public List<LocalDateTime> findAvailableTime(@Nonnull Set<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Iso8601TimeInterval periodToSearch, @Nonnull UUID timeSlotType) {
        if (periodToSearch.getStart().isAfter(periodToSearch.getEnd())) {
            throw new IllegalArgumentException("Start after end - " + periodToSearch.getStart() + " < " + periodToSearch.getEnd());
        }

        List<Calendar> calendars = getCalendars(calendarIds);
        List<LocalDateTime> availableTimes = new ArrayList<>();
        for (LocalDateTime start = periodToSearch.getStart(); start.isBefore(periodToSearch.getEnd().plus(duration)); start = start.plusMinutes(1)) {
            LocalDateTime finalStart = start;
            boolean fitsToAllCalendars = calendars.stream().allMatch(calendar -> calendar.isTypeAvailable(finalStart, finalStart.plus(duration), timeSlotType));
            if (fitsToAllCalendars) {
                availableTimes.add(finalStart);
            }
        }

        return availableTimes;
    }

    private List<Calendar> getCalendars(Set<UUID> calendarIds) {
        List<Calendar> calendars = new ArrayList<>(calendarIds.size());
        for (UUID calendarId : calendarIds) {
            Optional<Calendar> calendar = calendarRepository.selectOne(calendarId);
            if (!calendar.isPresent()) {
                throw new IllegalArgumentException("No calendar with id " + calendarId);
            }
            calendars.add(calendar.get());
        }
        return calendars;
    }
}
