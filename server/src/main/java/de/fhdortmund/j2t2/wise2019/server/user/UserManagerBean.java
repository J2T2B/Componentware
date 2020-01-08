package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;

import javax.ejb.Stateful;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateful
public class UserManagerBean implements UserManagerRemote, UserManagerLocal {
    Map<String, User> users = new HashMap<>();

    public List<Chat> getChatsForUser(String token) {
        return null;
    }

    @Override
    public void createUser(NewUserData user) {
        users.put(user.getUsername(), new DefaultUserImpl(user.getUsername(), hashUserData(user)));
    }

    private String hashUserData(LoginCredentials user) {
        return new String(user.getPassword());
    }

    @Override
    public User getUser(String name) {
        return users.get(name);
    }

    @Override
    public boolean login(LoginCredentials credentials) {
        User target = users.get(credentials.getUsername());

        return hashUserData(credentials).equals(target.getHash());
    }
}