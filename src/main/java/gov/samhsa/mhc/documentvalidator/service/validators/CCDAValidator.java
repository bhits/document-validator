/**
 * Created by jiahao.li on 4/14/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.validators;

import gov.samhsa.mhc.documentvalidator.service.DocumentValidationResult;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public interface CCDAValidator {
    ArrayList<DocumentValidationResult> validateCCDA(String ccdaFile) throws SAXException;
}

