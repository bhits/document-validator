package gov.samhsa.c2s.documentvalidator.service.schema;

import java.util.Arrays;
import java.util.Optional;

import static gov.samhsa.c2s.documentvalidator.service.schema.CCDAVersion.R1;
import static gov.samhsa.c2s.documentvalidator.service.schema.CCDAVersion.R2;
import static gov.samhsa.c2s.documentvalidator.service.schema.TemplateId.of;

public enum DocumentType {
    // C-CDA R2.1
    CCDA_R2_1_CCD_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.2", "2015-08-01")),
    CCDA_R2_1_CARE_PLAN_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.15", "2015-08-01")),
    CCDA_R2_1_CONSULTATION_NOTE_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.4", "2015-08-01")),
    // TODO: Per C-CDA R2.1 documentation, the extension of templateId for DIAGNOSTIC_IMAGING_REPORT_V3 is 2014-06-09, this might be an error in the spec since it is not aligned with the rest of the document types defined in C-CDA R2.1
    CCDA_R2_1_DIAGNOSTIC_IMAGING_REPORT_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.5", "2014-06-09")),
    CCDA_R2_1_DISCHARGE_SUMMARY_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.8", "2015-08-01")),
    CCDA_R2_1_HISTORY_AND_PHYSICAL_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.3", "2015-08-01")),
    CCDA_R2_1_OPERATIVE_NOTE_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.7", "2015-08-01")),
    CCDA_R2_1_PROCEDURE_NOTE_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.6", "2015-08-01")),
    CCDA_R2_1_PROGRESS_NOTE_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.9", "2015-08-01")),
    CCDA_R2_1_REFERRAL_NOTE_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.14", "2015-08-01")),
    CCDA_R2_1_TRANSFER_SUMMARY_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.13", "2015-08-01")),
    CCDA_R2_1_UNSTRUCTURED_DOCUMENT_V3(is(R2), of("2.16.840.1.113883.10.20.22.1.10", "2015-08-01")),
    CCDA_R2_1_US_REALM_HEADER_FOR_PATIENT_GENERATED_DOCUMENT_V2(is(R2), of("2.16.840.1.113883.10.20.29.1", "2015-08-01")),

    // C-CDA R2.0
    CCDA_R2_0_CCD_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.2", "2014-06-09")),
    CCDA_R2_0_CARE_PLAN_V1(is(R2), of("2.16.840.1.113883.10.20.22.1.15", Optional.empty())),
    CCDA_R2_0_CONSULTATION_NOTE_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.4", "2014-06-09")),
    CCDA_R2_0_DIAGNOSTIC_IMAGING_REPORT_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.5", "2014-06-09")),
    CCDA_R2_0_DISCHARGE_SUMMARY_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.8", "2014-06-09")),
    CCDA_R2_0_HISTORY_AND_PHYSICAL_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.3", "2014-06-09")),
    CCDA_R2_0_OPERATIVE_NOTE_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.7", "2014-06-09")),
    CCDA_R2_0_PROCEDURE_NOTE_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.6", "2014-06-09")),
    CCDA_R2_0_PROGRESS_NOTE_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.9", "2014-06-09")),
    CCDA_R2_0_REFERRAL_NOTE_V1(is(R2), of("2.16.840.1.113883.10.20.22.1.14", Optional.empty())),
    CCDA_R2_0_TRANSFER_SUMMARY_V1(is(R2), of("2.16.840.1.113883.10.20.22.1.13", Optional.empty())),
    CCDA_R2_0_UNSTRUCTURED_DOCUMENT_V2(is(R2), of("2.16.840.1.113883.10.20.22.1.10", "2014-06-09")),
    CCDA_R2_0_US_REALM_HEADER_FOR_PATIENT_GENERATED_DOCUMENT_V1(is(R2), of("2.16.840.1.113883.10.20.29.1", Optional.empty())),

    // C-CDA R1.1
    CCDA_R1_1_CCD_V1(is(R1), of("2.16.840.1.113883.10.20.22.1.2", Optional.empty())),

    HITSP_C32(isNotCCDA(), of("2.16.840.1.113883.3.88.11.32.1", Optional.empty())),

    UNIDENTIFIED(isNotCCDA(), null);

    private final Optional<CCDAVersion> ccdaVersion;
    private final TemplateId templateId;

    DocumentType(Optional<CCDAVersion> ccdaVersion, TemplateId templateId) {
        this.ccdaVersion = ccdaVersion;
        this.templateId = templateId;
    }

    public static DocumentType from(String root, Optional<String> extension) {
        return Arrays.stream(values())
                .filter(DocumentType::isIdentified)
                .filter(type -> type.getTemplateId().equals(of(root, extension)))
                .findAny().orElse(UNIDENTIFIED);
    }

    public static boolean isIdentified(DocumentType documentType) {
        return Optional.ofNullable(documentType)
                .filter(type -> !UNIDENTIFIED.equals(type))
                .isPresent();
    }

    private static Optional<CCDAVersion> is(CCDAVersion ccdaVersion) {
        return Optional.of(ccdaVersion);
    }

    private static Optional<CCDAVersion> isNotCCDA() {
        return Optional.empty();
    }

    public TemplateId getTemplateId() {
        return templateId;
    }

    public Optional<CCDAVersion> getCcdaVersion() {
        return ccdaVersion;
    }

    public boolean isCCDA(CCDAVersion ccdaVersion) {
        return this.ccdaVersion.filter(ccdaVersion::equals).isPresent();
    }
}
