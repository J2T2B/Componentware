package de.fhdortmund.j2t2.wise2019.server.user.login;

import de.fhdortmund.j2t2.wise2019.server.user.UserDoesntExistException;
import de.fhdortmund.j2t2.wise2019.server.user.UserManager;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManager;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("login")
public class LoginEndpoint {

    @Inject
    private UserManager userManager;

    @Inject
    private SessionManager sessionManager;

    @PUT
    @Consumes("application/json")
    @Produces("plain/text")
    public String login(LoginCredentials loginCredentials){
        String sessionToken;
        boolean success = false;
        try {
            success = userManager.login(loginCredentials);
        } catch (UserDoesntExistException e) {
            throw new NotAuthorizedException("False username or password!");
        } catch (Exception e){
            throw new InternalServerErrorException(e);
        } finally {
            loginCredentials.clean();
        }


        if(success){
            sessionToken = sessionManager.createSession(userManager.getUser(loginCredentials.getUsername()));
        } else {
            throw new NotAuthorizedException("False username or password!");
        }
        return sessionToken;
    }


}
