package gov.samhsa.c2s.documentvalidator.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import gov.samhsa.c2s.documentvalidator.infrastructure.ValidationCriteria;
import gov.samhsa.c2s.documentvalidator.service.DocumentValidationService;
import gov.samhsa.c2s.documentvalidator.service.dto.*;
import gov.samhsa.c2s.documentvalidator.service.exception.UnsupportedDocumentTypeValidationException;
import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import gov.samhsa.c2s.documentvalidator.service.schema.ValidationDiagnosticType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DocumentValidationControllerTest {
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Mock
    private DocumentValidationService documentValidationService;

    @InjectMocks
    private DocumentValidationController sut;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        mvc = MockMvcBuilders.standaloneSetup(this.sut).build();
    }


    @Test
    public void testValidateClinicalDocument() throws Exception {
        // Arrange
        final DocumentType documentType = DocumentType.CCDA_R1_1_CCD_V1;
        final boolean isDocumentValid = false;

        List<DocumentValidationResultDetail> resultDetails = new ArrayList<>();
        final ValidationCriteria validationCriteria = ValidationCriteria.C_CDA_IG_ONLY;
        final String diagnosticType = "diagnosticType";
        final int count = 1;
        List<ValidationDiagnosticStatistics> diagnosticStatisticsList = new ArrayList<>();
        diagnosticStatisticsList.add(ValidationDiagnosticStatistics.builder()
                .diagnosticType(diagnosticType)
                .count(count)
                .build());
        DocumentValidationResultSummary resultSummary = DocumentValidationResultSummary.builder()
                .validationCriteria(validationCriteria)
                .diagnosticStatistics(diagnosticStatisticsList)
                .build();

        final String description = "description";
        final ValidationDiagnosticType validationDiagnosticType = ValidationDiagnosticType.CCDA_ERROR;
        final String xPath = "xPath";
        final String documentLineNumber = "documentLineNumber";
        final boolean isSchemaError = true;
        final boolean isIGIssue = true;
        resultDetails.add(DocumentValidationResultDetail.builder()
                .description(description)
                .diagnosticType(validationDiagnosticType)
                .xPath(xPath)
                .documentLineNumber(documentLineNumber)
                .isIGIssue(isIGIssue)
                .isSchemaError(isSchemaError)
                .build());

        ValidationResponseDto validationResponseDto = ValidationResponseDto.builder()
                .documentType(documentType)
                .isDocumentValid(isDocumentValid)
                .validationResultDetails(resultDetails)
                .validationResultSummary(resultSummary)
                .build();

        final byte[] document = new byte[100];
        ValidationRequestDto requestDto = new ValidationRequestDto();
        requestDto.setDocument(document);
        requestDto.setDocumentEncoding(Optional.of("UTF-8"));
        when(documentValidationService.validateDocument(requestDto)).thenReturn(validationResponseDto);

        // Act and Assert
        mvc.perform(post("/documentValidation")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("documentValid", is(isDocumentValid)));
    }

    @Test
    public void testValidateClinicalDocument_Throws_UnsupportedDocumentTypeValidationException() throws Exception {
        // Arrange
        final byte[] document = new byte[100];
        ValidationRequestDto requestDto = new ValidationRequestDto();
        requestDto.setDocument(document);
        requestDto.setDocumentEncoding(Optional.of("UTF-8"));
        when(documentValidationService.validateDocument(requestDto)).thenThrow(UnsupportedDocumentTypeValidationException.class);

        // Act and Assert
        mvc.perform(post("/documentValidation")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(requestDto)))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void testValidateClinicalDocumentFile() throws Exception {
        // Arrange
        final DocumentType documentType = DocumentType.CCDA_R1_1_CCD_V1;
        final boolean isDocumentValid = false;

        List<DocumentValidationResultDetail> resultDetails = new ArrayList<>();
        final ValidationCriteria validationCriteria = ValidationCriteria.C_CDA_IG_ONLY;
        final String diagnosticType = "diagnosticType";
        final int count = 1;
        List<ValidationDiagnosticStatistics> diagnosticStatisticsList = new ArrayList<>();
        diagnosticStatisticsList.add(ValidationDiagnosticStatistics.builder()
                .diagnosticType(diagnosticType)
                .count(count)
                .build());
        DocumentValidationResultSummary resultSummary = DocumentValidationResultSummary.builder()
                .validationCriteria(validationCriteria)
                .diagnosticStatistics(diagnosticStatisticsList)
                .build();

        final String description = "description";
        final ValidationDiagnosticType validationDiagnosticType = ValidationDiagnosticType.CCDA_ERROR;
        final String xPath = "xPath";
        final String documentLineNumber = "documentLineNumber";
        final boolean isSchemaError = true;
        final boolean isIGIssue = true;
        resultDetails.add(DocumentValidationResultDetail.builder()
                .description(description)
                .diagnosticType(validationDiagnosticType)
                .xPath(xPath)
                .documentLineNumber(documentLineNumber)
                .isIGIssue(isIGIssue)
                .isSchemaError(isSchemaError)
                .build());

        ValidationResponseDto validationResponseDto = ValidationResponseDto.builder()
                .documentType(documentType)
                .isDocumentValid(isDocumentValid)
                .validationResultDetails(resultDetails)
                .validationResultSummary(resultSummary)
                .build();

        MockMultipartFile documentFile = new MockMultipartFile("documentFile", "document.xml".getBytes());
        when(documentValidationService.validateDocumentFile(documentFile)).thenReturn(validationResponseDto);

        // Act and Assert
        mvc.perform(MockMvcRequestBuilders.fileUpload("/multipartFileDocumentValidation")
                .file(documentFile))
                .andExpect(status().isOk());
    }
}
