package gov.samhsa.c2s.documentvalidator.infrastructure;

import gov.samhsa.c2s.documentvalidator.service.dto.DocumentValidationResultDetail;

import java.util.List;
import java.util.Optional;

public interface CcdaValidator {
    List<DocumentValidationResultDetail> validateCCDA(byte[] document, Optional<String> documentEncoding);
}
