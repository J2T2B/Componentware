package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;

public interface UserManager {
    void createUser(NewUserData user);
    User getUser(String name);
}
