/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

import gov.samhsa.mhc.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResponseDto;

public interface DocumentValidationService {
    ValidationResponseDto validateDocument(ValidationRequestDto requestDto);
}