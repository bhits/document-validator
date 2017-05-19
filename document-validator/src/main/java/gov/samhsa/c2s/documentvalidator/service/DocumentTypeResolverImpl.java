package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.common.document.accessor.DocumentAccessor;
import gov.samhsa.c2s.common.document.accessor.DocumentAccessorException;
import gov.samhsa.c2s.common.document.converter.DocumentXmlConverter;
import gov.samhsa.c2s.documentvalidator.service.exception.DocumentTypeResolverException;
import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import gov.samhsa.c2s.documentvalidator.service.schema.TemplateIdRootNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class DocumentTypeResolverImpl implements DocumentTypeResolver {

    private static final String XPATH_TEMPLATE_ID = "/hl7:ClinicalDocument/hl7:templateId[@root]";

    private final DocumentAccessor documentAccessor;

    private final DocumentXmlConverter documentXmlConverter;

    @Autowired
    public DocumentTypeResolverImpl(DocumentAccessor documentAccessor, DocumentXmlConverter documentXmlConverter) {
        this.documentAccessor = documentAccessor;
        this.documentXmlConverter = documentXmlConverter;
    }

    @Override
    public DocumentType resolve(String document) {
        return resolve(documentXmlConverter.loadDocument(document));
    }

    private DocumentType resolve(Document document) {
        try {
            final Stream<Node> templateIds = documentAccessor.getNodeListAsStream(document, XPATH_TEMPLATE_ID);
            return templateIds
                    .map(node -> DocumentType.from(toRoot(node), toExtension(node)))
                    .filter(DocumentType::isIdentified)
                    .findAny()
                    .orElse(DocumentType.UNIDENTIFIED);
        } catch (DocumentAccessorException e) {
            log.error(e.getMessage(), e);
            throw new DocumentTypeResolverException(e);
        }
    }

    private String toRoot(Node node) {
        return Optional.ofNullable(node)
                .map(Node::getAttributes)
                .map(attributes -> attributes.getNamedItem("root"))
                .map(Node::getNodeValue)
                .filter(StringUtils::hasText)
                .orElseThrow(TemplateIdRootNotFoundException::new);
    }

    private Optional<String> toExtension(Node node) {
        return Optional.ofNullable(node)
                .map(Node::getAttributes)
                .map(attributes -> attributes.getNamedItem("extension"))
                .map(Node::getNodeValue)
                .filter(StringUtils::hasText);
    }
}
