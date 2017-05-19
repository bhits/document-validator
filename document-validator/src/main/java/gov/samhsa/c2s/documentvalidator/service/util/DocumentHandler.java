package gov.samhsa.c2s.documentvalidator.service.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class DocumentHandler {
    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    public static String convertByteDocumentToString(byte[] document, Optional<String> documentEncoding) {
        // Set UTF-8 as default encoding if no present
        final Charset charset = documentEncoding.map(Charset::forName).orElse(DEFAULT_ENCODING);
        return new String(document, charset);
    }
}