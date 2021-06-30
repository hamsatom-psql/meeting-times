package org.service;

import org.repository.ICalendarRepository;

import javax.annotation.Nonnull;
import java.io.IOException;

public interface IImportService {
    void importCalendarToRepository(@Nonnull ICalendarRepository calendarRepository) throws IOException;
}
