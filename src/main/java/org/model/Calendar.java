package org.model;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Calendar {
    private final Map<LocalDate, DaySlotTypes> timeSlots = new HashMap<>();
    private final UUID id;

    public Calendar(@Nonnull UUID id) {
        this.id = id;
    }

    @Nonnull
    public UUID getId() {
        return id;
    }

    public void setAvailableTimeSlots(@Nonnull LocalDateTime start, @Nonnull LocalDateTime end, @Nonnull UUID slotTypeId) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start after end - " + start + " < " + end + " in " + id);
        }

        DaySlotTypes daySlotTypes = timeSlots.computeIfAbsent(start.toLocalDate(), date -> new DaySlotTypes());
        if (start.toLocalDate().isBefore(end.toLocalDate())) {
            daySlotTypes.setAvailableTypes(start.toLocalTime(), LocalTime.MAX, slotTypeId);
            LocalDateTime startOfNextDay = LocalDateTime.of(start.plusDays(1).toLocalDate(), LocalTime.MIN);
            setAvailableTimeSlots(startOfNextDay, end, slotTypeId);
        } else {
            daySlotTypes.setAvailableTypes(start.toLocalTime(), end.toLocalTime(), slotTypeId);
        }
    }

    public void useTimeSlots(@Nonnull LocalDateTime start, @Nonnull LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start after end - " + start + " < " + end + " in " + id);
        }

        DaySlotTypes daySlotTypes = timeSlots.get(start.toLocalDate());
        if (daySlotTypes != null) {
            if (start.toLocalDate().isBefore(end.toLocalDate())) {
                daySlotTypes.takeSlots(start.toLocalTime(), LocalTime.MAX);
                LocalDateTime startOfNextDay = LocalDateTime.of(start.plusDays(1).toLocalDate(), LocalTime.MIN);
                useTimeSlots(startOfNextDay, end);
            } else {
                daySlotTypes.takeSlots(start.toLocalTime(), end.toLocalTime());
            }
        }
    }

    public boolean isPeriodAvailable(@Nonnull LocalDateTime start, @Nonnull LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start after end - " + start + " < " + end + " in " + id);
        }

        DaySlotTypes daySlotTypes = timeSlots.get(start.toLocalDate());
        if (daySlotTypes != null) {
            if (start.toLocalDate().isBefore(end.toLocalDate())) {
                boolean isTodayAvailable = daySlotTypes.isPeriodAvailable(start.toLocalTime(), LocalTime.MAX);
                LocalDateTime startOfNextDay = LocalDateTime.of(start.plusDays(1).toLocalDate(), LocalTime.MIN);
                return isTodayAvailable && isPeriodAvailable(startOfNextDay, end);
            } else {
                return daySlotTypes.isPeriodAvailable(start.toLocalTime(), end.toLocalTime());
            }
        }

        return false;
    }

    public boolean isTypeAvailable(@Nonnull LocalDateTime start, @Nonnull LocalDateTime end, @Nonnull UUID typeId) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start after end - " + start + " < " + end + " in " + id);
        }

        DaySlotTypes daySlotTypes = timeSlots.get(start.toLocalDate());
        if (daySlotTypes != null) {
            if (start.toLocalDate().isBefore(end.toLocalDate())) {
                boolean isTodayAvailable = daySlotTypes.isTypeAvailable(start.toLocalTime(), LocalTime.MAX, typeId);
                LocalDateTime startOfNextDay = LocalDateTime.of(start.plusDays(1).toLocalDate(), LocalTime.MIN);
                return isTodayAvailable && isTypeAvailable(startOfNextDay, end, typeId);
            } else {
                return daySlotTypes.isPeriodAvailable(start.toLocalTime(), end.toLocalTime());
            }
        }

        return false;
    }
}
