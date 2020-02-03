package de.fhdortmund.j2t2.wise2019.server.user.register;

import de.fhdortmund.j2t2.wise2019.server.user.UserManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("register")
public class Register {

    @Inject
    private UserManager userManager;

    @POST
    @Consumes("application/json")
    public void registerUser(NewUserData user) {
        try {
            userManager.createUser(user);
        } catch (UserAlreadyExistsException e) {
            throw new NotAuthorizedException(e.getMessage());
        } finally {
            user.clean();
        }
    }
}
