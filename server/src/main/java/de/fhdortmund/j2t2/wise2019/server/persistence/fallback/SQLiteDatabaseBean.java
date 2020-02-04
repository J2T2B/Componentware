package de.fhdortmund.j2t2.wise2019.server.persistence.fallback;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.sql.*;

@Singleton
@Named
@ApplicationScoped
public class SQLiteDatabaseBean implements Database {

    private Connection connection;

    @PostConstruct
    public synchronized void connect()  {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:D:/Studium/WildFly/cw_test.db");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to SQLiteDB", e);
        }
    }

    public synchronized <T> T execute(SqlExecution<T> exec) throws SQLException {
        try {
            Statement stmt = connection.createStatement();
            T res = exec.execute(stmt);
            stmt.close();
            connection.commit();
            return res;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        }
    }

    @PreDestroy
    public synchronized void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
