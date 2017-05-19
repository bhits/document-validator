package gov.samhsa.c2s.documentvalidator.infrastructure;

import gov.samhsa.c2s.documentvalidator.infrastructure.exception.CcdaValidatorRunningException;
import gov.samhsa.c2s.documentvalidator.service.dto.DocumentValidationResultDetail;
import gov.samhsa.c2s.documentvalidator.service.schema.ValidationDiagnosticType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.mdht.uml.cda.DocumentRoot;
import org.eclipse.mdht.uml.cda.util.CDADiagnostic;
import org.eclipse.mdht.uml.cda.util.CDAUtil;
import org.eclipse.mdht.uml.cda.util.ValidationResult;
import org.eclipse.mdht.uml.cda.validate.XPathIndexer;
import org.openhealthtools.mdht.uml.cda.consol.ConsolPackage;
import org.openhealthtools.mdht.uml.cda.mu2consol.Mu2consolPackage;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CcdaValidatorImpl implements CcdaValidator {
    private static final String IG_ISSUE_ID = "a.consol";

    @PostConstruct
    public void buildStaticPackageRegistration() {
        // Validate on load and create validation result to hold diagnostics
        Mu2consolPackage.eINSTANCE.unload();
        // Access (or initialize) the R2.1 or R1.1 singleton instance (without MU2 certification)
        ConsolPackage.eINSTANCE.eClass();
    }

    @Override
    public List<DocumentValidationResultDetail> validateCCDA(String ccdaDocument) {
        ValidationResult result = new ValidationResult();
        XPathIndexer xpathIndexer = new XPathIndexer();
        try {
            processParseXMLDocument(xpathIndexer, ccdaDocument);

            InputStream ccdaDocumentStream = new ByteArrayInputStream(ccdaDocument.getBytes());
            log.info("Using validation criteria: " + ValidationCriteria.C_CDA_IG_ONLY);
            CDAUtil.load(ccdaDocumentStream, result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CcdaValidatorRunningException(e);
        }
        return analysisValidationResults(xpathIndexer, result);
    }

    private List<DocumentValidationResultDetail> analysisValidationResults(final XPathIndexer xpathIndexer,
                                                                           ValidationResult result) {
        List<DocumentValidationResultDetail> results;

        results = result.getErrorDiagnostics().stream()
                .map(diagnostic -> buildValidationResult(diagnostic, xpathIndexer, ValidationDiagnosticType.CCDA_ERROR))
                .collect(Collectors.toList());

        results.addAll(result.getWarningDiagnostics().stream()
                .map(diagnostic -> buildValidationResult(diagnostic, xpathIndexer, ValidationDiagnosticType.CCDA_WARN))
                .collect(Collectors.toList()));
        results.addAll(result.getInfoDiagnostics().stream()
                .map(diagnostic -> buildValidationResult(diagnostic, xpathIndexer, ValidationDiagnosticType.CCDA_INFO))
                .collect(Collectors.toList()));

        return results;
    }

    private DocumentValidationResultDetail buildValidationResult(Diagnostic diagnostic, XPathIndexer xPathIndexer,
                                                                 ValidationDiagnosticType resultType) {
        DocumentValidationResultDetail resultDetail = new DocumentValidationResultDetail();
        CDADiagnostic cdaDiag = new CDADiagnostic(diagnostic);

        resultDetail.setDescription(cdaDiag.getMessage());
        resultDetail.setDiagnosticType(resultType);
        resultDetail.setXPath(cdaDiag.getPath());
        resultDetail.setDocumentLineNumber(getLineNumberInXML(xPathIndexer, diagnostic));

        if (cdaDiag.getSource().contains(IG_ISSUE_ID)) {
            resultDetail.setIGIssue(true);
        } else if (resultType == ValidationDiagnosticType.CCDA_ERROR) {
            //schema - errors only
            resultDetail.setSchemaError(true);
        }
        return resultDetail;
    }

    private String getLineNumberInXML(final XPathIndexer xpathIndexer, Diagnostic diagnostic) {
        String generatedPath = "";
        if (diagnostic.getData().size() > 0 && diagnostic.getData().get(0) instanceof EObject) {
            generatedPath = getPath((EObject) diagnostic.getData().get(0));
        }
        XPathIndexer.ElementLocationData eld = xpathIndexer.getElementLocationByPath(generatedPath.toUpperCase());
        return eld != null ? Integer.toString(eld.line) : "Line number not available";
    }

    private String getPath(EObject eObject) {
        StringBuilder path = new StringBuilder();
        while (eObject != null && !(eObject instanceof DocumentRoot)) {
            EStructuralFeature feature = eObject.eContainingFeature();
            EObject container = eObject.eContainer();
            Object value = container.eGet(feature);
            if (feature.isMany()) {
                List<?> list = (List<?>) value;
                int index = list.indexOf(eObject) + 1;
                path.insert(0, "/" + feature.getName() + "[" + index + "]");
            } else {
                path.insert(0, "/" + feature.getName() + "[1]");
            }
            eObject = eObject.eContainer();
        }
        return path.toString();
    }

    private void processParseXMLDocument(XPathIndexer xpathIndexer, String xmlString) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(xpathIndexer);
        try {
            InputSource inputSource = new InputSource(new StringReader(xmlString));
            parser.parse(inputSource);
        } catch (Exception e) {
            log.info("Error In Parsing document: Bad filename, path or invalid document." + e.getMessage());
            log.debug(e.getMessage(), e);
            throw new CcdaValidatorRunningException(e);
        }
    }
}
