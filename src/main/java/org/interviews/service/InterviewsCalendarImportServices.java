package org.interviews.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.interviews.model.InterviewsAppointment;
import org.interviews.model.InterviewsCalendar;
import org.interviews.model.InterviewsConfig;
import org.interviews.model.InterviewsTimeSlot;
import org.model.Calendar;
import org.repository.ICalendarRepository;
import org.service.IImportService;
import org.service.LocalDateTimeDeserializer;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InterviewsCalendarImportServices implements IImportService {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
    private final InterviewsConfig interviewsConfig;

    public InterviewsCalendarImportServices(InterviewsConfig interviewsConfig) {
        this.interviewsConfig = interviewsConfig;
    }

    @Override
    public void importCalendarToRepository(@Nonnull ICalendarRepository calendarRepository) throws IOException {
        for (File file : interviewsConfig.getCalendarsFolder().listFiles()) {
            InterviewsCalendar interviewsCalendar = fileToCalendar(file);
            List<InterviewsAppointment> appointments = interviewsCalendar.getAppointments();
            List<InterviewsTimeSlot> timeSlots = interviewsCalendar.getTimeSlots();
            Optional<UUID> calendarId = extractCalendarId(appointments, timeSlots);

            if (!calendarId.isPresent()) {
                continue;
            }

            Calendar calendar = new Calendar(calendarId.get());
            timeSlots.forEach(slot -> calendar.setAvailableTimeSlots(slot.getStart(), slot.getEnd(), slot.getTypeId()));
            appointments.forEach(appointment -> calendar.useTimeSlot(appointment.getStart(), appointment.getEnd()));
            calendarRepository.save(calendar);
        }
    }

    private Optional<UUID> extractCalendarId(List<InterviewsAppointment> appointments, List<InterviewsTimeSlot> timeSlots) {
        if (!appointments.isEmpty()) {
            return Optional.of(appointments.get(0).getCalendarId());
        } else if (!timeSlots.isEmpty()) {
            return Optional.of(timeSlots.get(0).getCalendarId());
        } else {
            return Optional.empty();
        }
    }

    private InterviewsCalendar fileToCalendar(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), StandardCharsets.UTF_8))) {
            return GSON.fromJson(reader, InterviewsCalendar.class);
        }
    }
}
