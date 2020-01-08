package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.server.user.User;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import java.util.UUID;

@Named
@Dependent
@Singleton
public class SessionManagerBean implements LocalSessionManager, RemoteSessionManager {

    @Override
    public String createSession(User user) {
        return UUID.randomUUID().toString();
    }
}
