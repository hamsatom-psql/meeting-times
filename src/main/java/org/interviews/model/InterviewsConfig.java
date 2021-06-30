package org.interviews.model;

import javax.annotation.Nonnull;
import java.io.File;

public class InterviewsConfig {
    private final File calendarsFolder = new File("src/main/resources/calendars");

    @Nonnull
    public File getCalendarsFolder() {
        return calendarsFolder;
    }
}
