package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.common.document.accessor.DocumentAccessor;
import gov.samhsa.c2s.common.document.accessor.DocumentAccessorImpl;
import gov.samhsa.c2s.common.document.converter.DocumentXmlConverter;
import gov.samhsa.c2s.common.document.converter.DocumentXmlConverterImpl;
import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;


public class DocumentTypeResolverImplRealDocumentTest {
    private DocumentXmlConverter documentXmlConverter = new DocumentXmlConverterImpl();
    private DocumentAccessor documentAccessor = new DocumentAccessorImpl();
    private DocumentTypeResolverImpl sut = new DocumentTypeResolverImpl(documentAccessor, documentXmlConverter);

    @Before
    public void setup() {
        ReflectionTestUtils.setField(sut, "documentAccessor", documentAccessor);
    }

    @Test
    public void resolve_CCDA_R2_1_CCD_V3() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_1_CCD_V3;
        final String fileClasspathLocation = "c-cda-r2-1/C-CDA_R2-1_CCD.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_CARE_PLAN_V1() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_CARE_PLAN_V1;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Care_Plan.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_CCD_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_CCD_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_CCD.XML";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_CCD_V2_2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_CCD_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_CCD_2.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_CONSULTATION_NOTE_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_CONSULTATION_NOTE_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Consultation_Note.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    @Ignore("Per C-CDA R2.1 documentation, the extension of templateId for DIAGNOSTIC_IMAGING_REPORT_V3 is 2014-06-09, this might be an error in the spec since it is not aligned with the rest of the document types defined in C-CDA R2.1")
    public void resolve_CCDA_R2_0_DIAGNOSTIC_IMAGING_REPORT_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_DIAGNOSTIC_IMAGING_REPORT_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Diagnostic_Imaging_Report.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_DISCHARGE_SUMMARY_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_DISCHARGE_SUMMARY_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Discharge_Summary.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_CARE_PLAN_V1_2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_CARE_PLAN_V1;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_HHCP485.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_HISTORY_AND_PHYSICAL_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_HISTORY_AND_PHYSICAL_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_History_and_Physical.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_OPERATIVE_NOTE_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_OPERATIVE_NOTE_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Operative_Note.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_US_REALM_HEADER_FOR_PATIENT_GENERATED_DOCUMENT_V1() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_US_REALM_HEADER_FOR_PATIENT_GENERATED_DOCUMENT_V1;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_PatientGeneratedDocument.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_PROCEDURE_NOTE_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_PROCEDURE_NOTE_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Procedure_Note.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_PROGRESS_NOTE_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_PROGRESS_NOTE_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Progress_Note.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_REFERRAL_NOTE_V1() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_REFERRAL_NOTE_V1;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Referral_Note.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_TRANSFER_SUMMARY_V1() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_TRANSFER_SUMMARY_V1;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Transfer_Summary.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_UNSTRUCTURED_DOCUMENT_V2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_UNSTRUCTURED_DOCUMENT_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Unstructured_Document_embed.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_CCDA_R2_0_UNSTRUCTURED_DOCUMENT_V2_2() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.CCDA_R2_0_UNSTRUCTURED_DOCUMENT_V2;
        final String fileClasspathLocation = "c-cda-r2-0/C-CDA_R2_Unstructured_Document_reference.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }

    @Test
    public void resolve_HITSP_C32() throws Exception {
        // Arrange
        final DocumentType expectedDocumentType = DocumentType.HITSP_C32;
        final String fileClasspathLocation = "sampleC32/c32.xml";
        final ClassPathResource classPathResource = new ClassPathResource(fileClasspathLocation);
        final String xmlFileString = new BufferedReader(new InputStreamReader(classPathResource.getInputStream())).lines().collect(joining("\n"));
        Document xmlDocument = documentXmlConverter.loadDocument(xmlFileString);

        // Act
        final DocumentType resolve = sut.resolve(xmlDocument);

        // Assert
        assertEquals(expectedDocumentType, resolve);
    }
}