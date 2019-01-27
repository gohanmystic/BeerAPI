package com.BeerAPI.common.support;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.BeerAPI.common.entity.DateTime;
import com.BeerAPI.common.entity.DateTimeProperties;
import com.BeerAPI.common.enums.DateTimeFormat;
import com.BeerAPI.common.exception.SystemException;

@Component
public class DateTimeSupport extends DateUtils implements Serializable {

    private static final long serialVersionUID = -3301695478208950415L;

    public Date now() {
        return new Date();
    }

    public String getCurrentTimestamp() {
        Date date = new Date();

        return String.valueOf(date.getTime());
    }

    public String convertDateTimeToString(final LocalDateTime target,
                                          final DateTimeFormat pattern) {

        String result = null;
        if (pattern == null) {
            throw new SystemException();
        }

        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern(pattern.getValue());
        } catch (final Exception e) {
            throw new SystemException(e.getMessage(), e);
        }

        try {
            result = formatter.format(target);
        } catch (final Exception e) {
            throw new SystemException(e.getMessage(), e);
        }

        return result;
    }

    public Date convertStringToUtilDate(final String target,
                                          final DateTimeFormat pattern) {

        Date result = null;
        if (pattern == null) {
            throw new SystemException();
        }

        if (StringUtils.isNotBlank(target)) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat(pattern.getValue());
                result = dateFormat.parse(target);

            } catch (final ParseException e) {

                throw new SystemException(e.getMessage(), e);
            }
        }

        return result;
    }

    public String convertUtilDateToString(final Date target,
                                          final DateTimeFormat pattern) {

        String result = null;
        if (pattern == null) {
            throw new SystemException();
        }

        if (target != null) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat(pattern.getValue());
                result = dateFormat.format(target);

            } catch (final Exception e) {

                throw new SystemException(e.getMessage(), e);
            }
        }

        return result;
    }

    public Date convertCommonDateTimeToUtilDate(final DateTime target,
                                                final DateTimeFormat pattern) {

        Date result = null;
        if (pattern == null) {
            throw new SystemException();
        }

        if (target != null && StringUtils.isNotBlank(target.getFormatted())) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat(pattern.getValue());
                result = dateFormat.parse(target.getFormatted());

            } catch (final ParseException e) {

                throw new SystemException(e.getMessage(), e);
            }
        }

        return result;
    }

    public DateTime convertUtilDateToCommonDateTime(final Date target, final DateTimeFormat pattern) {

        DateTime result = null;

        if (pattern == null) {
            throw new SystemException();
        }

        if (target != null) {
            try {

                result = new DateTime();
                final DateFormat dateFormat = new SimpleDateFormat(pattern.getValue());

                Calendar cal = Calendar.getInstance();
                cal.setTime(target);

                DateTimeProperties properties = new DateTimeProperties();
                properties.setDay(cal.get(Calendar.DAY_OF_MONTH));
                properties.setMonth(cal.get(Calendar.MONTH) + 1);
                properties.setYear(cal.get(Calendar.YEAR));

                result.setDate(properties);
                result.setFormatted(dateFormat.format(target));
                result.setTimestamp(target.getTime());

            } catch (final Exception e) {

                throw new SystemException(e.getMessage(), e);
            }
        }

        return result;
    }

    public boolean isDate(final String target,
                          final DateTimeFormat pattern,
                          final boolean strict) {

        boolean ret = true;

        if (pattern == null) {
            throw new SystemException();
        }

        if (StringUtils.isNotBlank(target)) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat(pattern.getValue());
                final Date date = dateFormat.parse(target);

                String value = null;
                if (strict
                        && (value = dateFormat.format(date)) != null
                        && !target.equals(value)) {

                    ret = false;
                }

            } catch (final ParseException e) {

                ret = false;
            }
        }

        return ret;
    }

    public boolean isDate(final DateTime target,
                          final DateTimeFormat pattern,
                          final boolean strict) {

        boolean ret = true;

        if (pattern == null) {
            throw new SystemException();
        }

        if (target != null && StringUtils.isNotBlank(target.getFormatted())) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat(pattern.getValue());
                final Date date = dateFormat.parse(target.getFormatted());

                String value = null;
                if (strict
                        && (value = dateFormat.format(date)) != null
                        && !target.getFormatted().equals(value)) {

                    ret = false;
                }

            } catch (final ParseException e) {

                ret = false;
            }
        }

        return ret;
    }

    public LocalDate convertStringToLocalDate(final String target,
                                              final DateTimeFormat pattern) {

        LocalDate localDate = null;
        if (pattern == null) {
            throw new SystemException();
        }

        DateTimeFormatter dateTimeFormatter = null;
        try {
            dateTimeFormatter = DateTimeFormatter.ofPattern(pattern.getValue());
        } catch (final Exception e) {
            throw new SystemException("", e);
        }

        try {
            localDate = LocalDate.parse(target, dateTimeFormatter);
        } catch (final DateTimeParseException e) {
            throw new SystemException("", e);
        }

        return localDate;
    }

    public boolean compareDate(final String fromDate, final String toDate) {
        return compareDate(convertStringToLocalDate(fromDate, DateTimeFormat.SLASH_YYYYMMDD),
                           convertStringToLocalDate(toDate, DateTimeFormat.SLASH_YYYYMMDD));
    }

    public boolean compareDate(final LocalDate fromDate, final LocalDate toDate) {
        return ((fromDate.compareTo(toDate) < 0) || (fromDate.compareTo(toDate) == 0));
    }
}