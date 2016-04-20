package gov.samhsa.mhc.documentvalidator.service.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ValidationResultsMetaData {
    private final Map<String, AtomicInteger> validationSummary = new LinkedHashMap<>();

    public ValidationResultsMetaData() {
        for (DiagnosticType diagnosticType : DiagnosticType.values()) {
            validationSummary.put(diagnosticType.getTypeName(), new AtomicInteger(0));
        }
    }

    public Map<String, AtomicInteger> getValidationSummary() {
        return validationSummary;
    }

    public void addCount(DiagnosticType diagnosticType) {
        if (validationSummary.containsKey(diagnosticType.getTypeName())) {
            validationSummary.get(diagnosticType.getTypeName()).addAndGet(1);
        } else {
            validationSummary.put(diagnosticType.getTypeName(), new AtomicInteger(1));
        }
    }
}
