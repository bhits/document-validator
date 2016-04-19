package gov.samhsa.mhc.documentvalidator.service.dto;

import gov.samhsa.mhc.documentvalidator.service.DiagnosticType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ValidationResultsMetaData {
    private final Map<String, AtomicInteger> errorCounts = new LinkedHashMap<String, AtomicInteger>();
    private List<ResultMetaData> resultMetaData;

    public ValidationResultsMetaData() {
        for (DiagnosticType diagnosticType : DiagnosticType.values()) {
            errorCounts.put(diagnosticType.getTypeName(), new AtomicInteger(0));
        }
    }

    public List<ResultMetaData> getResultMetaData() {
        resultMetaData = new ArrayList<ResultMetaData>();
        for (Map.Entry<String, AtomicInteger> entry : errorCounts.entrySet()) {
            resultMetaData.add(new ResultMetaData(entry.getKey(), entry.getValue().intValue()));
        }
        return resultMetaData;
    }

    public void addCount(DiagnosticType diagnosticType) {
        if (errorCounts.containsKey(diagnosticType.getTypeName())) {
            errorCounts.get(diagnosticType.getTypeName()).addAndGet(1);
        } else {
            errorCounts.put(diagnosticType.getTypeName(), new AtomicInteger(1));
        }
    }
}
