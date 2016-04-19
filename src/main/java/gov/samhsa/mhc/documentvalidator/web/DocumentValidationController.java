/**
 * Created by jiahao.li on 4/12/2016.
 */
package gov.samhsa.mhc.documentvalidator.web;

import gov.samhsa.mhc.documentvalidator.service.DocumentValidationService;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentValidationController {
    @Autowired
    DocumentValidationService documentValidationService;

    @RequestMapping("/")
    public String index() {
        return "Welcome to Validator Service";
    }

    @RequestMapping(value = "/validator", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public ValidationResponseDto validateCCDADocument(
            @RequestParam(value = "ccdaDocument", required = true) MultipartFile ccdaDocument) {
        return documentValidationService.validateDocument(ccdaDocument);
    }
}
