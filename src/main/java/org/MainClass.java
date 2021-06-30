package org;

import org.repository.HashMapCalendarRepository;
import org.repository.ICalendarRepository;
import org.service.DefaultMeetingService;
import org.service.FileImportService;
import org.service.IImportService;
import org.service.IMeetingService;

public class MainClass {
    public static void main(String[] args) {
        ICalendarRepository calendarRepository = new HashMapCalendarRepository();
        IImportService importService = new FileImportService(calendarRepository);

        IMeetingService meetingService = new DefaultMeetingService(calendarRepository);
    }
}
