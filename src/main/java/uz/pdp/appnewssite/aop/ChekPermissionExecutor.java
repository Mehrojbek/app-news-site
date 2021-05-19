package uz.pdp.appnewssite.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.exceptions.ForbiddinException;

@Component
@Aspect
public class ChekPermissionExecutor {

    @Before(value = "@annotation(checkPermission)")
    public void checkUserPermission(CheckPermission checkPermission){
        String value = checkPermission.value();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean exist = false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(value)) {
                exist = true;
                break;
            }
        }
        if (!exist)
            throw new ForbiddinException(HttpStatus.BAD_REQUEST, checkPermission.value(), "ruxsat yo'q");
    }
}
