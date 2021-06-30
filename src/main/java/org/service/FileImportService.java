package org.service;

import org.repository.ICalendarRepository;

import javax.annotation.Nonnull;

public class FileImportService implements IImportService {
    private final ICalendarRepository calendarRepository;

    public FileImportService(@Nonnull ICalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }
}
