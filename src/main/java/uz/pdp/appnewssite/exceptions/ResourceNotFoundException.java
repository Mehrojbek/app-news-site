package uz.pdp.appnewssite.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class ResourceNotFoundException extends RuntimeException{

    private final String resourceName;  //ROLE

    private final String resourceField; //NAME

    private final Object object;       //ADMIN, USER, 1, @, #, 1254

    private  final String message;


    public ResourceNotFoundException(String resourceName, String resourceField, Object object, String message) {
        this.resourceName = resourceName;
        this.resourceField = resourceField;
        this.object = object;
        this.message = message;
    }
}
