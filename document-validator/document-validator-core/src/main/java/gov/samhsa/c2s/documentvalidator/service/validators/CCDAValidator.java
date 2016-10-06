package gov.samhsa.c2s.documentvalidator.service.validators;

import gov.samhsa.c2s.documentvalidator.service.dto.DocumentValidationResult;

import java.util.ArrayList;
import java.util.Optional;

public interface CCDAValidator {
    ArrayList<DocumentValidationResult> validateCCDA(byte[] ccdaFile, Optional<String> documentEncoding) throws Exception;
}

