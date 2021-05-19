package uz.pdp.appnewssite.exceptions.handler;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.appnewssite.exceptions.ForbiddinException;
import uz.pdp.appnewssite.exceptions.ResourceNotFoundException;
import uz.pdp.appnewssite.exceptions.payload.ApiException;

import java.time.LocalDateTime;

@ControllerAdvice
public class AllExceptionHandler {




    @ExceptionHandler(value = {ForbiddinException.class})
    public HttpEntity<?> handleException(ForbiddinException e){
        ApiException apiException = new ApiException(
                LocalDateTime.now(),
                e.getHttpStatus(),
                e.getType(),
                e.getMessage()
        );
        return ResponseEntity.status(apiException.getHttpStatus()).body(apiException);
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public HttpEntity<?> handleException(ResourceNotFoundException e){
        ApiException apiException = new ApiException(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(apiException.getHttpStatus()).body(apiException);
    }


    @ExceptionHandler(value = {Exception.class})
    public HttpEntity<?> handleException(Exception e){
        ApiException apiException = new ApiException(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getLocalizedMessage(),
                e.getMessage()
        );
        return ResponseEntity.status(apiException.getHttpStatus()).body(apiException);
    }


}
