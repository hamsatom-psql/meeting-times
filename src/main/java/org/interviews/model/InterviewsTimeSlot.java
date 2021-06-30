package org.interviews.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class InterviewsTimeSlot implements Serializable {
    private static final long serialVersionUID = -244079362369297005L;
    @SerializedName("calendar_id")
    private UUID calendarId;
    @SerializedName("type_id")
    private UUID typeId;
    private LocalDateTime start;
    private LocalDateTime end;

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

    public LocalDateTime getStart() {
        return start;
    }

    public InterviewsTimeSlot setStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public InterviewsTimeSlot setEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }
}
