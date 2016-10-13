package garfield.java.jersey.example.rest.resource;

import java.util.List;

import org.junit.Test;

import garfield.java.jersey.example.rest.ClientTest;

public class DepartmentResourceTest extends ClientTest {
    
    private static final String PATH = "department";
    
    @Test
    public void testFindAll() {
        testGet(PATH, List.class);
    }
}
