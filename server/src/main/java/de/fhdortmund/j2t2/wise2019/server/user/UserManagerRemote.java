package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;

import javax.ejb.Remote;

@Remote
public interface UserManagerRemote extends UserManager{

    boolean login(LoginCredentials credentials);
}
