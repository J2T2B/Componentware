package de.fhdortmund.j2t2.wise2019.server.user.register;

import de.fhdortmund.j2t2.wise2019.server.commons.util.HashUtil;
import de.fhdortmund.j2t2.wise2019.server.user.UserEntity;
import de.fhdortmund.j2t2.wise2019.server.user.UserManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Path("register")
public class Register {

    @Inject
    private UserManager userManager;

    @POST
    @Consumes("application/json")
    public void registerUser(NewUserData user) throws NoSuchAlgorithmException {
        userManager.createUser(user);
        user.clean();
    }
}
