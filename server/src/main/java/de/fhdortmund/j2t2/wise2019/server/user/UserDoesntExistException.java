package de.fhdortmund.j2t2.wise2019.server.user;

public class UserDoesntExistException extends Exception {
    public UserDoesntExistException(String username) {
        super(username);
    }
}
