package de.fhdortmund.j2t2.wise2019.server.user.register;

import de.fhdortmund.j2t2.wise2019.server.user.Credentials;

import javax.xml.bind.annotation.XmlRootElement;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@XmlRootElement
public class NewUserData implements Credentials {
    private char[] password;
    private String username;

    public char[] getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void clean(){
        eraseCharArray(password);
    }

    private void eraseCharArray(char[] password){
        for(int i=0; i<password.length; i++){
            try {
                password[i] = Character.highSurrogate(SecureRandom.getInstanceStrong().nextInt());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
