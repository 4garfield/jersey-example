package garfield.java.jersey.example.initial.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationFeature;

import garfield.java.jersey.example.common.constant.PackageConstant;

public class JerseyConfig extends ResourceConfig {
    
    public JerseyConfig() {
        
        property(ServerProperties.APPLICATION_NAME, "JerseyExample");
        
        // disable server auto discover jersey external service
        property(ServerProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
        property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
        
        // set the jersey provider and resource package
        packages(Boolean.TRUE, PackageConstant.JERSEY_PROVIDER_PACKAGE, PackageConstant.JERSEY_RESOURCE_PACKAGE);
        
        // disable jersey default moxy json provider and register jackson as json provider
        property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
        register(JacksonFeature.class);
        
        // disable Json Processing feature
        property(ServerProperties.JSON_PROCESSING_FEATURE_DISABLE, true);
        
        // register bean-validation feature
        register(ValidationFeature.class);
        property(ServerProperties.BV_FEATURE_DISABLE, false);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        // @ValidateOnExecution annotations on subclasses won't cause errors.
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        
        // register multi-part support
        register(MultiPartFeature.class);
        
        // override servlet container default http error-page
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        
        // default setting, just to highlight
        property(ServerProperties.WADL_FEATURE_DISABLE, false);
        
        // expose monitoring statistics as JMX MBeans
        property(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, true);
        
        // enable tracing support via special X-Jersey-Tracing-Accept(value can be any string, even null) HTTP request header
        property(ServerProperties.TRACING, "ON_DEMAND");
        
        // can be override by X-Jersey-Tracing-Threshold HTTP request header
        property(ServerProperties.TRACING_THRESHOLD, "TRACE");
        
    }
}
