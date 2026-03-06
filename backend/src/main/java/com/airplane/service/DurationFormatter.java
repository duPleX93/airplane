package com.airplane.service;

/**
 * Formats duration in minutes to Hungarian: "10 perc", "1 óra 15 perc", "2 óra 10 perc".
 */
public final class DurationFormatter {

    private DurationFormatter() {}

    public static String formatMinutes(int totalMinutes) {
        if (totalMinutes < 60) {
            return totalMinutes + " perc";
        }
        int hours = totalMinutes / 60;
        int mins = totalMinutes % 60;
        if (mins == 0) {
            return hours + " óra";
        }
        return hours + " óra " + mins + " perc";
    }
}
