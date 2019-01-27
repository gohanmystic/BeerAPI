package com.BeerAPI.common.handler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.BeerAPI.common.Error;
import com.BeerAPI.common.annotation.ErrorCode;
import com.BeerAPI.common.annotation.FieldName;
import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.enums.StatusCode;
import com.BeerAPI.common.exception.NotFoundException;
import com.BeerAPI.common.support.ResponseSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DOT_CHARACTER = ".";

    @Autowired
    @Qualifier("validationMessageSource")
    private MessageSource messageSource;

    @Autowired
    private ResponseSupport responseSupport;

    @ExceptionHandler({Exception.class, RuntimeException.class})
    protected ResponseEntity<Object> handleInternalError(final Exception exception) {

        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                responseSupport.fetchError(StatusCode.SYSTEM_FAILURE,
                                             MessageCode.SYSTEM_FAILURE_MESSAGE),
                HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(final NotFoundException exception) {

        String code = "notFound";
        if (StringUtils.isNotEmpty(exception.getMessage())) {
            code = exception.getMessage();
        }

        String message = messageSource.getMessage(code, null, Locale.getDefault());
        log.error(message, exception);

        return new ResponseEntity<>(
                responseSupport.fetchError(StatusCode.NOT_FOUND, message),
                HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error(exception.getMessage(), exception);

        return handleBindingResult(exception,
                                   exception.getBindingResult(),
                                   headers,
                                   request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error(exception.getMessage(), exception);

        return handleBindingResult(exception,
                                   exception.getBindingResult(),
                                   headers,
                                   request);
    }

    private ResponseEntity<Object> handleBindingResult(
            final Exception exception,
            final BindingResult bindingResult,
            final HttpHeaders headers,
            final WebRequest request) {

        BeanWrapperImpl targetWrapper = new BeanWrapperImpl(bindingResult.getTarget());
        List<Error> errors = new ArrayList<>();

        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (final FieldError fieldError : fieldErrors) {
            Class<? extends Object> clazz = bindingResult.getTarget().getClass();
            String fieldNameTemp = fieldError.getField();
            try {
                final String itemName = getPropertyShortName(targetWrapper, fieldNameTemp);
                final String code = getErrorCode(fieldError.getCode());

                String errorMsg = null;
                if (StringUtils.contains(fieldNameTemp, DOT_CHARACTER)) {

                    fieldNameTemp = StringUtils.substringBefore(fieldNameTemp, "[");
                    errorMsg = messageSource.getMessage("itemInputCheck", null, Locale.getDefault());
                } else {

                    errorMsg = fieldError.getDefaultMessage();
                }

                final Field field = clazz.getDeclaredField(fieldNameTemp);
                final FieldName fieldNameAnnotation = field.getAnnotation(FieldName.class);
                String fieldName = itemName;
                if (fieldNameAnnotation != null) {
                    fieldName = fieldNameAnnotation.value();
                }

                final Error error = new Error();
                error.setErrorItem(itemName);
                error.setErrorCode(code);
                error.setErrorMessage(messageSource.getMessage(null,
                                                               new Object[] {fieldName},
                                                               errorMsg,
                                                               Locale.getDefault()));
                errors.add(error);
            } catch (NoSuchFieldException e) {
                log.error(e.toString());
                e.printStackTrace();
            } catch (SecurityException e) {
                log.error(e.toString());
                e.printStackTrace();
            }
        }

        return handleExceptionInternal(exception,
                responseSupport.fetchError(errors),
                headers,
                HttpStatus.OK,
                request);
    }

    private String getPropertyShortName(final BeanWrapperImpl targetWrapper,
                                        final String fieldName) {
        final StringJoiner result = new StringJoiner(DOT_CHARACTER);

        final StringJoiner stringJoiner = new StringJoiner(DOT_CHARACTER);
        final StringTokenizer stringTokenizer = new StringTokenizer(fieldName, DOT_CHARACTER);

        while (stringTokenizer.hasMoreTokens()) {

            final String propertyName = stringTokenizer.nextToken();

            stringJoiner.add(propertyName);

            final TypeDescriptor typeDescriptor = targetWrapper.getPropertyTypeDescriptor(stringJoiner.toString());
            final JsonProperty jsonProperty = typeDescriptor.getAnnotation(JsonProperty.class);
            final String jsonPropertyValue = (String) AnnotationUtils.getValue(jsonProperty);

            if (jsonPropertyValue == null) {
                result.add(propertyName);
            } else {
                final String fullPropertyName = PropertyAccessorUtils.getPropertyName(propertyName);
                result.add(propertyName.replaceFirst(fullPropertyName, jsonPropertyValue));
            }
        }

        return result.toString();
    }

    private String getErrorCode(final String errorKey) {
        Class<?> clazz = null;
        String code = null;
        final String shortName = StringUtils.capitalize(errorKey);

        try {

            clazz = Class.forName("com.BeerAPI.common.validation.constraints." + shortName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (clazz.isAnnotationPresent(ErrorCode.class)) {

            final ErrorCode errorCode = clazz.getAnnotation(ErrorCode.class);
            code = errorCode.value();
        }

        return code;
    }
}
