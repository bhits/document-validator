package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationResponseDto;

public interface DocumentValidationService {
    ValidationResponseDto validateDocument(ValidationRequestDto requestDto);
}
