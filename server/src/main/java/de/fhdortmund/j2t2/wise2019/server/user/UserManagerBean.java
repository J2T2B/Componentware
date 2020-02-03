package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;
import de.fhdortmund.j2t2.wise2019.server.user.register.UserAlreadyExistsException;

import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
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

    public List<Chat> getChatsForUser(String token) {
        return new ArrayList<>(); // TODO
    }

    @Override
    public void createUser(NewUserData user) throws UserAlreadyExistsException {
        if(users.containsKey(user.getUsername())){
            throw new UserAlreadyExistsException("User with name " + user.getUsername() + "already exists");
        }
        users.put(user.getUsername(), new DefaultUserImpl(user.getUsername(), hashUserData(user)));
    }

    @Override
    public User getUser(String name) {
        return users.get(name);
    }

    @Override
    public boolean login(LoginCredentials credentials) throws UserDoesntExistException {
        User target = users.get(credentials.getUsername());
        if(target == null){
            throw new UserDoesntExistException(credentials.getUsername());
        }
        return hashUserData(credentials).equals(target.getHash());
    }

    private String hashUserData(Credentials user) {
        return new String(user.getPassword());
    }

    /*public static UserManagerBean getInstance(){
        if(that == null){
            that = new UserManagerBean();
        }

        return that;
    }*/
}
