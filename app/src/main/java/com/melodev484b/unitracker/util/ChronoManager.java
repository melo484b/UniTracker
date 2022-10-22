package com.melodev484b.unitracker.util;

import android.app.DatePickerDialog;
import android.content.Context;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public abstract class ChronoManager {
    static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyy");
    static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDateTime parseDateTime(String dateTime) {
        return (LocalDateTime) dateTimeFormat.parse(dateTime);
    }

    public static LocalTime parseTime(String time) {
        return (LocalTime) timeFormat.parse(time);
    }

    public static Long dateInMilliseconds(String date) {
        LocalDate futureDate = (LocalDate) dateFormat.parse(date);
        LocalTime currentTime = LocalTime.parse(timeFormat.format(LocalTime.now()));
        LocalDateTime futureDateWithCurrentTime = LocalDateTime.of(futureDate, currentTime);
        return futureDateWithCurrentTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTimeFormat.format(dateTime);
    }

    public static String toString(LocalTime time) {
        return timeFormat.format(time);
    }

    public static String dateTime(int year, int month, int day, int hour, int minute) {
        return dateTimeFormat.format(LocalDateTime.of(year, month + 1, day, hour, minute));
    }

    public static String time(int hour, int minute) {
        return timeFormat.format(LocalTime.of(hour, minute));
    }

    public static String date(int year, int month, int day) {
        return dateFormat.format(LocalDate.of(year, month, day));
    }

}
