package garfield.java.jersey.example.initial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import garfield.java.jersey.example.common.constant.DatabaseConstant;
import garfield.java.jersey.example.common.constant.PackageConstant;

@Configuration
@ComponentScan({PackageConstant.DAO_PACKAGR, PackageConstant.SERVICE_PACKAGE})
@Import({DatabaseConfig.class})
@PropertySource(value = {DatabaseConstant.DB_PROPERTIES})
public class SpringConfig {
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
}
