package org.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class CalendarTest {
    private static final UUID TYPE = UUID.randomUUID();
    private static final LocalDateTime START = LocalDateTime.of(2020, 4, 20, 2, 50);
    private static final LocalDateTime END = LocalDateTime.of(2020, 4, 20, 21, 0);
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        calendar = new Calendar(UUID.randomUUID());
    }

    @Test
    void empty() {
        Assertions.assertFalse(calendar.isPeriodAvailable(START, END));
        Assertions.assertFalse(calendar.isTypeAvailable(START, END, TYPE));
    }

    @Test
    void setAvailableTimeSlots() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        Assertions.assertTrue(calendar.isPeriodAvailable(START, END));
        Assertions.assertTrue(calendar.isTypeAvailable(START, END, TYPE));
    }

    @Test
    void setAvailableTimeSlots_startAfterEnd() {
        Assertions.assertThrows(RuntimeException.class, () -> calendar.setAvailableTimeSlots(END, START, TYPE));
    }

    @Test
    void setAvailableTimeSlots_multipleDays() {
        LocalDateTime lateEnd = END.plusDays(5);
        calendar.setAvailableTimeSlots(START, lateEnd, TYPE);
        Assertions.assertTrue(calendar.isPeriodAvailable(START, lateEnd));
        Assertions.assertTrue(calendar.isTypeAvailable(START, lateEnd, TYPE));
    }

    @Test
    void setAvailableTimeSlots_multipleDaysSubperiod() {
        LocalDateTime lateEnd = END.plusDays(10);
        LocalDateTime subperiodEnd = lateEnd.minusDays(2).plusHours(1).plusMinutes(2);
        LocalDateTime subperiodStart = START.plusDays(2).minusHours(1).plusMinutes(2);
        calendar.setAvailableTimeSlots(START, lateEnd, TYPE);
        Assertions.assertTrue(calendar.isPeriodAvailable(subperiodStart, subperiodEnd));
        Assertions.assertTrue(calendar.isTypeAvailable(subperiodStart, subperiodEnd, TYPE));
    }

    @Test
    void setAvailableTimeSlots_subperiod() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        Assertions.assertTrue(calendar.isPeriodAvailable(START.plusHours(1), END.minusHours(1)));
        Assertions.assertTrue(calendar.isTypeAvailable(START.plusHours(1), END.minusHours(1), TYPE));
    }

    @Test
    void setAvailableTimeSlots_dayLate() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        Assertions.assertFalse(calendar.isPeriodAvailable(START, END.plusDays(1)));
        Assertions.assertFalse(calendar.isTypeAvailable(START, END.plusDays(1), TYPE));
    }

    @Test
    void setAvailableTimeSlots_minuteLate() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        Assertions.assertFalse(calendar.isPeriodAvailable(START, END.plusMinutes(1)));
        Assertions.assertFalse(calendar.isTypeAvailable(START, END.plusMinutes(1), TYPE));
    }

    @Test
    void setAvailableTimeSlots_dayEarly() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        Assertions.assertFalse(calendar.isPeriodAvailable(START.minusDays(1), END));
        Assertions.assertFalse(calendar.isTypeAvailable(START.minusDays(1), END, TYPE));
    }

    @Test
    void setAvailableTimeSlots_minuteEarly() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        Assertions.assertFalse(calendar.isPeriodAvailable(START.minusMinutes(1), END));
        Assertions.assertFalse(calendar.isTypeAvailable(START.minusMinutes(1), END, TYPE));
    }

    @Test
    void setAvailableTimeSlots_wrongType() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        Assertions.assertTrue(calendar.isPeriodAvailable(START, END));
        Assertions.assertFalse(calendar.isTypeAvailable(START, END, UUID.randomUUID()));
    }

    @Test
    void useTimeSlots() {
        calendar.setAvailableTimeSlots(START, END, TYPE);
        calendar.useTimeSlots(START, END);
        Assertions.assertFalse(calendar.isPeriodAvailable(START, END));
        Assertions.assertFalse(calendar.isTypeAvailable(START, END, TYPE));
    }

    @Test
    void useTimeSlots_part() {
        LocalDateTime earlyStart = START.minusHours(1).minusMinutes(1);
        LocalDateTime lateEnd = END.plusHours(1).plusMinutes(5);
        calendar.setAvailableTimeSlots(earlyStart, lateEnd, TYPE);
        calendar.useTimeSlots(START, END);

        Assertions.assertTrue(calendar.isPeriodAvailable(earlyStart, START));
        Assertions.assertTrue(calendar.isTypeAvailable(earlyStart, START, TYPE));

        Assertions.assertFalse(calendar.isPeriodAvailable(START, END));
        Assertions.assertFalse(calendar.isTypeAvailable(START, END, TYPE));


        Assertions.assertTrue(calendar.isPeriodAvailable(END, lateEnd));
        Assertions.assertTrue(calendar.isTypeAvailable(END, lateEnd, TYPE));
    }

    @Test
    void useTimeSlots_multipleDays() {
        LocalDateTime lateEnd = END.plusDays(5);
        calendar.setAvailableTimeSlots(START, lateEnd, TYPE);
        calendar.useTimeSlots(START, lateEnd);
        Assertions.assertFalse(calendar.isPeriodAvailable(START, lateEnd));
        Assertions.assertFalse(calendar.isTypeAvailable(START, lateEnd, TYPE));
    }

    @Test
    void useTimeSlots_partMultipleDays() {
        LocalDateTime earlyStart = START.minusDays(5).plusHours(1).plusMinutes(1);
        LocalDateTime lateEnd = END.plusDays(5).minusHours(1).minusMinutes(5);
        calendar.setAvailableTimeSlots(earlyStart, lateEnd, TYPE);
        calendar.useTimeSlots(START, END);

        Assertions.assertTrue(calendar.isPeriodAvailable(earlyStart, START));
        Assertions.assertTrue(calendar.isTypeAvailable(earlyStart, START, TYPE));

        Assertions.assertFalse(calendar.isPeriodAvailable(START, END));
        Assertions.assertFalse(calendar.isTypeAvailable(START, END, TYPE));


        Assertions.assertTrue(calendar.isPeriodAvailable(END, lateEnd));
        Assertions.assertTrue(calendar.isTypeAvailable(END, lateEnd, TYPE));
    }

    @Test
    void useTimeSlots_startAfterEnd() {
        Assertions.assertThrows(RuntimeException.class, () -> calendar.useTimeSlots(END, START));
    }

    @Test
    void isPeriodAvailable_startAfterEnd() {
        Assertions.assertThrows(RuntimeException.class, () -> calendar.isPeriodAvailable(END, START));
    }

    @Test
    void isTypeAvailable_startAfterEnd() {
        Assertions.assertThrows(RuntimeException.class, () -> calendar.isTypeAvailable(END, START, TYPE));
    }
}