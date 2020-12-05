package com.azarenka.votingsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Time Util.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 05.12.2020
 */
public class TimeUtil {

    /**
     * Pattern for date.
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * Pattern for time.
     */
    public static final String TIME_PATTERN = "HH:MM";
    /**
     * Date formatter.
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    /**
     * Time formatter.
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * Returns time like string.
     *
     * @param localDateTime instance of {@link LocalDateTime}
     * @return time like string.
     */
    public static String timeToString(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(TIME_FORMATTER);
    }

    /**
     * Returns date like string.
     *
     * @param localDateTime instance of {@link LocalDateTime}
     * @return time like string
     */
    public static String dateToString(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(DATE_FORMATTER);
    }
}
