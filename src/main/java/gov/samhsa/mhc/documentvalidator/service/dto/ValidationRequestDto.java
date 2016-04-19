/**
 * Created by Jiahao.Li on 4/13/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.dto;

import javax.validation.constraints.NotNull;

public class ValidationRequestDto {

    @NotNull
    private byte[] document;

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
}
