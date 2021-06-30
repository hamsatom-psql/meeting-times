package org.repository;

import org.model.Calendar;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface ICalendarRepository {
    void save(@Nonnull Calendar calendar);

    @Nonnull
    Optional<Calendar> selectOne(@Nonnull UUID id);
}
