package org.model;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Iso8601TimeInterval {
    private LocalDateTime start;
    private LocalDateTime end;

    public static Iso8601TimeInterval parse(@Nonnull String iso8601TimeInterval) {
        String[] intervalBorders = iso8601TimeInterval.split("/");
        LocalDateTime start = ZonedDateTime.parse(intervalBorders[0]).toLocalDateTime();
        LocalDateTime end = ZonedDateTime.parse(intervalBorders[1]).toLocalDateTime();

        Iso8601TimeInterval timeInterval = new Iso8601TimeInterval();
        timeInterval.start = start;
        timeInterval.end = end;
        return timeInterval;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
