package org.interviews.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.interviews.model.InterviewsAppointment;
import org.interviews.model.InterviewsCalendar;
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

public class InterviewsCalendarImportServices implements IImportService {
    private static final String CALENDARS_FOLDER = "src/main/resources/calendars";
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();

    @Override
    public void importCalendarToRepository(@Nonnull ICalendarRepository calendarRepository) throws IOException {
        for (File file : new File(CALENDARS_FOLDER).listFiles()) {
            InterviewsCalendar interviewsCalendar = fileToCalendar(file);
            List<InterviewsAppointment> appointments = interviewsCalendar.getAppointments();
            List<InterviewsTimeSlot> timeSlots = interviewsCalendar.getTimeSlots();

            Calendar calendar;
            if (!appointments.isEmpty()) {
                calendar = new Calendar(appointments.get(0).getCalendarId());
            } else if (!timeSlots.isEmpty()) {
                calendar = new Calendar(timeSlots.get(0).getCalendarId());
            } else {
                continue;
            }
        }
    }

    private InterviewsCalendar fileToCalendar(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), StandardCharsets.UTF_8))) {
            return GSON.fromJson(reader, InterviewsCalendar.class);
        }
    }
}
