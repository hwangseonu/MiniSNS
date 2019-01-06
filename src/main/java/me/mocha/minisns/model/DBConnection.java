package me.mocha.minisns.model;

import java.sql.*;
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
            return connection;
        } catch (ClassNotFoundException ex) {
            log.warning("cannot found mariadb driver");
        } catch (SQLException ex) {
            log.warning("cannot connected mariadb");
        }
        System.exit(-1);
        return null;
    }

    public static void closeResources(Connection conn, PreparedStatement pstmt) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

}
