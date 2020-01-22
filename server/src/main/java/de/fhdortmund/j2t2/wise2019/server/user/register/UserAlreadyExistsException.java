package de.fhdortmund.j2t2.wise2019.server.user.register;


public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String m) {
        super(m);
    }
}
