package garfield.java.jersey.example.rest.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResourceSupportImpl<T> extends ResourceSupport{
    
    private T content;
    
    public BaseResourceSupportImpl() {
        super();
    }
    
    @JsonCreator
    public BaseResourceSupportImpl(@JsonProperty("content") T content) {
        super();
        this.content = content;
    }
    
    public T getContent() {
        return content;
    }
    
    public void setContent(T content) {
        this.content = content;
    }
    
}
