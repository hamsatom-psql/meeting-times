package org.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class DaySlotTypes {
    private final UUID[] slotTypes = new UUID[1440]; // one slot type for every minute of day

    public void setAvailableTypes(@Nonnull LocalTime start, @Nonnull LocalTime end, @Nonnull UUID type) {
        setInternal(start, end, type);
    }

    public void takeSlots(@Nonnull LocalTime start, @Nonnull LocalTime end) {
        setInternal(start, end, null);
    }

    public boolean isTypeAvailable(@Nonnull LocalTime start, @Nonnull LocalTime end, @Nonnull UUID type) {
        return noneInPeriodMatch(start, end, uuid -> !type.equals(uuid));
    }

    public boolean isPeriodAvailable(@Nonnull LocalTime start, @Nonnull LocalTime end) {
        return noneInPeriodMatch(start, end, Objects::isNull);
    }

    private boolean noneInPeriodMatch(@Nonnull LocalTime start, @Nonnull LocalTime end, @Nonnull Predicate<UUID> isNotAvailable) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start after end - " + start + " < " + end);
        }

        int startMinute = start.getHour() * 60 + start.getMinute();
        int endMinute = end.getHour() * 60 + end.getMinute();

        if (isNotAvailable.test(slotTypes[startMinute]) || isNotAvailable.test(slotTypes[endMinute - 1])) {
            return false;
        }

        for (int minute = startMinute + 1; minute < endMinute - 2; minute++) {
            if (isNotAvailable.test(slotTypes[minute])) {
                return false;
            }
        }

        return true;
    }

    private void setInternal(@Nonnull LocalTime start, @Nonnull LocalTime end, @Nullable UUID type) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start after end - " + start + " < " + end);
        }

        int startMinute = start.getHour() * 60 + start.getMinute();
        int endMinute = end.getHour() * 60 + end.getMinute();
        for (int minute = startMinute; minute < endMinute; minute++) {
            slotTypes[minute] = type;
        }
    }
}
