package com.lewiskellett.bank.DemoBankREST.Advice;

import com.lewiskellett.bank.DemoBankREST.Types.AccountApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AccountApplicationAdvice {

    @ResponseBody
    @ExceptionHandler(AccountApplicationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String accountApplicationExceptionHandler(AccountApplicationException ex) {
        return "{\"error\": \"" + ex.getMessage() + "\" }";
    }
}
