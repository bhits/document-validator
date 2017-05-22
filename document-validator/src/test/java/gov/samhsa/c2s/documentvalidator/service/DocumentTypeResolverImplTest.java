package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.common.document.accessor.DocumentAccessor;
import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DocumentTypeResolverImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private DocumentAccessor documentAccessor;

    @InjectMocks
    private DocumentTypeResolverImpl sut;

    @Test
    public void resolve_Selected_Document_Is_CCDA_R2_1_CCD_V3() throws Exception {
        // Arrange
        Document document = mock(Document.class);
        Node node = mock(Node.class);
        final DocumentType selectedDocument = DocumentType.CCDA_R2_1_CCD_V3;
        String rootValue = selectedDocument.getTemplateId().getRoot();
        String extensionValue = selectedDocument.getTemplateId().getExtension().orElse(null);
        when(documentAccessor.getNodeListAsStream(eq(document), anyString())).thenReturn(Stream.of(node));
        NamedNodeMap attributes = mock(NamedNodeMap.class);
        when(node.getAttributes()).thenReturn(attributes);
        Node root = mock(Node.class);
        when(attributes.getNamedItem("root")).thenReturn(root);
        Node extension = mock(Node.class);
        when(attributes.getNamedItem("extension")).thenReturn(extension);
        when(extension.getNodeValue()).thenReturn(extensionValue);
        when(root.getNodeValue()).thenReturn(rootValue);

        // Act
        final DocumentType resolve = sut.resolve(document);

        // Assert
        assertEquals(selectedDocument, resolve);
    }

    @Test
    public void resolve_Selected_Document_Is_CCDA_R2_0_CARE_PLAN_V1() throws Exception {
        // Arrange
        Document document = mock(Document.class);
        Node node = mock(Node.class);
        final DocumentType selectedDocument = DocumentType.CCDA_R2_0_CARE_PLAN_V1;
        String rootValue = selectedDocument.getTemplateId().getRoot();
        String extensionValue = selectedDocument.getTemplateId().getExtension().orElse(null);
        when(documentAccessor.getNodeListAsStream(eq(document), anyString())).thenReturn(Stream.of(node));
        NamedNodeMap attributes = mock(NamedNodeMap.class);
        when(node.getAttributes()).thenReturn(attributes);
        Node root = mock(Node.class);
        when(attributes.getNamedItem("root")).thenReturn(root);
        Node extension = mock(Node.class);
        when(attributes.getNamedItem("extension")).thenReturn(extension);
        when(extension.getNodeValue()).thenReturn(extensionValue);
        when(root.getNodeValue()).thenReturn(rootValue);

        // Act
        final DocumentType resolve = sut.resolve(document);

        // Assert
        assertEquals(selectedDocument, resolve);
    }

    @Test
    public void resolve_Selected_Document_UNIDENTIFIED() throws Exception {
        // Arrange
        Document document = mock(Document.class);
        Node node = mock(Node.class);
        String rootValue = "rootValue";
        String extensionValue = "extensionValue";
        when(documentAccessor.getNodeListAsStream(eq(document), anyString())).thenReturn(Stream.of(node));
        NamedNodeMap attributes = mock(NamedNodeMap.class);
        when(node.getAttributes()).thenReturn(attributes);
        Node root = mock(Node.class);
        when(attributes.getNamedItem("root")).thenReturn(root);
        Node extension = mock(Node.class);
        when(attributes.getNamedItem("extension")).thenReturn(extension);
        when(extension.getNodeValue()).thenReturn(extensionValue);
        when(root.getNodeValue()).thenReturn(rootValue);

        // Act
        final DocumentType resolve = sut.resolve(document);

        // Assert
        assertEquals(DocumentType.UNIDENTIFIED, resolve);
    }
}
