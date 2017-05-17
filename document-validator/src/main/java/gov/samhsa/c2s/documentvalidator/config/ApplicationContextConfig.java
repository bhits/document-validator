package gov.samhsa.c2s.documentvalidator.config;

import gov.samhsa.c2s.common.document.accessor.DocumentAccessor;
import gov.samhsa.c2s.common.document.accessor.DocumentAccessorImpl;
import gov.samhsa.c2s.common.document.converter.DocumentXmlConverter;
import gov.samhsa.c2s.common.document.converter.DocumentXmlConverterImpl;
import gov.samhsa.c2s.common.validation.XmlValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextConfig {
    private static final String C32_CDA_XSD_PATH = "schema/cdar2c32/infrastructure/cda/";
    private static final String C32_CDA_XSD_NAME = "C32_CDA.xsd";

    @Bean
    public DocumentAccessor documentAccessor() {
        return new DocumentAccessorImpl();
    }

    @Bean
    public DocumentXmlConverter documentXmlConverter() {
        return new DocumentXmlConverterImpl();
    }

    @Bean
    public XmlValidation xmlValidation(){
        return new XmlValidation(this.getClass().getClassLoader()
                .getResourceAsStream(C32_CDA_XSD_PATH + C32_CDA_XSD_NAME), C32_CDA_XSD_PATH);
    }
}