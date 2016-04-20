package gov.samhsa.mhc.documentvalidator.service.validators;

import gov.samhsa.mhc.documentvalidator.service.dto.DiagnosticType;
import gov.samhsa.mhc.documentvalidator.service.dto.DocumentValidationResult;
import org.eclipse.emf.common.util.Diagnostic;
import org.openhealthtools.mdht.uml.cda.consol.ConsolPackage;
import org.openhealthtools.mdht.uml.cda.util.CDADiagnostic;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CCDAValidatorImpl implements CCDAValidator {

    @PostConstruct
    public void buildStaticPackageRegistration() {
        ConsolPackage.eINSTANCE.eClass();
    }

    @Override
    public ArrayList<DocumentValidationResult> validateCCDA(InputStream ccdaFile) throws SAXException {

        // validate on load
        // create validation result to hold diagnostics
        ValidationResult result = new ValidationResult();

        try {
            CDAUtil.load(ccdaFile, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processValidationResults(result);
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
