package org;

import org.repository.HashMapCalendarRepository;
import org.repository.ICalendarRepository;
import org.service.DefaultMeetingService;
import org.interviews.service.InterviewsCalendarImportServices;
import org.service.IImportService;
import org.service.IMeetingService;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        IImportService importService = new InterviewsCalendarImportServices();
        ICalendarRepository calendarRepository = new HashMapCalendarRepository();
        importService.importCalendarToRepository(calendarRepository);

        IMeetingService meetingService = new DefaultMeetingService(calendarRepository);
    }
}
