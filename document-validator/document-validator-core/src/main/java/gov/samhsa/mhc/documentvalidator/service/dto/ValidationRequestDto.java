/**
 * Created by Jiahao.Li on 4/13/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.dto;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ValidationRequestDto {

    @NotNull
    private byte[] document;
    private Optional<String> documentEncoding = Optional.empty();

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public Optional<String> getDocumentEncoding() {
        return documentEncoding;
    }

    public void setDocumentEncoding(String documentEncoding) {
        this.documentEncoding = Optional.of(documentEncoding);
    }
}
