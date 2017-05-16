package gov.samhsa.c2s.documentvalidator.service.dto;

import gov.samhsa.c2s.documentvalidator.service.schema.ValidationDiagnosticType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ValidationResultMetaData {
    private final Map<String, AtomicInteger> validationSummaryMap = new LinkedHashMap<>();

    public ValidationResultMetaData() {
        for (ValidationDiagnosticType diagnosticType : ValidationDiagnosticType.values()) {
            validationSummaryMap.put(diagnosticType.getTypeName(), new AtomicInteger(0));
        }
    }

    public Map<String, AtomicInteger> getValidationSummaryMap() {
        return validationSummaryMap;
    }

    public void addCount(ValidationDiagnosticType diagnosticType) {
        if (validationSummaryMap.containsKey(diagnosticType.getTypeName())) {
            validationSummaryMap.get(diagnosticType.getTypeName()).addAndGet(1);
        } else {
            validationSummaryMap.put(diagnosticType.getTypeName(), new AtomicInteger(1));
        }
    }
}