package de.fhdortmund.j2t2.wise2019.server.persistence.fallback;

import de.fhdortmund.j2t2.wise2019.server.user.DefaultUserImpl;
import de.fhdortmund.j2t2.wise2019.server.user.User;
import de.fhdortmund.j2t2.wise2019.server.user.UserDoesntExistException;
import de.fhdortmund.j2t2.wise2019.server.user.register.UserAlreadyExistsException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
@Named
@ApplicationScoped
public class UserDatabaseBean implements UserDatabase {

    private static final String USER_TABLE = "users";
    private static final String NAME_COLUMN = "name";
    private static final String PASSWORD_COLUMN = "pass";

    @Inject
    private Database database;

    @PostConstruct
    public void initUserTable() {
        try {
            database.execute(stmt -> stmt.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s (%s VARCHAR(256) PRIMARY KEY, %s VARCHAR(256) NOT NULL)", USER_TABLE, NAME_COLUMN, PASSWORD_COLUMN)));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to init user table", e);
        }
    }

    @Override
    public boolean createUser(String username, String passwordHash) throws UserAlreadyExistsException {
        try {
            if(database.execute(stmt -> {
                int res = stmt.executeUpdate(createInsertCommand(username, passwordHash));
                return res == 1;
            })) {
                return true;
            } else {
                throw new UserAlreadyExistsException(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User loadUser(String userName) {
        try {
            return database.execute(stmt -> {
               ResultSet rs = stmt.executeQuery(createSelectCommand(userName));
               if(rs.next()) {
                   String loadName = rs.getString(NAME_COLUMN);
                   String loadPassHash = rs.getString(PASSWORD_COLUMN);
                   return new DefaultUserImpl(loadName, loadPassHash);
               }
               return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String createInsertCommand(String username, String passwordHash) {
        return String.format("INSERT INTO %s(%s,%s) VALUES('%s','%s')", USER_TABLE, NAME_COLUMN, PASSWORD_COLUMN, username, passwordHash);
    }

    private String createSelectCommand(String username) {
        return String.format("SELECT * FROM %s WHERE %s='%s'", USER_TABLE, NAME_COLUMN, username);
    }
}