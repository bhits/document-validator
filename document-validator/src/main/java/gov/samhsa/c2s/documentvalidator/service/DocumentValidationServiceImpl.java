package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DocumentValidationServiceImpl implements DocumentValidationService {
    @Override
    public ValidationResponseDto validateDocument(ValidationRequestDto requestDto) {
        return null;
    }
}
