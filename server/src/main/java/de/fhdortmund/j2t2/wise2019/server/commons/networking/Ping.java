package de.fhdortmund.j2t2.wise2019.server.commons.networking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("ping")
public class Ping {
    @GET
    public String ping(){
        return "pong";
    }
}
