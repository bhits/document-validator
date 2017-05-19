package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentValidationService {
    ValidationResponseDto validateDocument(ValidationRequestDto requestDto);

    ValidationResponseDto validateDocumentFile(MultipartFile documentFile);
}
