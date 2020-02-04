package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;
import de.fhdortmund.j2t2.wise2019.server.user.register.UserAlreadyExistsException;

import java.security.NoSuchAlgorithmException;

public interface UserManager {
    void createUser(NewUserData user) throws UserAlreadyExistsException, NoSuchAlgorithmException;
    User getUser(String name);
    boolean login(LoginCredentials credentials) throws UserDoesntExistException, NoSuchAlgorithmException;
}
