package org.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

class DaySlotTypesTest {
    private static final UUID TYPE = UUID.randomUUID();
    private static final LocalTime START = LocalTime.of(10, 20);
    private static final LocalTime END = LocalTime.of(11, 25);
    private DaySlotTypes daySlots;

    @BeforeEach
    void setUp() {
        daySlots = new DaySlotTypes();
    }

    @Test
    void empty() {
        Assertions.assertFalse(daySlots.isPeriodAvailable(START, END));
        Assertions.assertFalse(daySlots.isTypeAvailable(START, END, TYPE));
    }

    @Test
    void setAvailableTypes() {
        daySlots.setAvailableTypes(START, END, TYPE);
        Assertions.assertTrue(daySlots.isPeriodAvailable(START, END));
    }

    @Test
    void setAvailableTypes_subperiod() {
        daySlots.setAvailableTypes(START, END, TYPE);
        Assertions.assertTrue(daySlots.isPeriodAvailable(START.plusMinutes(20), END.minusMinutes(20)));
    }

    @Test
    void setAvailableTypes_early() {
        daySlots.setAvailableTypes(START, END, TYPE);
        Assertions.assertFalse(daySlots.isPeriodAvailable(START.minusMinutes(1), END));
    }

    @Test
    void setAvailableTypes_late() {
        daySlots.setAvailableTypes(START, END, TYPE);
        Assertions.assertFalse(daySlots.isPeriodAvailable(START, END.plusMinutes(1)));
    }

    @Test
    void setAvailableTypes_correctType() {
        daySlots.setAvailableTypes(START, END, TYPE);
        Assertions.assertTrue(daySlots.isTypeAvailable(START, END, TYPE));
    }

    @Test
    void setAvailableTypes_wrongType() {
        daySlots.setAvailableTypes(START, END, TYPE);
        Assertions.assertFalse(daySlots.isTypeAvailable(START, END, UUID.randomUUID()));
    }

    @Test
    void takeSlots() {
        daySlots.setAvailableTypes(START, END, TYPE);
        daySlots.takeSlots(START, END);
        Assertions.assertFalse(daySlots.isPeriodAvailable(START, END));
        Assertions.assertFalse(daySlots.isTypeAvailable(START, END, TYPE));
    }
}