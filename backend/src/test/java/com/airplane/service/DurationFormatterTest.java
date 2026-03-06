package com.airplane.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DurationFormatterTest {

    @Test
    void formatMinutes_underOneHour() {
        assertEquals("0 perc", DurationFormatter.formatMinutes(0));
        assertEquals("10 perc", DurationFormatter.formatMinutes(10));
        assertEquals("59 perc", DurationFormatter.formatMinutes(59));
    }

    @Test
    void formatMinutes_exactHours() {
        assertEquals("1 óra", DurationFormatter.formatMinutes(60));
        assertEquals("2 óra", DurationFormatter.formatMinutes(120));
    }

    @Test
    void formatMinutes_hoursAndMinutes() {
        assertEquals("1 óra 15 perc", DurationFormatter.formatMinutes(75));
        assertEquals("2 óra 10 perc", DurationFormatter.formatMinutes(130));
    }
}
