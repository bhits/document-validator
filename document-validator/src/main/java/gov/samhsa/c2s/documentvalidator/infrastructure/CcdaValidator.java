package gov.samhsa.c2s.documentvalidator.infrastructure;

import gov.samhsa.c2s.documentvalidator.service.dto.DocumentValidationResultDetail;

import java.util.List;

public interface CcdaValidator {
    List<DocumentValidationResultDetail> validateCCDA(String ccdaDocument);
}
