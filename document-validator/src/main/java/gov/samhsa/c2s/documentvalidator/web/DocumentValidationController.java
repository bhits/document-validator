package gov.samhsa.c2s.documentvalidator.web;

import gov.samhsa.c2s.documentvalidator.service.DocumentValidationService;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
public class DocumentValidationController {
    @Autowired
    DocumentValidationService documentValidationService;

    /**
     * Implement to validate clinical document in encoded bytes format
     * @param requestDto
     * @return
     */
    @PostMapping("/validateDocument")
    public ValidationResponseDto validateClinicalDocument(@Valid @RequestBody ValidationRequestDto requestDto) {
        return documentValidationService.validateDocument(requestDto);
    }

    /**
     * Implement to validate clinical document in file format
     * @param documentFile
     * @return
     */
    @PostMapping("/validateDocumentFile")
    public ValidationResponseDto validateClinicalDocumentFile(@RequestParam MultipartFile documentFile) {
        return documentValidationService.validateDocumentFile(documentFile);
    }
}
