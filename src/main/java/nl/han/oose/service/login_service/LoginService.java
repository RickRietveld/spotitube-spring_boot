package nl.han.oose.service.login_service;

import nl.han.oose.entity.account_entity.Account;
import nl.han.oose.entity.account_entity.UserToken;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public interface LoginService {
    UserToken login(Account user) throws LoginException;
}
