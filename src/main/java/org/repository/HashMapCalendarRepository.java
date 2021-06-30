package org.repository;

import org.model.Calendar;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class HashMapCalendarRepository implements ICalendarRepository {
    @Override
    public void save(@Nonnull Calendar calendar) {

    }

    @Nonnull
    @Override
    public Optional<Calendar> selectOne(@Nonnull UUID id) {
        return Optional.empty();
    }
}
