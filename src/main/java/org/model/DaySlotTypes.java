package org.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.UUID;

public class DaySlotTypes {
    private final UUID[] slotTypes = new UUID[1440]; // one slot type for every minute of day

    public void setAvailableTypes(@Nonnull LocalTime start, @Nonnull LocalTime end, @Nonnull UUID type) {
        setInternal(start, end, type);
    }

    public void takeSlots(@Nonnull LocalTime start, @Nonnull LocalTime end) {
        setInternal(start, end, null);
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
