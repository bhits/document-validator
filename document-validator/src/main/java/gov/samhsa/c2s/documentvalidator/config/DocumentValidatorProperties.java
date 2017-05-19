package gov.samhsa.c2s.documentvalidator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "c2s.document-validator")
@Data
public class DocumentValidatorProperties {
    /**
     * Enable schematron validation
     */
    @NotNull
    private boolean enabledSchematronValidation;
}
