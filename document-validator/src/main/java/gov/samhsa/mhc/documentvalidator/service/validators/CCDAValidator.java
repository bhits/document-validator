/**
 * Created by jiahao.li on 4/14/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.validators;

import gov.samhsa.mhc.documentvalidator.service.dto.DocumentValidationResult;

import java.util.ArrayList;
import java.util.Optional;

public interface CCDAValidator {
    ArrayList<DocumentValidationResult> validateCCDA(byte[] ccdaFile, Optional<String> documentEncoding) throws Exception;
}

