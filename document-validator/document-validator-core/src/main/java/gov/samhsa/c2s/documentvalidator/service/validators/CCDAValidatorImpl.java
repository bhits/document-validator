package gov.samhsa.c2s.documentvalidator.service.validators;

import gov.samhsa.c2s.documentvalidator.service.dto.DiagnosticType;
import gov.samhsa.mhc.common.log.Logger;
import gov.samhsa.mhc.common.log.LoggerFactory;
import gov.samhsa.c2s.documentvalidator.service.dto.DocumentValidationResult;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.openhealthtools.mdht.uml.cda.DocumentRoot;
import org.openhealthtools.mdht.uml.cda.consol.ConsolPackage;
import org.openhealthtools.mdht.uml.cda.util.CDADiagnostic;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;
import org.openhealthtools.mdht.uml.cda.validate.XPathIndexer;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CCDAValidatorImpl implements CCDAValidator {
    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
    private Logger logger = LoggerFactory.getLogger(this);

    @PostConstruct
    public void buildStaticPackageRegistration() {
        ConsolPackage.eINSTANCE.eClass();
    }

    @Override
    public ArrayList<DocumentValidationResult> validateCCDA(byte[] ccdaFile, Optional<String> documentEncoding) throws Exception {
        // validate on load
        // create validation result to hold diagnostics
        ValidationResult result = new ValidationResult();

        final XPathIndexer xpathIndexer = new XPathIndexer();

        try {
            InputStream fileStream = new ByteArrayInputStream(ccdaFile);
            String fileString = new String(ccdaFile, getCharset(documentEncoding));
            CDAUtil.load(fileStream, result);
            parseXMLDocument(xpathIndexer, fileString);
        } catch (Exception e) {
            logger.info(() -> "Load validation failed: " + e.getMessage());
            logger.debug(e::getMessage, e);
        }
        return processValidationResults(xpathIndexer, result);
    }

    private Charset getCharset(Optional<String> documentEncoding) {
        return documentEncoding.map(Charset::forName).orElse(DEFAULT_ENCODING);
    }

    private ArrayList<DocumentValidationResult> processValidationResults(final XPathIndexer xpathIndexer,
                                                                         ValidationResult result) {
        ArrayList<DocumentValidationResult> results = result.getErrorDiagnostics()
                .stream()
                .map(diagnostic -> buildValidationResult(diagnostic, xpathIndexer, DiagnosticType.CCDA_ERROR))
                .collect(Collectors.toCollection(ArrayList::new));

        results.addAll(result.getWarningDiagnostics()
                .stream()
                .map(diagnostic -> buildValidationResult(diagnostic, xpathIndexer, DiagnosticType.CCDA_WARN))
                .collect(Collectors.toList()));

        results.addAll(result.getInfoDiagnostics()
                .stream()
                .map(diagnostic -> buildValidationResult(diagnostic, xpathIndexer, DiagnosticType.CCDA_INFO))
                .collect(Collectors.toList()));
        return results;
    }

    private DocumentValidationResult buildValidationResult(Diagnostic diagnostic, XPathIndexer xPathIndexer,
                                                           DiagnosticType resultType) {
        CDADiagnostic diag = new CDADiagnostic(diagnostic);
        String lineNumber = getLineNumberInXML(xPathIndexer, diagnostic);
        return wrapValidationResult(diag, resultType, lineNumber);
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
        String path = "";
        while (eObject != null && !(eObject instanceof DocumentRoot)) {
            EStructuralFeature feature = eObject.eContainingFeature();
            EObject container = eObject.eContainer();
            Object value = container.eGet(feature);
            if (feature.isMany()) {
                List<?> list = (List<?>) value;
                int index = list.indexOf(eObject) + 1;
                path = "/" + feature.getName() + "[" + index + "]" + path;
            } else {
                path = "/" + feature.getName() + "[1]" + path;
            }
            eObject = eObject.eContainer();
        }
        return path;
    }

    private void parseXMLDocument(XPathIndexer xpathIndexer, String xmlString) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(xpathIndexer);
        try {
            InputSource inputSource = new InputSource(new StringReader(xmlString));
            parser.parse(inputSource);
        } catch (Exception e) {
            logger.info(() -> "Error In Line Number Routine: Bad filename, path or invalid document." + e.getMessage());
            logger.debug(e::getMessage, e);
        }
    }

    private DocumentValidationResult wrapValidationResult(CDADiagnostic cdaDiag, DiagnosticType resultType,
                                                          String lineNumber) {
        return new DocumentValidationResult(resultType, cdaDiag.getMessage(), cdaDiag.getPath(), lineNumber);
    }
}
