package gov.samhsa.c2s.documentvalidator.service.schema;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TemplateIdTest {

    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetRoot() throws Exception {
        // Arrange
        final String root = "root";

        // Act
        TemplateId templateId = new TemplateId(root, Optional.empty());

        // Assert
        assertEquals(root, templateId.getRoot());
    }

    @Test
    public void testGetExtension() throws Exception {
        // Arrange
        final String root = "root";
        final String extension = "extension";

        // Act
        TemplateId templateId = new TemplateId(root, Optional.of(extension));

        // Assert
        assertEquals(root, templateId.getRoot());
        assertEquals(extension, templateId.getExtension().get());
    }

    @Test
    public void testHashCode() throws Exception {
        // Arrange
        final String root = "root";
        final String extension = "extension";
        final int expectedHashCode = (root + ":" + extension).hashCode();

        // Act
        TemplateId templateId = new TemplateId(root, Optional.of(extension));
        final int hashCode = templateId.hashCode();

        // Assert
        assertEquals(expectedHashCode, hashCode);
    }

    @Test
    public void testEquals() throws Exception {
        // Arrange
        final String root = "root";
        final String extension = "extension";

        // Act
        TemplateId templateId1 = new TemplateId(root, Optional.of(extension));
        TemplateId templateId2 = new TemplateId(root, Optional.of(extension));

        // Assert
        assertEquals(templateId1, templateId2);
    }

    @Test
    public void testOfRootAndExtension() throws Exception {
        // Arrange
        final String root = "root";
        final String extension = "extension";

        // Act
        TemplateId templateId = TemplateId.of(root, extension);

        // Assert
        assertNotNull(templateId);
        assertEquals(root, templateId.getRoot());
        assertEquals(extension, templateId.getExtension().get());
    }

    @Test
    public void testOfRootAndOptionalExtension() throws Exception {
        // Arrange
        final String root = "root";
        final String extension = "extension";

        // Act
        TemplateId templateId = TemplateId.of(root, Optional.of(extension));

        // Assert
        assertNotNull(templateId);
        assertEquals(root, templateId.getRoot());
        assertEquals(extension, templateId.getExtension().get());
    }

}