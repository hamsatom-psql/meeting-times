package org.service;

import org.model.Calendar;
import org.model.Iso8601TimeInterval;
import org.repository.ICalendarRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;

public class DefaultMeetingService implements IMeetingService {
    private final ICalendarRepository calendarRepository;

    public DefaultMeetingService(@Nonnull ICalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Nonnull
    @Override
    public List<ZonedDateTime> findAvailableTime(@Nonnull Set<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Iso8601TimeInterval periodToSearch, @Nullable UUID timeSlotType) {
        if (periodToSearch.getStart().isAfter(periodToSearch.getEnd())) {
            throw new IllegalArgumentException("Start after end - " + periodToSearch.getStart() + " < " + periodToSearch.getEnd());
        }

        List<Calendar> calendars = getCalendars(calendarIds);


        return Collections.emptyList();
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
