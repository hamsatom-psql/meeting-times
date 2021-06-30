package org.interviews.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InterviewsCalendar implements Serializable {
    private static final long serialVersionUID = -8582273013955339546L;
    private List<InterviewsAppointment> appointments;
    @SerializedName("timeslots")
    private List<InterviewsTimeSlot> timeSlots;

    public List<InterviewsAppointment> getAppointments() {
        return appointments;
    }

    public InterviewsCalendar setAppointments(List<InterviewsAppointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public List<InterviewsTimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public InterviewsCalendar setTimeSlots(List<InterviewsTimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
        return this;
    }
}
