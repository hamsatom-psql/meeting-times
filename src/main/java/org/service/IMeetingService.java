package org.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface IMeetingService {
    @Nonnull
    default List<ZonedDateTime> findAvailableTime(@Nonnull List<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Period periodToSearch) {
        return findAvailableTime(calendarIds, duration, periodToSearch, null);
    }

    @Nonnull
    List<ZonedDateTime> findAvailableTime(@Nonnull List<UUID> calendarIds, @Nonnull Duration duration, @Nonnull Period periodToSearch, @Nullable UUID timeSlotType);
}
