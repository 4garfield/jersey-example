package garfield.java.jersey.example.rest.provider;

import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

@Provider
public class MonitorApplicationEvent implements ApplicationEventListener{
    
    private static final Long startTime = System.currentTimeMillis();
    private static final Log logger = LogFactory.getLog(MonitorApplicationEvent.class);
    
    @Override
    public void onEvent(ApplicationEvent event) {
        String appName = event.getResourceConfig().getApplicationName();
        
        switch(event.getType()) {
            case INITIALIZATION_START : 
                logger.info("......" + appName + " initial start......");
                break;
            case INITIALIZATION_APP_FINISHED : 
                logger.info("jersey application initial finished, using " + (System.currentTimeMillis()-startTime) + " ms.");
                break;
            case INITIALIZATION_FINISHED : 
                logger.info("......" + appName + " initial finished, using " + (System.currentTimeMillis()-startTime) + " ms.");
                break;
            case DESTROY_FINISHED : 
                logger.info("......" + appName + " destroy finished......");
                break;
            case RELOAD_FINISHED : 
                logger.info("......" + appName + " reload finished......");
                break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return new RequestEventListener(){
            private final Long requestStartTime = System.currentTimeMillis();
            @Override
            public void onEvent(RequestEvent event) {
                switch(event.getType()) {
                    case MATCHING_START : 
                        logger.info("resource url: " + event.getUriInfo().getAbsolutePath().toString());
                        break;
                    case FINISHED : 
                        logger.info("resource method execute finished, using " + (System.currentTimeMillis()-requestStartTime) + " ms.");
                        break;
                    default : 
                        break;
                }
            }};
    }

}
