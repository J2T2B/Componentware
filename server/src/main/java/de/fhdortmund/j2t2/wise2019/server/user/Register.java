package de.fhdortmund.j2t2.wise2019.server.user;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Path("/api/register")
public class Register {

    @Inject
    private UserEntity userEntity;
    @POST
    public void registerUser(NewUserData user){

    }

    private void cleanArray(char[] array){
        Arrays.fill(array, (char) 0);
    }

    private String createHash(NewUserData user) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return ""; //TODO
    }
}
