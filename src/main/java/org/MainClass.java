package org;

import org.interviews.model.InterviewsConfig;
import org.interviews.service.InterviewsCalendarImportServices;
import org.model.Iso8601TimeInterval;
import org.repository.HashMapCalendarRepository;
import org.repository.ICalendarRepository;
import org.service.DefaultMeetingService;
import org.service.IImportService;
import org.service.IMeetingService;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;

public class MainClass {
    public static void main(String[] args) throws IOException {
        IImportService importService = new InterviewsCalendarImportServices(new InterviewsConfig());
        ICalendarRepository calendarRepository = new HashMapCalendarRepository();
        importService.importCalendarToRepository(calendarRepository);

        Set<UUID> calendarIds = new HashSet<>(Arrays.asList(
                UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9"),
                UUID.fromString("452dccfc-975e-11e5-bfa5-c8e0eb18c1e9"),
                UUID.fromString("48644c7a-975e-11e5-a090-c8e0eb18c1e9")
        ));

        IMeetingService meetingService = new DefaultMeetingService(calendarRepository);
        List<ZonedDateTime> availableTime = meetingService.findAvailableTime(calendarIds, Duration.ofMinutes(30), Iso8601TimeInterval.parse("2019-03-01T13:00:00Z/2019-05-11T15:30:00Z"));
        availableTime.forEach(System.out::println);
    }
}
