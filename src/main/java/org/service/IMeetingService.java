package org.service;

import org.model.Iso8601TimeInterval;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IMeetingService {
    @Nonnull
    default List<ZonedDateTime> findAvailableTime(@Nonnull Set<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Iso8601TimeInterval periodToSearch) {
        return findAvailableTime(calendarIds, duration, periodToSearch, null);
    }

    @Nonnull
    List<ZonedDateTime> findAvailableTime(@Nonnull Set<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Iso8601TimeInterval periodToSearch, @Nullable UUID timeSlotType);
}
