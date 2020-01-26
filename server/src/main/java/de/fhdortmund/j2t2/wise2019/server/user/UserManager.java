package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;
import de.fhdortmund.j2t2.wise2019.server.user.register.UserAlreadyExistsException;

public interface UserManager {
    void createUser(NewUserData user) throws UserAlreadyExistsException;
    User getUser(String name);
    boolean login(LoginCredentials credentials);
}
