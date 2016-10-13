package garfield.java.jersey.example.common.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResponseUtil {
    
    public static Response buildBadRequest(String message) {
        return Response.status(Status.BAD_REQUEST).entity(message).build();
    }
    
    public static Response buildNotFound() {
        return Response.status(Status.NOT_FOUND).entity("cannot find the request resource").build();
    }
}
