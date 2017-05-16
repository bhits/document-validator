package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;

public interface DocumentTypeResolver {
    DocumentType resolve(String document);
}
