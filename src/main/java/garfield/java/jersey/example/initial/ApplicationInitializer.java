package garfield.java.jersey.example.initial;

import javax.annotation.Priority;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;  

@Priority(value = 1)
public class ApplicationInitializer implements WebApplicationInitializer {
    
    @Override
	public void onStartup(ServletContext container) throws ServletException {
        
	    // create the root Spring application context
        container.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
        container.setInitParameter("contextConfigLocation", "garfield.java.jersey.example.initial.config.SpringConfig");
        
        // manage the lifecycle of the root application context
        container.addListener(ContextLoaderListener.class);
        
        // spring hateoas depends on the current HTTP request(exception: Could not find current request via RequestContextHolder)
        container.addListener(RequestContextListener.class);
        
        // add jersey servlet
        ServletRegistration.Dynamic rest = container.addServlet("rest", ServletContainer.class);
        rest.setInitParameter("javax.ws.rs.Application", "garfield.java.jersey.example.initial.config.JerseyConfig");
        rest.addMapping("/*");
        rest.setLoadOnStartup(1);
    }
    
}
