package garfield.java.jersey.example.rest.provider;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

public class OAuthFilter implements ContainerRequestFilter{
    
    @Context
    private HttpServletRequest request;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    }

}
