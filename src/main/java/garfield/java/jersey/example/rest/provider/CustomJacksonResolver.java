package garfield.java.jersey.example.rest.provider;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class CustomJacksonResolver implements ContextResolver<ObjectMapper>{
    
    @Override
    public ObjectMapper getContext(Class<?> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        return objectMapper;
    }
}
