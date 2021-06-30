package org.interviews.model;

import java.io.File;

public class InterviewsConfig {
    private final File calendarsFolder = new File("src/main/resources/calendars");

    public File getCalendarsFolder() {
        return calendarsFolder;
    }
}
