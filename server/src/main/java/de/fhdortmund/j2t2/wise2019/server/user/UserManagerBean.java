package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.persistence.fallback.UserDatabase;
import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;
import de.fhdortmund.j2t2.wise2019.server.user.register.UserAlreadyExistsException;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Named
@Dependent
public class UserManagerBean implements UserManagerRemote, UserManagerLocal {
    Map<String, User> users = new HashMap<>();

    @Inject
    private UserDatabase userDatabase;

    @Override
    public void createUser(NewUserData user) throws UserAlreadyExistsException {
        if(getUser(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User with name " + user.getUsername() + "already exists");
        }
        String passwordHash = createHash(user.getPassword());
        userDatabase.createUser(user.getUsername(), passwordHash);
        users.put(user.getUsername(), new DefaultUserImpl(user.getUsername(), passwordHash));
    }

    @Override
    public User getUser(String name) {
        return users.computeIfAbsent(name, key -> userDatabase.loadUser(key));
    }

    @Override
    public boolean login(LoginCredentials credentials) throws UserDoesntExistException {
        User target = getUser(credentials.getUsername());
        if(target == null){
            throw new UserDoesntExistException(credentials.getUsername());
        }
        return createHash(credentials.getPassword()).equals(target.getPasswordHash());
    }

    private String createHash(char[] password) {
        return new String(password);
    }

    /*public static UserManagerBean getInstance(){
        if(that == null){
            that = new UserManagerBean();
        }

        return that;
    }*/
}
