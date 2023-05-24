package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {

        String createTable = "CREATE TABLE `mydb`.`users` (\n" +
                "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastname` VARCHAR(45) NULL,\n" +
                "  `age` TINYINT(3) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
        try (Statement statement = Util.connectionBD().createStatement()) {
            statement.executeUpdate(createTable);

        } catch (SQLException e) {
            System.out.println("createUsersTable");

        }
    }

    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLE users";
        try (Statement statement = Util.connectionBD().createStatement()) {
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            System.out.println("dropUsersTable");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users (name, lastname, age) values (?,?,?)";
        try (PreparedStatement preparedStatement = Util.connectionBD().prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("saveUser");
        }

    }

    @Override
    public void removeUserById(long id) {
        String removeUser = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = Util.connectionBD().prepareStatement(removeUser);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("removeUserById");
        }
    }

    @Override
    public List<User> getAllUsers() {
        String getUsers = "SELECT * FROM users;";
        ResultSet resultSet = null;
        List<User> usersList = new ArrayList<>();
        try (Statement statement = Util.connectionBD().createStatement()) {
            resultSet = statement.executeQuery(getUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("getAllUsers");
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE users";

        try (Statement statement = Util.connectionBD().createStatement()) {
            statement.executeUpdate(cleanTable);
        } catch (SQLException e) {
            System.out.println("cleanUsersTable");
        }
    }


}
