package uz.pdp.appnewssite.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForbiddinException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String type;
    private final String message;

    public ForbiddinException(HttpStatus httpStatus, String type, String message) {
        this.httpStatus = httpStatus;
        this.type = type;
        this.message = message;
    }
}
