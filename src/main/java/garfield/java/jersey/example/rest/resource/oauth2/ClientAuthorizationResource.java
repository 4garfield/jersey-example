package garfield.java.jersey.example.rest.resource.oauth2;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import garfield.java.jersey.example.common.model.OAuthParam;
import garfield.java.jersey.example.common.util.OAuthUtil;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("login/{oauthProvider}")
@Singleton
public class ClientAuthorizationResource {
    
    @GET
    // Create the OAuth End User Authorization Request, in order to receive the authorization code
    public Response authorizationCode(@PathParam("oauthProvider") String provider) throws IllegalArgumentException, UriBuilderException, OAuthSystemException, URISyntaxException {
        
        OAuthParam param = OAuthParam.constructByProvider(provider);
        
        // produce an OAuth request which all the parameters are encoded in the URL query
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(param.getAuthzEndpoint())
                .setClientId(param.getCliendId())
                .setRedirectURI(param.getRedirectUrl())
                .setResponseType(OAuth.OAUTH_CODE)
                .setScope(param.getScope())
                .setState(OAuthUtil.generateState())
                .buildQueryMessage();
        
        // obtain the generated OAuth request URL
        URI redirect = new URI(request.getLocationUri());
        
        // redirect to access token
        return Response.seeOther(redirect).build();
    }
    
}
