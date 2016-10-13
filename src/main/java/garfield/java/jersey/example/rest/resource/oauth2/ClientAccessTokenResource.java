package garfield.java.jersey.example.rest.resource.oauth2;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.httpclient4.HttpClient4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import garfield.java.jersey.example.common.constant.OAuthConstant;
import garfield.java.jersey.example.common.model.OAuthParam;
import garfield.java.jersey.example.common.util.OAuthUtil;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("login/{oauthProvider}/oauth2callback")
@Singleton
public class ClientAccessTokenResource {
    
    @GET
    public Response accessToken(@PathParam("oauthProvider") String provider, @Context HttpServletRequest request) throws OAuthProblemException, OAuthSystemException, JsonProcessingException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        
        OAuthAuthzResponse oauth = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
        // get Authorization Code
        String code = oauth.getCode();
        
        if(!OAuthUtil.verifyState(oauth.getState()))
            throw new IllegalArgumentException("Illegal state info returned from server");
        
        OAuthParam param = OAuthParam.constructByProvider(provider);
        
        OAuthClientRequest oauthRequest = OAuthClientRequest
                .tokenLocation(param.getTokenEndpoint())
                .setClientId(param.getCliendId())
                .setClientSecret(param.getClientSecret())
                .setCode(code)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setRedirectURI(param.getRedirectUrl())
                .buildBodyMessage();
        
        HttpHost proxy = new HttpHost("webproxy.wlb.nsroot.net", 8080, "http");
        HttpRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(new TrustStrategy(){
                    @Override
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }})
                .build();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier()))
                    .build();
        HttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient closeableClient = HttpClients.custom()
                            .setRoutePlanner(routePlanner)
                            .setSSLContext(sslContext)
                            .setConnectionManager(manager)
                            .build();
        
        //create OAuth client which use custom http client under the hood
        OAuthClient oAuthClient = new OAuthClient(new HttpClient4(closeableClient));
        
        String accessToken = null;
        if(StringUtils.equalsIgnoreCase(provider, OAuthConstant.GITHUB)) {
            GitHubTokenResponse githubResponse = oAuthClient.accessToken(oauthRequest, GitHubTokenResponse.class);
            accessToken = githubResponse.getAccessToken();
        } else if(StringUtils.equalsIgnoreCase(provider, OAuthConstant.GOOGLE)) {
            OAuthJSONAccessTokenResponse googleResponse = oAuthClient.accessToken(oauthRequest);
            accessToken = googleResponse.getAccessToken();
        }
        //retrieve user info
        final OAuthClientRequest clientRequest = new OAuthBearerClientRequest(param.getResourceUrl())
                .setAccessToken(accessToken)
                .buildQueryMessage();
        OAuthResourceResponse response = oAuthClient.resource(clientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response.getBody());
        String userId = node.path("id").asText();
        String userName = node.path("name").asText();
        
        return Response.ok("{userId: " + userId + ", userName: " + userName + "}").build();
    }
}
