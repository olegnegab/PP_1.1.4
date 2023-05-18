package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "admin";


    public static Connection connectionBD() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка соединения с БД");
        }
        return connection;
    }


    public static void disconnectionBD(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка соединения с БД");
        }
    }
}
