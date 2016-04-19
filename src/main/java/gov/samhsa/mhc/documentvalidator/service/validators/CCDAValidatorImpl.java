/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.validators;

import gov.samhsa.mhc.documentvalidator.service.DiagnosticType;
import gov.samhsa.mhc.documentvalidator.service.DocumentValidationResult;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.Diagnostic;
import org.openhealthtools.mdht.uml.cda.consol.ConsolPackage;
import org.openhealthtools.mdht.uml.cda.util.CDADiagnostic;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CCDAValidatorImpl implements CCDAValidator {

    @Autowired
    private ValidationResult result;

    @Override
    public ArrayList<DocumentValidationResult> validateCCDA(String ccdaFile) throws SAXException {

        InputStream in = null;
        createValidationResultObjectToCollectDiagnosticsProducedDuringValidation();
        try {
            in = IOUtils.toInputStream(ccdaFile, "UTF-8");
            CDAUtil.load(in, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return processValidationResults(result);
    }

    private void createValidationResultObjectToCollectDiagnosticsProducedDuringValidation() {
        ConsolPackage.eINSTANCE.eClass();
    }

    private ArrayList<DocumentValidationResult> processValidationResults(ValidationResult result) {
        ArrayList<DocumentValidationResult> results = result.getErrorDiagnostics()
                .stream()
                .map(diagnostic -> buildValidationResult(diagnostic, DiagnosticType.CCDA_ERROR))
                .collect(Collectors.toCollection(ArrayList::new));

        results.addAll(result.getWarningDiagnostics()
                .stream()
                .map(diagnostic -> buildValidationResult(diagnostic, DiagnosticType.CCDA_WARN))
                .collect(Collectors.toList()));

        results.addAll(result.getInfoDiagnostics()
                .stream()
                .map(diagnostic -> buildValidationResult(diagnostic, DiagnosticType.CCDA_INFO))
                .collect(Collectors.toList()));
        return results;
    }

    private DocumentValidationResult buildValidationResult(Diagnostic diagnostic, DiagnosticType resultType) {
        CDADiagnostic diag = new CDADiagnostic(diagnostic);
        return createNewValidationResult(diag, resultType);
    }

    private DocumentValidationResult createNewValidationResult(CDADiagnostic cdaDiag, DiagnosticType resultType) {
        return new DocumentValidationResult(cdaDiag.getMessage(), cdaDiag.getCode(), resultType);
    }
}
