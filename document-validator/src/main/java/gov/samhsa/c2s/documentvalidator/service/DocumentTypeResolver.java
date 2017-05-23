package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import org.w3c.dom.Document;

public interface DocumentTypeResolver {
    DocumentType resolve(String document);

    DocumentType resolve(Document document);
}
