package orig.example.authorizationsimple.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import orig.example.authorizationsimple.security.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;

@Service("authz")
@Log4j2
public class AuthService {

    public boolean check(HttpServletRequest request, CustomUserDetails principal){
        log.info("checking incoming request " + request.getRequestURI() +
        "for Principal " + principal.getUsername());
        return true;
    }
}
