package de.fhdortmund.j2t2.wise2019.server.user;

import java.util.Arrays;
import java.util.Objects;

class LoginCredentials {
    private char[] password;
    private String username;

    public char[] getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginCredentials that = (LoginCredentials) o;
        return Arrays.equals(password, that.password) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}
