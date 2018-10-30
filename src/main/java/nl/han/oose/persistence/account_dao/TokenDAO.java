package nl.han.oose.persistence.account_dao;

import nl.han.oose.entity.account_entity.Account;
import nl.han.oose.entity.account_entity.UserToken;
import nl.han.oose.persistence.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenDAO {

    @Autowired
    private ConnectionFactory connectionFactory;

    public UserToken getUserToken(String token) {

        UserToken userToken = null;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement getTokenStatement = connection.prepareStatement("SELECT * FROM token WHERE token = ?")
        ) {
            getTokenStatement.setString(1, token);
            ResultSet resultSet = getTokenStatement.executeQuery();
            while (resultSet.next()) {
                String tokenString = resultSet.getString("token");
                String user = resultSet.getString("user");
                userToken = new UserToken(tokenString, user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userToken;
    }

    public boolean checkIfTokenAlreadyExists(String username) {
        boolean tokenExists = false;
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM token WHERE user = ?;")
        ) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            if (resultSet.getRow() > 0) {
                tokenExists = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tokenExists;
    }

    public UserToken getNewToken(Account login) {
        String user = login.getUser();
        String token = generateToken();
        String currentDatetime = getDatetime();
        UserToken newLoginToken = new UserToken(token, user);
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement query = connection.prepareStatement("INSERT INTO token (user, token, expiryDate) VALUES (?, ?, ?);")
        ) {
            query.setString(1, user);
            query.setString(2, token);
            query.setString(3, currentDatetime);
            query.execute();
            return newLoginToken;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeTokenFromList(String user) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM token WHERE user = ?;")
        ) {
            preparedStatement.setString(1, user);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String getDatetime() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return dateFormat.format(date);
    }

    public boolean isTokenValid(UserToken userToken) {
        boolean isValid = false;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM token WHERE token = ? AND user = ?;")
        ) {
            preparedStatement.setString(1, userToken.getToken());
            preparedStatement.setString(2, userToken.getUser());
            ResultSet resultSet = preparedStatement.executeQuery();
            LocalDate currentDate = LocalDate.now();
            while (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate expiryDate = LocalDate.parse(resultSet.getString("expiryDate"), formatter);
                if (expiryDate.isAfter(currentDate)) {
                    isValid = true;
                } else {
                    removeTokenFromList(userToken.getUser());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }


    public UserToken getExistingUserAndToken(String username) {
        Connection connection = connectionFactory.getConnection();
        UserToken userToken = new UserToken();
        try {
            String query = "SELECT * FROM token where user = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userToken.setToken(rs.getString("token"));
                userToken.setUser(rs.getString("user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userToken;
    }

}
