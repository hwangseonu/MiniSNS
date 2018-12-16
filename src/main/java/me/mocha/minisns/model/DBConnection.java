package me.mocha.minisns.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger log = Logger.getLogger(DBConnection.class.getName());

    public static Connection getConnection() {
        Connection connection = null;
        try {
            String user = "root";
            String pw = "mocha127";
            String url = "jdbc:mariadb://localhost:3306/minisns";

            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pw);
            log.info("connected mariadb");
            return connection;
        } catch (ClassNotFoundException ex) {
            log.warning("cannot found mariadb driver");
        } catch (SQLException ex) {
            log.warning("cannot connected mariadb");
        }
        System.exit(-1);
        return null;
    }

}
