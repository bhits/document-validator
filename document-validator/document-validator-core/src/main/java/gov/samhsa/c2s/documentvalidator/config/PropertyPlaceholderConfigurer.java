package gov.samhsa.c2s.documentvalidator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by Jiahao.Li on 7/11/2016.
 */

@Configuration
@PropertySource("classpath:META-INF/spring/validator.properties")
public class PropertyPlaceholderConfigurer {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
