package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import de.fhdortmund.j2t2.wise2019.server.user.User;

public interface SessionManager {
    String createSession(User user);
}
