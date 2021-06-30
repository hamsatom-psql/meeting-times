package org.repository;

import org.model.Calendar;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HashMapCalendarRepository implements ICalendarRepository {
    private final Map<UUID, Calendar> database = new HashMap<>();

    @Override
    public void save(@Nonnull Calendar calendar) {
        database.put(calendar.getId(), calendar);
    }

    @Nonnull
    @Override
    public Optional<Calendar> selectOne(@Nonnull UUID id) {
        return Optional.ofNullable(database.get(id));
    }
}
