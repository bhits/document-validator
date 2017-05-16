package gov.samhsa.c2s.documentvalidator.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
public class ValidationRequestDto {

    @NotNull
    private byte[] document;

    private Optional<String> documentEncoding = Optional.empty();

    public void setDocumentEncoding(String documentEncoding) {
        this.documentEncoding = Optional.of(documentEncoding);
    }
}