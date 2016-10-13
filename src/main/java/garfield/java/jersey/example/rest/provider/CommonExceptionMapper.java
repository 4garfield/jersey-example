package garfield.java.jersey.example.rest.provider;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<Throwable> {
    
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(Throwable exception) {
        if(exception instanceof WebApplicationException) {
            int statusCode = ((WebApplicationException) exception).getResponse().getStatus();
            return Response.status(statusCode).entity(exception.getMessage()).build();
        }
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(writer.toString()).build();
    }
}
