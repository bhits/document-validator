package gov.samhsa.c2s.documentvalidator.service.schema;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DocumentTypeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getTemplateId() throws Exception {
        // Arrange
        final DocumentType documentType = DocumentType.values()[0];

        // Act
        final TemplateId templateId = documentType.getTemplateId();

        // Assert
        assertNotNull(templateId);
        assertNotNull(templateId.getRoot());
        assertTrue(StringUtils.hasText(templateId.getRoot()));
        assertNotNull(templateId.getExtension());
    }

    @Test
    public void from() throws Exception {
        // Arrange
        final DocumentType documentType = DocumentType.values()[0];
        final TemplateId templateId = documentType.getTemplateId();

        // Act
        final DocumentType from = DocumentType.from(templateId.getRoot(), templateId.getExtension());

        // Assert
        assertEquals(documentType, from);
    }

    @Test
    public void isIdentified() throws Exception {
        // Arrange
        final DocumentType ccdaR21CcdV3 = DocumentType.CCDA_R2_1_CCD_V3;
        final DocumentType unidentified = DocumentType.UNIDENTIFIED;

        // Act
        final boolean identifiedCcdaR21CcdV3 = DocumentType.isIdentified(ccdaR21CcdV3);
        final boolean identifiedUnidentified = DocumentType.isIdentified(unidentified);

        // Assert
        assertNotNull(ccdaR21CcdV3);
        assertNotNull(unidentified);
        assertTrue(identifiedCcdaR21CcdV3);
        assertFalse(identifiedUnidentified);
    }
}