package nl.han.oose.controller.login_controller;

import nl.han.oose.entity.account_entity.Account;
import nl.han.oose.entity.account_entity.UserToken;
import nl.han.oose.service.login_service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(path = "/login", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<UserToken> login(@RequestBody Account user) {
        try {
            return new ResponseEntity<>(loginService.login(user), HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}


