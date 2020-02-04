package de.fhdortmund.j2t2.wise2019.server.persistence.fallback;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public interface Database {

    <T> T execute(SqlExecution<T> exec) throws SQLException;

    @FunctionalInterface
    interface SqlExecution<T> {

        T execute(Statement stmt) throws SQLException;

    }
}
