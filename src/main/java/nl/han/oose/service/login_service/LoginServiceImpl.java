package nl.han.oose.service.login_service;

import nl.han.oose.entity.account_entity.Account;
import nl.han.oose.entity.account_entity.UserToken;
import nl.han.oose.persistence.account_dao.TokenDAO;
import nl.han.oose.persistence.account_dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TokenDAO tokenDAO;

    @Override
    public UserToken login(Account account) throws LoginException {
        if (tokenDAO.checkIfTokenAlreadyExists(account.getUser())) {
            return tokenDAO.getExistingUserAndToken(account.getUser());
        } else if (userDAO.verifyLogin(account)) {
            return tokenDAO.getNewToken(account);
        } else {
            throw new LoginException("Wrong credentials.");
        }
    }


}





