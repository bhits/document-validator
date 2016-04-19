/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentValidationService {
    ValidationResponseDto validateDocument(MultipartFile ccdaFile);
}