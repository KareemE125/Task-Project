package com.ke.task.exceptions;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(
        String message,
        @JsonIgnore(value = true) Throwable throwable,
        String httpStatus,
        int httpStatusCode,
        ZonedDateTime zonedDateTime
) { }
