package ru.nsu.ccfit.muratov.railroad.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database implements AutoCloseable {
    private static Connection connection;

    private static void init() throws IOException, SQLException, ClassNotFoundException {
        InputStream resource = Database.class.getClassLoader().
                getResourceAsStream("credentials.properties");
        Properties properties = new Properties();
        properties.load(resource);

        String login = properties.getProperty("db.login");
        String password = properties.getProperty("db.password");
        String dbName = properties.getProperty("db.name");
        String address = properties.getProperty("db.address");
        String port = properties.getProperty("db.port", "5432");

        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                String.format("jdbc:postgresql://%s:%s/%s", address, port, dbName),
                login, password);
    }

    public static Connection getInstance() throws DatabaseException {
        try {
            if (connection == null) {
                init();
            }
        }
        catch(IOException | ClassNotFoundException | SQLException e) {
            throw new DatabaseException(e);
        }
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
