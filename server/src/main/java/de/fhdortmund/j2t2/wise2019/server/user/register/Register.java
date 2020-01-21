package de.fhdortmund.j2t2.wise2019.server.user.register;

import de.fhdortmund.j2t2.wise2019.server.commons.util.HashUtil;
import de.fhdortmund.j2t2.wise2019.server.user.UserEntity;
import de.fhdortmund.j2t2.wise2019.server.user.UserManager;
import de.fhdortmund.j2t2.wise2019.server.user.UserManagerBean;
import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Path("register")
public class Register {

    private UserManager userManager = UserManagerBean.getInstance();

    @POST
    @Consumes("application/json")
    public void registerUser(LoginCredentials user) throws NoSuchAlgorithmException {
        userManager.createUser(user);
        user.clean();
    }
}
