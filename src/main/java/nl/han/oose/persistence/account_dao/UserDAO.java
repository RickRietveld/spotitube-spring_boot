package nl.han.oose.persistence.account_dao;

import nl.han.oose.entity.account_entity.Account;
import nl.han.oose.persistence.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    @Autowired
    private ConnectionFactory connectionFactory;

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user;")
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("user");
                String password = resultSet.getString("password");
                accounts.add(new Account(user, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    public boolean verifyLogin(Account account) throws LoginException {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement query = connection.prepareStatement("SELECT * FROM user WHERE user = ? AND password = ?;")
        ) {
            query.setString(1, account.getUser());
            query.setString(2, account.getPassword());
            ResultSet resultSet = query.executeQuery();
            resultSet.last();
            if (resultSet.getRow() == 1) {
                return true;
            } else {
                throw new LoginException("Wrong credentials.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void persistAccount(Account account) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO user (user,password) VALUES (?,?);")
        ) {
            statement.setString(1, account.getUser());
            statement.setString(2, account.getPassword());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
