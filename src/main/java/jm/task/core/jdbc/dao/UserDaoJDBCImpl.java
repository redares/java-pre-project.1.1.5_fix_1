package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection dbConnection = null;
    private Statement statement = null;

    public UserDaoJDBCImpl() {

        try {
            dbConnection = new Util().getDbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {

        System.out.println("Creating table...");

        String sql = "CREATE TABLE IF NOT EXISTS USER " +
                "(id int NOT NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age TINYINT UNSIGNED, " +
                " PRIMARY KEY (id))";

        try {
            statement.executeUpdate(sql);
            System.out.println("Created table!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        System.out.println("Deleting table...");

        String sql = "DROP TABLE IF EXISTS USER";

        try {
            statement.executeUpdate(sql);
            System.out.println("Table  deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        System.out.println("Save user...");

        String sql = "INSERT INTO USER (name, lastName, age) " +
                "VALUES ('" + name + "', '" + lastName + "', " + age + ")";

        try {
            statement.executeUpdate(sql);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        System.out.println("Delete user...");

        String sql = "DELETE FROM USER " +
                "WHERE id = " + id;

        try {
            statement.executeUpdate(sql);
            System.out.println("User is deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        System.out.println("Getting all users...");

        String sql = "SELECT * FROM USER";
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while(resultSet.next()) {

                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));

                users.add(user);

                System.out.println("Users were received!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        System.out.println("Clearing  table...");

        String sql = "TRUNCATE USER";

        try {
            statement.executeUpdate(sql);
            System.out.println("Table is cleared!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
