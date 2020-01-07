package de.fhdortmund.j2t2.wise2019.server.user.login;

import de.fhdortmund.j2t2.wise2019.server.user.RemoteUserManager;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManagerBean;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("login")
public class LoginEndpoint {

    @Inject
    RemoteUserManager userManager;

    @Inject
    SessionManagerBean sessionManager;

    @PUT
    @Consumes("application/json")
    @Produces("plain/text")
    public String login(LoginCredentials loginCredentials){
        String sessionToken;
        boolean success = userManager.login(loginCredentials);
        loginCredentials.clean();

        if(success){
            sessionToken = sessionManager.createSession(userManager.getUser(loginCredentials.getUsername()));
        } else {
            throw new NotAuthorizedException("False username or password!");
        }
        return sessionToken;
    }


}
