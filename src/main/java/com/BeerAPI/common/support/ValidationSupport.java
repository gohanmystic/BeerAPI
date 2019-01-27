package com.BeerAPI.common.support;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.net.IDN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.BeerAPI.common.enums.CodeEnum;

@Component
public class ValidationSupport {

    private static final int MAX_LOCAL_PART_LENGTH = 64;
    private static final int MAX_DOMAIN_PART_LENGTH = 255;

    private static final String LOCAL_PART_ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uFFFF-]";
    private static final String LOCAL_PART_INSIDE_QUOTES_ATOM = "([a-z0-9!#$%&'*.(),<>\\[\\]:;  @+/=?^_`{|}~\u0080-\uFFFF-]|\\\\\\\\|\\\\\\\")";
    private static final Pattern LOCAL_PART_PATTERN = Pattern.compile(
            "(" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" +
                    "(\\." + "(" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" + ")*", CASE_INSENSITIVE
    );

    public boolean isBlank(final String target) {
        return StringUtils.isBlank(target);
    }

    public boolean isBlank(final Object target) {

        if (target == null) {
            return true;
        }

        if (target instanceof String) {
            return StringUtils.isBlank(String.valueOf(target));
        }

        return false;
    }

    public boolean checkLength(final String target, final long length) {
        return (StringUtils.length(target) == length);
    }

    public boolean checkMin(final String target, final long min) {
        return (StringUtils.length(target) >= min);
    }

    public boolean checkMax(final String target, final long max) {
        return (StringUtils.length(target) <= max);
    }

    public boolean checkAlphaNumberic(final String target) {
        return StringUtils.isAlphanumeric(target);
    }

    public boolean checkNumberic(final String target) {
        return StringUtils.isNumeric(target);
    }

    public boolean checkValidEnum(final Class<? extends CodeEnum> enumClass, final String target) {

        boolean result = false;

        final CodeEnum[] enumList = enumClass.getEnumConstants();

        for (CodeEnum codeEnum : enumList) {
            if (StringUtils.equals(codeEnum.getValue(), target)) {
                result = true;
                break;
            }
        }

        return result;
    }

    public boolean checkEmail(final String target) {

        if (target == null || target.length() == 0) {
            return true;
        }

        String stringValue = target.toString();
        int splitPosition = stringValue.lastIndexOf('@');

        if (splitPosition < 0) {
            return false;
        }

        String localPart = stringValue.substring(0, splitPosition);
        String domainPart = stringValue.substring(splitPosition + 1);

        if (!matchLocalPart(localPart)) {
            return false;
        }

        return this.matchDomain(domainPart);
    }

    private boolean matchLocalPart(String localPart) {

        if (localPart.length() > MAX_LOCAL_PART_LENGTH) {

            return false;
        }

        Matcher matcher = LOCAL_PART_PATTERN.matcher(localPart);

        return matcher.matches();
    }

    private boolean matchDomain(String domain) {

        if ( domain.endsWith( "." ) ) {
            return false;
        }

        String[] domainSplit = domain.split("[.]");
        if (domainSplit.length < 2) {
            return false;
        }

        Matcher matcher = LOCAL_PART_PATTERN.matcher( domain );
        if ( !matcher.matches() ) {
            return false;
        }

        String asciiString;
        try {
            asciiString = IDN.toASCII( domain );
        }
        catch (IllegalArgumentException e) {
            return false;
        }

        if ( asciiString.length() > MAX_DOMAIN_PART_LENGTH ) {
            return false;
        }

        return true;
    }
}
