package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

import javax.ejb.Stateful;
import java.util.Arrays;
import java.util.List;

@Stateful
public class UserManager {
    public List<Chat> getChatsForUser(String token) {
        return null;
    }
}
