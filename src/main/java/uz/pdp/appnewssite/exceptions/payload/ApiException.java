package uz.pdp.appnewssite.exceptions.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiException {
    private final LocalDateTime localDateTime;
    private final HttpStatus httpStatus;
    private final String type;
    private final String MESSAGE;
}
