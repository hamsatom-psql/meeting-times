package org.service;

import org.repository.ICalendarRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class DefaultMeetingService implements IMeetingService {
    private final ICalendarRepository calendarRepository;

    public DefaultMeetingService(@Nonnull ICalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Nonnull
    @Override
    public List<ZonedDateTime> findAvailableTime(@Nonnull List<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Period periodToSearch, @Nullable UUID timeSlotType) {
        return null;
    }
}
