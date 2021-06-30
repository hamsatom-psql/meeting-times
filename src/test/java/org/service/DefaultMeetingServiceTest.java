package org.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.model.Calendar;
import org.model.Iso8601TimeInterval;
import org.repository.ICalendarRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultMeetingServiceTest {
    private static final Set<UUID> IDS = new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));

    @Mock
    private Calendar calendar1;
    @Mock
    private Calendar calendar2;
    @Mock
    private ICalendarRepository repository;

    private DefaultMeetingService meetingService;

    @BeforeEach
    void setUp() {
        lenient().when(repository.selectOne(any(UUID.class))).thenReturn(Optional.of(calendar1), Optional.of(calendar2));
        meetingService = new DefaultMeetingService(repository);
    }

    @Test
    void findAvailableTime() {
        when(calendar1.isPeriodAvailable(any(), any())).thenReturn(true);
        when(calendar2.isPeriodAvailable(any(), any())).thenReturn(true);
        List<LocalDateTime> availableTime = meetingService.findAvailableTime(IDS, Duration.ofMinutes(10), Iso8601TimeInterval.parse("2019-03-01T13:00:00Z/2019-03-01T13:30:00Z"));
        Assertions.assertEquals(20, availableTime.size());
    }

    @Test
    void findAvailableTime_noneAvailable() {
        when(calendar1.isPeriodAvailable(any(), any())).thenReturn(true);
        when(calendar2.isPeriodAvailable(any(), any())).thenReturn(false);
        List<LocalDateTime> availableTime = meetingService.findAvailableTime(IDS, Duration.ofMinutes(10), Iso8601TimeInterval.parse("2019-03-01T13:00:00Z/2019-03-01T13:30:00Z"));
        Assertions.assertTrue(availableTime.isEmpty());
    }

    @Test
    void findAvailableTime_subperiod() {
        when(calendar1.isPeriodAvailable(any(), any())).thenAnswer(invocation -> invocation.getArgument(0, LocalDateTime.class).isBefore(LocalDateTime.parse("2019-04-15T13:30:00")));
        when(calendar2.isPeriodAvailable(any(), any())).thenAnswer(invocation -> !invocation.getArgument(0, LocalDateTime.class).isBefore(LocalDateTime.parse("2019-04-14T13:30:00")));
        List<LocalDateTime> availableTime = meetingService.findAvailableTime(IDS, Duration.ofMinutes(10), Iso8601TimeInterval.parse("2019-04-10T13:00:00Z/2019-04-20T13:30:00Z"));
        Assertions.assertEquals(24 * 60, availableTime.size());
    }

    @Test
    void findAvailableTime_wrongType() {
        when(calendar1.isTypeAvailable(any(), any(), any())).thenReturn(true);
        when(calendar2.isTypeAvailable(any(), any(), any())).thenReturn(false);
        List<LocalDateTime> availableTime = meetingService.findAvailableTime(IDS, Duration.ofMinutes(10), Iso8601TimeInterval.parse("2019-03-01T13:00:00Z/2019-03-01T13:30:00Z"), UUID.randomUUID());
        Assertions.assertTrue(availableTime.isEmpty());
    }

    @Test
    void findAvailableTime_differentTypes() {
        when(calendar1.isTypeAvailable(any(), any(), any())).thenAnswer(invocation -> invocation.getArgument(0, LocalDateTime.class).isBefore(LocalDateTime.parse("2019-04-15T13:30:00")));
        when(calendar2.isTypeAvailable(any(), any(), any())).thenAnswer(invocation -> !invocation.getArgument(0, LocalDateTime.class).isBefore(LocalDateTime.parse("2019-04-14T13:30:00")));
        List<LocalDateTime> availableTime = meetingService.findAvailableTime(IDS, Duration.ofMinutes(10), Iso8601TimeInterval.parse("2019-04-10T13:00:00Z/2019-04-20T13:30:00Z"), UUID.randomUUID());
        Assertions.assertEquals(24 * 60, availableTime.size());
    }

    @Test
    void findAvailableTime_endBeforeStart() {
        Assertions.assertThrows(RuntimeException.class, () -> meetingService.findAvailableTime(IDS, Duration.ofMinutes(10),
                Iso8601TimeInterval.parse("2019-05-10T13:00:00Z/2019-04-20T13:30:00Z")));
    }

    @Test
    void findAvailableTime_withTypeEndBeforeStart() {
        Assertions.assertThrows(RuntimeException.class, () -> meetingService.findAvailableTime(IDS, Duration.ofMinutes(10),
                Iso8601TimeInterval.parse("2019-05-10T13:00:00Z/2019-04-20T13:30:00Z"), UUID.randomUUID()));
    }
}