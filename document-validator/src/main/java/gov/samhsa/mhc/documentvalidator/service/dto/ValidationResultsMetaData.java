package gov.samhsa.mhc.documentvalidator.service.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ValidationResultsMetaData {
    private final Map<String, AtomicInteger> validationSummaryMap = new LinkedHashMap<>();

    public ValidationResultsMetaData() {
        for (DiagnosticType diagnosticType : DiagnosticType.values()) {
            validationSummaryMap.put(diagnosticType.getTypeName(), new AtomicInteger(0));
        }
    }

    public Map<String, AtomicInteger> getValidationSummaryMap() {
        return validationSummaryMap;
    }

    public void addCount(DiagnosticType diagnosticType) {
        if (validationSummaryMap.containsKey(diagnosticType.getTypeName())) {
            validationSummaryMap.get(diagnosticType.getTypeName()).addAndGet(1);
        } else {
            validationSummaryMap.put(diagnosticType.getTypeName(), new AtomicInteger(1));
        }
    }
}
