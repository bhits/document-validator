/**
 * Created by jiahao.li on 4/12/2016.
 */
package gov.samhsa.c2s.documentvalidator.web;

import gov.samhsa.c2s.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationResponseDto;
import gov.samhsa.c2s.documentvalidator.service.DocumentValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DocumentValidationController {
    @Autowired
    DocumentValidationService documentValidationService;

    @RequestMapping("/")
    public String index() {
        return "Welcome to Validator Service";
    }

    @RequestMapping(value = "/validator", method = RequestMethod.POST)
    public ValidationResponseDto validateCCDADocument(@Valid @RequestBody ValidationRequestDto requestDto) {
        return documentValidationService.validateDocument(requestDto);
    }
}
