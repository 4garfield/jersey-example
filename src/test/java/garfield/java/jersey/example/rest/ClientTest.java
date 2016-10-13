package garfield.java.jersey.example.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientTest extends JerseyTest {
    
    private static final String JSON = MediaType.APPLICATION_JSON;
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        
        return new ResourceConfig();
    }
    
    @Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }
    
    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.builder(configure())
                .contextPath("JerseyExample")
                .initParam("javax.ws.rs.Application", "garfield.java.jersey.example.initial.config.JerseyConfig")
                .addListener(ContextLoaderListener.class)
                .addListener(RequestContextListener.class)
                .contextParam("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext")
                .contextParam("contextConfigLocation", "garfield.java.jersey.example.initial.config.SpringConfig")
                .build();
    }
    
    @Override
    protected void configureClient(final ClientConfig config) {
        config.register(JacksonFeature.class);
        config.register(GZipReaderInterceptor.class);
    }
    
    private Invocation.Builder request(final String path) {
        return target().path(path).request(JSON);
    }
    
    private void checkResponse(Response response, int correctStatusCode) {
        if(response.getStatus() != correctStatusCode)
            if(response.hasEntity())
                System.out.println("the exception response entity:\n" + response.readEntity(String.class));
    }
    
    public void testGet(final String path, final Class<?> type) {
        Response response = request(path).get();
        checkResponse(response, 200);
        try {
            System.out.println(mapper.writeValueAsString(response.readEntity(type)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
    public void testPost(final String path, final Object entity) {
        Response response = request(path).post(Entity.entity(entity, JSON));
        checkResponse(response, 201);
    }
    
    public void testPut(final String path, final Object entity) {
        Response response = request(path).put(Entity.entity(entity, JSON));
        checkResponse(response, 200);
    }
    
    public void testDelete(final String path) {
        Response response = request(path).delete();
        checkResponse(response, 204);
    }
    
}
