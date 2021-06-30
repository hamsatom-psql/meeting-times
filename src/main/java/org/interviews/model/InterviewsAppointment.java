package org.interviews.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class InterviewsAppointment implements Serializable {
    private static final long serialVersionUID = -5964439089284713530L;
    @SerializedName("calendar_id")
    private UUID calendarId;
    private LocalDateTime start;
    private LocalDateTime end;

    public UUID getCalendarId() {
        return calendarId;
    }

    public InterviewsAppointment setCalendarId(UUID calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public InterviewsAppointment setStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public InterviewsAppointment setEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }
}
