package com.BeerAPI.common.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.BeerAPI.common.enums.DateTimeFormat;
import com.BeerAPI.common.exception.SystemException;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LoggingSupport extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {

        StringBuilder param = new StringBuilder();
        param.append("[")
             .append(convertDateTimeToString(LocalDateTime.now(), DateTimeFormat.YYYYMMDDHHMMSS))
             .append("]");

        if (RequestContextHolder.getRequestAttributes() != null) {

            final ServletContext servletContext = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest().getServletContext();

            final WebApplicationContext applicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(servletContext);

            if (applicationContext.containsBean("moduleIdSupport")) {

                final ModuleIdSupport moduleIdSupport = applicationContext.getBean(ModuleIdSupport.class);

                if (moduleIdSupport != null && moduleIdSupport.getModuleId() != null) {
                    param.append("[").append(moduleIdSupport.getModuleId()).append("]");
                }
            }
        }

        return param.toString();
    }

    private String convertDateTimeToString(final LocalDateTime target, final DateTimeFormat pattern) {

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
}
