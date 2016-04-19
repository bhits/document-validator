package gov.samhsa.mhc.documentvalidator.config;

import org.openhealthtools.mdht.uml.cda.util.ValidationResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class ApplicationContextConfig {

    @Bean
    public ValidationResult validationResult() {
        return new ValidationResult();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }
}
