package de.fhdortmund.j2t2.wise2019.server.user;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Path("/login")
public class LoginBean {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @PUT
    public String login(LoginCredentials loginCredentials){
        Credential credential = new UsernamePasswordCredential(loginCredentials.getUsername(), new Password(loginCredentials.getPassword()));
        AuthenticationStatus status = securityContext.authenticate(
            getHttpRequest(),
            getHttpServletResponse(),
            AuthenticationParameters.withParams().credential(credential)
        );
        eraseCharArray(loginCredentials.getPassword());
        return "token"; //TODO
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

    private HttpServletRequest getHttpRequest(){
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }

    private HttpServletResponse getHttpServletResponse(){
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }
}
