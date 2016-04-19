/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator;

import org.eclipse.emf.common.util.Diagnostic;
import org.openhealthtools.mdht.uml.cda.consol.ConsolPackage;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;

import java.io.FileInputStream;

public class CCDAValidationTool {
    public static void main(String[] args) {

        String filePathAndName = "C:/workspace/sprint/sprint10/code/C-CDA_R2-1_CCD.xml";

        ConsolPackage.eINSTANCE.eClass();

        // validate on load
        // create validation result to hold diagnostics
        ValidationResult result = new ValidationResult();

        try {
            CDAUtil.load(new FileInputStream(filePathAndName), result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("**************************** Schema Validation **********************************");
        // Schema Validation
        if (result.getSchemaValidationDiagnostics().size() == 0) {
            System.out.println("Schema Validation: Document is valid");
        } else {
            System.out.println("Schema Validation: Document is invalid");
            for (Diagnostic diagnostic : result
                    .getSchemaValidationDiagnostics()) {
                System.out.println("Schema ERROR: " + diagnostic.getMessage());
            }
        }

        System.out.println("**************************** Schematron Validation **********************************");
        // Schematron Validation
        if (!result.hasErrors()) {
            System.out.println("Schematron validation: Document is valid");
        } else {
            System.out.println("Schematron validation: Document is invalid");
        }

        System.out.println("**************************** Schematron Diagnostics **********************************");


//        System.out.println(result.getAllDiagnostics());
        System.out.println("**************************************************************");


        for (Diagnostic diagnostic : result.getErrorDiagnostics()) {
            System.out.println("ERROR: " + diagnostic.getMessage());
        }
        for (Diagnostic diagnostic : result.getWarningDiagnostics()) {
            System.out.println("WARNING: " + diagnostic.getMessage());
        }
        for (Diagnostic diagnostic : result.getInfoDiagnostics()) {
            System.out.println("INFO: " + diagnostic.getMessage());
        }
    }
}


