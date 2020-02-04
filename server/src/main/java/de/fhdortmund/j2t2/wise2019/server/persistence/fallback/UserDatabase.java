package de.fhdortmund.j2t2.wise2019.server.persistence.fallback;

import de.fhdortmund.j2t2.wise2019.server.user.User;
import de.fhdortmund.j2t2.wise2019.server.user.UserDoesntExistException;
import de.fhdortmund.j2t2.wise2019.server.user.register.UserAlreadyExistsException;

public interface UserDatabase {
    boolean createUser(String username, String passwordHash) throws UserAlreadyExistsException;

    User loadUser(String key);
}
