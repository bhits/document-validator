/**
 * Created by jiahao.li on 4/14/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.validators;

import gov.samhsa.mhc.documentvalidator.service.dto.DocumentValidationResult;

import java.io.InputStream;
import java.util.ArrayList;

public interface CCDAValidator {
    ArrayList<DocumentValidationResult> validateCCDA(InputStream ccdaFile) throws Exception;
}

