package org.interviews.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class InterviewsAppointment implements Serializable {
    private static final long serialVersionUID = -5964439089284713530L;
    private UUID calendarId;
    private ZonedDateTime start;
    private ZonedDateTime end;

    public UUID getCalendarId() {
        return calendarId;
    }

    public InterviewsAppointment setCalendarId(UUID calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public InterviewsAppointment setStart(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public InterviewsAppointment setEnd(ZonedDateTime end) {
        this.end = end;
        return this;
    }
}
