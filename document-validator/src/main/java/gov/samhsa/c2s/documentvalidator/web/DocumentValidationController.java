package gov.samhsa.c2s.documentvalidator.web;

import gov.samhsa.c2s.documentvalidator.service.DocumentValidationService;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DocumentValidationController {
    @Autowired
    DocumentValidationService documentValidationService;

    @PostMapping("/validateDocument")
    public ValidationResponseDto validateCCDADocument(@Valid @RequestBody ValidationRequestDto requestDto) {
        return documentValidationService.validateDocument(requestDto);
    }
}
