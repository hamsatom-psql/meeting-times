package org.interviews.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class InterviewsTimeSlot implements Serializable {
    private static final long serialVersionUID = -244079362369297005L;
    private UUID calendarId;
    private UUID typeId;
    private ZonedDateTime start;
    private ZonedDateTime end;

    public UUID getCalendarId() {
        return calendarId;
    }

    public InterviewsTimeSlot setCalendarId(UUID calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public InterviewsTimeSlot setTypeId(UUID typeId) {
        this.typeId = typeId;
        return this;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public InterviewsTimeSlot setStart(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public InterviewsTimeSlot setEnd(ZonedDateTime end) {
        this.end = end;
        return this;
    }
}
